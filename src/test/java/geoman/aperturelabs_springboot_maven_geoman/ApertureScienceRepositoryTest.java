package geoman.aperturelabs_springboot_maven_geoman;

import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureScience;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureScienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class ApertureScienceRepositoryTest {

    @Autowired
    private ApertureScienceRepository repository;

    @BeforeEach
    void limpiarBD() {
        repository.deleteAll();
    }

    @Test
    void save_deberiaGuardarEmpresa() {
        ApertureScience empresa = new ApertureScience(null, "Aperture Science", "We do what we must", "Michigan", 1953,
                null);

        ApertureScience guardada = repository.save(empresa);

        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getNombre()).isEqualTo("Aperture Science");
        assertThat(guardada.getSlogan()).isEqualTo("We do what we must");
        assertThat(guardada.getUbicacion()).isEqualTo("Michigan");
        assertThat(guardada.getAnioFundacion()).isEqualTo(1953);
    }

    @Test
    void findById_deberiaEncontrarEmpresaExistente() {
        ApertureScience empresa = repository.save(
                new ApertureScience(null, "Aperture Science", "We do what we must", "Michigan", 1953, null));

        Optional<ApertureScience> resultado = repository.findById(empresa.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Aperture Science");
    }

    @Test
    void findById_deberiaRetornarVacioSiNoExiste() {
        Optional<ApertureScience> resultado = repository.findById(9999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findAll_deberiaRetornarTodasLasEmpresas() {
        repository.save(new ApertureScience(null, "Aperture Science", "We do what we must", "Michigan", 1953, null));
        repository.save(new ApertureScience(null, "Aperture Labs", "For Science", "Utah", 1955, null));

        List<ApertureScience> resultado = repository.findAll();

        assertThat(resultado).hasSize(2);
    }

    @Test
    void deleteById_deberiaEliminarEmpresa() {
        ApertureScience empresa = repository.save(
                new ApertureScience(null, "Aperture Science", "We do what we must", "Michigan", 1953, null));

        repository.deleteById(empresa.getId());

        assertThat(repository.findById(empresa.getId())).isEmpty();
    }

    @Test
    void update_deberiaActualizarDatosDeEmpresa() {
        ApertureScience empresa = repository.save(
                new ApertureScience(null, "Aperture Science", "Slogan original", "Michigan", 1953, null));

        empresa.setSlogan("Science isn't about why");
        empresa.setUbicacion("Cave Johnson's Lab");
        ApertureScience actualizada = repository.save(empresa);

        assertThat(actualizada.getSlogan()).isEqualTo("Science isn't about why");
        assertThat(actualizada.getUbicacion()).isEqualTo("Cave Johnson's Lab");
    }
}
