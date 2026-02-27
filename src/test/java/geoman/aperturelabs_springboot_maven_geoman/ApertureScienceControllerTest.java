package geoman.aperturelabs_springboot_maven_geoman;

import com.fasterxml.jackson.databind.ObjectMapper;
import geoman.aperturelabs_springboot_maven_geoman.Controller.ApertureScienceController;
import geoman.aperturelabs_springboot_maven_geoman.DTO.ApertureScienceDTO;
import geoman.aperturelabs_springboot_maven_geoman.Service.ApertureScienceService;
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

@WebMvcTest(ApertureScienceController.class)
class ApertureScienceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApertureScienceService service;

    @Autowired
    private ObjectMapper objectMapper;

    private ApertureScienceDTO dtoEjemplo() {
        return new ApertureScienceDTO(1L, "Aperture Science", "We do what we must", "Michigan", 1953);
    }

    @Test
    void findAll_deberiaRetornar200ConListaDeEmpresas() throws Exception {
        when(service.findAll()).thenReturn(List.of(dtoEjemplo()));

        mockMvc.perform(get("/api/aperture-science"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Aperture Science"))
                .andExpect(jsonPath("$[0].slogan").value("We do what we must"))
                .andExpect(jsonPath("$[0].ubicacion").value("Michigan"))
                .andExpect(jsonPath("$[0].anioFundacion").value(1953));
    }

    @Test
    void findById_deberiaRetornar200ConDTO() throws Exception {
        when(service.findById(1L)).thenReturn(dtoEjemplo());

        mockMvc.perform(get("/api/aperture-science/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Aperture Science"));
    }

    @Test
    void save_deberiaRetornar200ConDTOCreado() throws Exception {
        ApertureScienceDTO input = new ApertureScienceDTO(null, "Aperture Science", "We do what we must", "Michigan",
                1953);
        when(service.save(any(ApertureScienceDTO.class))).thenReturn(dtoEjemplo());

        mockMvc.perform(post("/api/aperture-science")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Aperture Science"));
    }

    @Test
    void update_deberiaRetornar200ConDTOActualizado() throws Exception {
        ApertureScienceDTO input = new ApertureScienceDTO(null, "Aperture Science Updated", "New slogan", "Utah", 1960);
        ApertureScienceDTO updated = new ApertureScienceDTO(1L, "Aperture Science Updated", "New slogan", "Utah", 1960);
        when(service.update(eq(1L), any(ApertureScienceDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/aperture-science/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Aperture Science Updated"))
                .andExpect(jsonPath("$.slogan").value("New slogan"));
    }

    @Test
    void delete_deberiaRetornar200YLlamarAlServicio() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/aperture-science/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void findAll_conListaVacia_deberiaRetornar200ConArrayVacio() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/aperture-science"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
