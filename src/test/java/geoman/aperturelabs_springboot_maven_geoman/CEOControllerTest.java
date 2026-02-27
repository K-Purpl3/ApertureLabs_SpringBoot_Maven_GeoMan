package geoman.aperturelabs_springboot_maven_geoman;

import com.fasterxml.jackson.databind.ObjectMapper;
import geoman.aperturelabs_springboot_maven_geoman.Controller.CEOController;
import geoman.aperturelabs_springboot_maven_geoman.DTO.CEODTO;
import geoman.aperturelabs_springboot_maven_geoman.Service.CEOService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CEOController.class)
class CEOControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CEOService service;

    @Autowired
    private ObjectMapper objectMapper;

    private CEODTO dtoEjemplo() {
        return new CEODTO(1L, "Cave Johnson", "vivo", 1953, 1L);
    }

    @Test
    void findAll_deberiaRetornar200ConListaDeCEOs() throws Exception {
        when(service.findAll()).thenReturn(List.of(dtoEjemplo()));

        mockMvc.perform(get("/api/ceo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Cave Johnson"))
                .andExpect(jsonPath("$[0].estado").value("vivo"))
                .andExpect(jsonPath("$[0].anioMandato").value(1953));
    }

    @Test
    void findById_deberiaRetornar200ConDTO() throws Exception {
        when(service.findById(1L)).thenReturn(dtoEjemplo());

        mockMvc.perform(get("/api/ceo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cave Johnson"));
    }

    @Test
    void save_deberiaRetornar200ConDTOCreado() throws Exception {
        CEODTO input = new CEODTO(null, "Cave Johnson", "vivo", 1953, 1L);
        when(service.save(any(CEODTO.class))).thenReturn(dtoEjemplo());

        mockMvc.perform(post("/api/ceo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cave Johnson"));
    }

    @Test
    void update_deberiaRetornar200ConDTOActualizado() throws Exception {
        CEODTO input = new CEODTO(null, "Caroline", "Vivo", 1994, 1L);
        CEODTO updated = new CEODTO(1L, "GLaDOS", "IA", 1994, 1L);
        when(service.update(eq(1L), any(CEODTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/ceo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Caroline"))
                .andExpect(jsonPath("$.estado").value("Fallecido"));
    }

    @Test
    void delete_deberiaRetornar200YLlamarAlServicio() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/ceo/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void findAll_conListaVacia_deberiaRetornar200ConArrayVacio() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/ceo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
