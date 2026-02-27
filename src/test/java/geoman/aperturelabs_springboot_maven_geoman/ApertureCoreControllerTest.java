package geoman.aperturelabs_springboot_maven_geoman;

import com.fasterxml.jackson.databind.ObjectMapper;
import geoman.aperturelabs_springboot_maven_geoman.Controller.ApertureCoreController;
import geoman.aperturelabs_springboot_maven_geoman.DTO.ApertureCoreDTO;
import geoman.aperturelabs_springboot_maven_geoman.Service.ApertureCoreService;
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

@WebMvcTest(ApertureCoreController.class)
class ApertureCoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApertureCoreService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ApertureCoreDTO dtoEjemplo() {
        return new ApertureCoreDTO(1L, "Wheatley", "Estupido pero optimista", "activo");
    }

    @Test
    void findAll_deberiaRetornar200ConListaDeCores() throws Exception {
        when(service.findAll()).thenReturn(List.of(dtoEjemplo()));

        mockMvc.perform(get("/api/aperture-core"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Wheatley"))
                .andExpect(jsonPath("$[0].personalidad").value("Estupido pero optimista"))
                .andExpect(jsonPath("$[0].estado").value("activo"));
    }

    @Test
    void findById_deberiaRetornar200ConDTO() throws Exception {
        when(service.findById(1L)).thenReturn(dtoEjemplo());

        mockMvc.perform(get("/api/aperture-core/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Wheatley"));
    }

    @Test
    void save_deberiaRetornar200ConDTOCreado() throws Exception {
        ApertureCoreDTO input = new ApertureCoreDTO(null, "Wheatley", "Estupido pero optimista", "activo");
        when(service.save(any(ApertureCoreDTO.class))).thenReturn(dtoEjemplo());

        mockMvc.perform(post("/api/aperture-core")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Wheatley"));
    }

    @Test
    void update_deberiaRetornar200ConDTOActualizado() throws Exception {
        ApertureCoreDTO input = new ApertureCoreDTO(null, "Wheatley", "Ahora malvado", "activo");
        ApertureCoreDTO updated = new ApertureCoreDTO(1L, "Wheatley", "Ahora malvado", "activo");
        when(service.update(eq(1L), any(ApertureCoreDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/aperture-core/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personalidad").value("Ahora malvado"));
    }

    @Test
    void delete_deberiaRetornar200YLlamarAlServicio() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/aperture-core/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void findAll_conListaVacia_deberiaRetornar200ConArrayVacio() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/aperture-core"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
