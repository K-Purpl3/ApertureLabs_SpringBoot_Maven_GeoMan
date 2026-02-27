package geoman.aperturelabs_springboot_maven_geoman;

import com.fasterxml.jackson.databind.ObjectMapper;
import geoman.aperturelabs_springboot_maven_geoman.Controller.EmpleadoController;
import geoman.aperturelabs_springboot_maven_geoman.DTO.EmpleadoDTO;
import geoman.aperturelabs_springboot_maven_geoman.Service.EmpleadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpleadoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private EmpleadoDTO dtoEjemplo() {
        return new EmpleadoDTO(1L, "Doug Rattmann", "R&D", "Investigacion", "desaparecido", null);
    }

    @Test
    void findAll_deberiaRetornar200ConListaDeEmpleados() throws Exception {
        when(service.findAll()).thenReturn(List.of(dtoEjemplo()));

        mockMvc.perform(get("/api/empleado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Doug Rattmann"))
                .andExpect(jsonPath("$[0].rol").value("R&D"))
                .andExpect(jsonPath("$[0].departamento").value("Investigacion"))
                .andExpect(jsonPath("$[0].estado").value("desaparecido"));
    }

    @Test
    void findById_deberiaRetornar200ConDTO() throws Exception {
        when(service.findById(1L)).thenReturn(dtoEjemplo());

        mockMvc.perform(get("/api/empleado/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Doug Rattmann"));
    }

    @Test
    void save_deberiaRetornar200ConDTOCreado() throws Exception {
        EmpleadoDTO input = new EmpleadoDTO(null, "Doug Rattmann", "R&D", "Investigacion", "desaparecido", null);
        when(service.save(any(EmpleadoDTO.class))).thenReturn(dtoEjemplo());

        mockMvc.perform(post("/api/empleado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Doug Rattmann"));
    }

    @Test
    void update_deberiaRetornar200ConDTOActualizado() throws Exception {
        EmpleadoDTO input = new EmpleadoDTO(null, "Doug Rattmann", "Supervisor", "Testing", "activo", 2L);
        EmpleadoDTO updated = new EmpleadoDTO(1L, "Doug Rattmann", "Supervisor", "Testing", "activo", 2L);
        when(service.update(eq(1L), any(EmpleadoDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/empleado/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rol").value("Supervisor"))
                .andExpect(jsonPath("$.estado").value("activo"))
                .andExpect(jsonPath("$.apertureCoreId").value(2));
    }

    @Test
    void delete_deberiaRetornar200YLlamarAlServicio() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/empleado/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void findAll_conListaVacia_deberiaRetornar200ConArrayVacio() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/empleado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
