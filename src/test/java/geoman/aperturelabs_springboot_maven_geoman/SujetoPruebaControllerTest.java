package geoman.aperturelabs_springboot_maven_geoman;

import com.fasterxml.jackson.databind.ObjectMapper;
import geoman.aperturelabs_springboot_maven_geoman.Controller.SujetoPruebaController;
import geoman.aperturelabs_springboot_maven_geoman.DTO.SujetoPruebaDTO;
import geoman.aperturelabs_springboot_maven_geoman.Service.SujetoPruebaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SujetoPruebaController.class)
class SujetoPruebaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SujetoPruebaService service;

    @Autowired
    private ObjectMapper objectMapper;

    private SujetoPruebaDTO dtoEjemplo() {
        return new SujetoPruebaDTO(1L, "Chell", "activo", 1, 2L);
    }

    @Test
    void findAll_deberiaRetornar200ConListaDeSujetos() throws Exception {
        when(service.findAll()).thenReturn(List.of(dtoEjemplo()));

        mockMvc.perform(get("/api/sujeto-prueba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Chell"))
                .andExpect(jsonPath("$[0].estado").value("activo"))
                .andExpect(jsonPath("$[0].numeroCaso").value(1));
    }

    @Test
    void findById_deberiaRetornar200ConDTO() throws Exception {
        when(service.findById(1L)).thenReturn(dtoEjemplo());

        mockMvc.perform(get("/api/sujeto-prueba/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Chell"));
    }

    @Test
    void save_deberiaRetornar200ConDTOCreado() throws Exception {
        SujetoPruebaDTO input = new SujetoPruebaDTO(null, "Chell", "activo", 1, 2L);
        when(service.save(any(SujetoPruebaDTO.class))).thenReturn(dtoEjemplo());

        mockMvc.perform(post("/api/sujeto-prueba")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Chell"));
    }

    @Test
    void update_deberiaRetornar200ConDTOActualizado() throws Exception {
        SujetoPruebaDTO input = new SujetoPruebaDTO(null, "Chell", "escapado", 1, 2L);
        SujetoPruebaDTO updated = new SujetoPruebaDTO(1L, "Chell", "escapado", 1, 2L);
        when(service.update(eq(1L), any(SujetoPruebaDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/sujeto-prueba/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("escapado"));
    }

    @Test
    void delete_deberiaRetornar200YLlamarAlServicio() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/sujeto-prueba/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void findAll_conListaVacia_deberiaRetornar200ConArrayVacio() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/sujeto-prueba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
