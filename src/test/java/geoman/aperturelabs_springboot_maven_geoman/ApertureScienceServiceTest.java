package geoman.aperturelabs_springboot_maven_geoman;

import geoman.aperturelabs_springboot_maven_geoman.DTO.ApertureScienceDTO;
import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureScience;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureScienceRepository;
import geoman.aperturelabs_springboot_maven_geoman.Service.ApertureScienceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApertureScienceServiceTest {

    @Mock
    private ApertureScienceRepository repository;

    @InjectMocks
    private ApertureScienceService service;

    @Test
    void findAll_deberiaRetornarListaDeDTOs() {
        ApertureScience empresa = new ApertureScience(1L, "Aperture Science", "We do what we must", "Michigan", 1953, null);
        when(repository.findAll()).thenReturn(List.of(empresa));

        List<ApertureScienceDTO> resultado = service.findAll();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Aperture Science");
    }

    @Test
    void findById_deberiaRetornarDTO() {
        ApertureScience empresa = new ApertureScience(1L, "Aperture Science", "We do what we must", "Michigan", 1953, null);
        when(repository.findById(1L)).thenReturn(Optional.of(empresa));

        ApertureScienceDTO resultado = service.findById(1L);

        assertThat(resultado.getNombre()).isEqualTo("Aperture Science");
    }

    @Test
    void findById_deberiaLanzarExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    void save_deberiaGuardarYRetornarDTO() {
        ApertureScienceDTO dto = new ApertureScienceDTO(null, "Aperture Science", "We do what we must", "Michigan", 1953);
        ApertureScience saved = new ApertureScience(1L, "Aperture Science", "We do what we must", "Michigan", 1953, null);
        when(repository.save(any())).thenReturn(saved);

        ApertureScienceDTO resultado = service.save(dto);

        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    void delete_deberiaLlamarAlRepositorio() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}