package geoman.aperturelabs_springboot_maven_geoman;

import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureCore;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureCoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class ApertureCoreRepositoryTest {

    @Autowired
    private ApertureCoreRepository repository;

    @BeforeEach
    void limpiarBD() {
        repository.deleteAll();
    }

    @Test
    void save_deberiaGuardarApertureCore() {
        ApertureCore core = new ApertureCore(null, "Wheatley", "Estupido pero optimista", "activo", null, null);

        ApertureCore guardado = repository.save(core);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Wheatley");
        assertThat(guardado.getPersonalidad()).isEqualTo("Estupido pero optimista");
        assertThat(guardado.getEstado()).isEqualTo("activo");
    }

    @Test
    void findById_deberiaEncontrarCorePorId() {
        ApertureCore core = repository.save(
                new ApertureCore(null, "GLaDOS", "Calculadora y siniestra", "activo", null, null)
        );

        Optional<ApertureCore> resultado = repository.findById(core.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("GLaDOS");
    }

    @Test
    void findById_deberiaRetornarVacioSiNoExiste() {
        Optional<ApertureCore> resultado = repository.findById(9999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findAll_deberiaRetornarTodosLosCores() {
        repository.save(new ApertureCore(null, "Wheatley", "Estupido", "activo", null, null));
        repository.save(new ApertureCore(null, "Space Core", "Obsesionado con el espacio", "en orbita", null, null));
        repository.save(new ApertureCore(null, "Fact Core", "Lleno de datos", "activo", null, null));

        List<ApertureCore> resultado = repository.findAll();

        assertThat(resultado).hasSize(3);
    }

    @Test
    void deleteById_deberiaEliminarCore() {
        ApertureCore core = repository.save(
                new ApertureCore(null, "Wheatley", "Estupido", "activo", null, null)
        );

        repository.deleteById(core.getId());

        assertThat(repository.findById(core.getId())).isEmpty();
    }

    @Test
    void update_deberiaActualizarEstadoDelCore() {
        ApertureCore core = repository.save(
                new ApertureCore(null, "Wheatley", "Estupido", "activo", null, null)
        );

        core.setEstado("destruido");
        ApertureCore actualizado = repository.save(core);

        assertThat(actualizado.getEstado()).isEqualTo("destruido");
    }
}
