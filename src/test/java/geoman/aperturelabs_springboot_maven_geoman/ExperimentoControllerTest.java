package geoman.aperturelabs_springboot_maven_geoman;

import com.fasterxml.jackson.databind.ObjectMapper;
import geoman.aperturelabs_springboot_maven_geoman.Controller.ExperimentoController;
import geoman.aperturelabs_springboot_maven_geoman.DTO.ExperimentoDTO;
import geoman.aperturelabs_springboot_maven_geoman.Service.ExperimentoService;
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


@WebMvcTest(ExperimentoController.class)
class ExperimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExperimentoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ExperimentoDTO dtoEjemplo() {
        return new ExperimentoDTO(1L, "Test Gel Portal", "Prueba de gel azul", "en progreso", "Camara-01", 2L, 3L);
    }

    @Test
    void findAll_deberiaRetornar200ConListaDeExperimentos() throws Exception {
        when(service.findAll()).thenReturn(List.of(dtoEjemplo()));

        mockMvc.perform(get("/api/experimento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test Gel Portal"))
                .andExpect(jsonPath("$[0].resultado").value("en progreso"))
                .andExpect(jsonPath("$[0].camara").value("Camara-01"))
                .andExpect(jsonPath("$[0].supervisorId").value(2))
                .andExpect(jsonPath("$[0].sujetoPruebaId").value(3));
    }

    @Test
    void findById_deberiaRetornar200ConDTO() throws Exception {
        when(service.findById(1L)).thenReturn(dtoEjemplo());

        mockMvc.perform(get("/api/experimento/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test Gel Portal"));
    }

    @Test
    void save_deberiaRetornar200ConDTOCreado() throws Exception {
        ExperimentoDTO input = new ExperimentoDTO(null, "Test Gel Portal", "Prueba de gel azul", "en progreso", "Camara-01", 2L, 3L);
        when(service.save(any(ExperimentoDTO.class))).thenReturn(dtoEjemplo());

        mockMvc.perform(post("/api/experimento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test Gel Portal"));
    }

    @Test
    void update_deberiaRetornar200ConDTOActualizado() throws Exception {
        ExperimentoDTO input = new ExperimentoDTO(null, "Test Gel Portal", "Prueba completada", "exitoso", "Camara-01", 2L, 3L);
        ExperimentoDTO updated = new ExperimentoDTO(1L, "Test Gel Portal", "Prueba completada", "exitoso", "Camara-01", 2L, 3L);
        when(service.update(eq(1L), any(ExperimentoDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/experimento/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultado").value("exitoso"))
                .andExpect(jsonPath("$.descripcion").value("Prueba completada"));
    }

    @Test
    void delete_deberiaRetornar200YLlamarAlServicio() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/experimento/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void findAll_conListaVacia_deberiaRetornar200ConArrayVacio() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/experimento"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
