package geoman.aperturelabs_springboot_maven_geoman;

import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureCore;
import geoman.aperturelabs_springboot_maven_geoman.Model.SujetoPrueba;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureCoreRepository;
import geoman.aperturelabs_springboot_maven_geoman.Repository.SujetoPruebaRepository;
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
class SujetoPruebaRepositoryTest {

    @Autowired
    private SujetoPruebaRepository sujetoPruebaRepository;

    @Autowired
    private ApertureCoreRepository apertureCoreRepository;

    private ApertureCore core;

    @BeforeEach
    void setUp() {
        sujetoPruebaRepository.deleteAll();
        apertureCoreRepository.deleteAll();
        core = apertureCoreRepository.save(
                new ApertureCore(null, "GLaDOS", "Calculadora y siniestra", "activo", null, null)
        );
    }

    @Test
    void save_deberiaGuardarSujetoSinCore() {
        SujetoPrueba sujeto = new SujetoPrueba(null, "Chell", "activo", 1, null, null);

        SujetoPrueba guardado = sujetoPruebaRepository.save(sujeto);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Chell");
        assertThat(guardado.getEstado()).isEqualTo("activo");
        assertThat(guardado.getNumeroCaso()).isEqualTo(1);
        assertThat(guardado.getApertureCore()).isNull();
    }

    @Test
    void save_deberiaGuardarSujetoConCore() {
        SujetoPrueba sujeto = new SujetoPrueba(null, "Chell", "activo", 1, core, null);

        SujetoPrueba guardado = sujetoPruebaRepository.save(sujeto);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getApertureCore()).isNotNull();
        assertThat(guardado.getApertureCore().getId()).isEqualTo(core.getId());
    }

    @Test
    void findById_deberiaEncontrarSujetoPorId() {
        SujetoPrueba sujeto = sujetoPruebaRepository.save(
                new SujetoPrueba(null, "Chell", "escapado", 1, null, null)
        );

        Optional<SujetoPrueba> resultado = sujetoPruebaRepository.findById(sujeto.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Chell");
        assertThat(resultado.get().getEstado()).isEqualTo("escapado");
    }

    @Test
    void findById_deberiaRetornarVacioSiNoExiste() {
        Optional<SujetoPrueba> resultado = sujetoPruebaRepository.findById(9999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findAll_deberiaRetornarTodosLosSujetos() {
        sujetoPruebaRepository.save(new SujetoPrueba(null, "Chell", "escapado", 1, null, null));
        sujetoPruebaRepository.save(new SujetoPrueba(null, "Doug Rattmann", "fallecido", 2, null, null));

        List<SujetoPrueba> resultado = sujetoPruebaRepository.findAll();

        assertThat(resultado).hasSize(2);
    }

    @Test
    void deleteById_deberiaEliminarSujeto() {
        SujetoPrueba sujeto = sujetoPruebaRepository.save(
                new SujetoPrueba(null, "Chell", "activo", 1, null, null)
        );

        sujetoPruebaRepository.deleteById(sujeto.getId());

        assertThat(sujetoPruebaRepository.findById(sujeto.getId())).isEmpty();
    }

    @Test
    void update_deberiaActualizarEstadoDeSujeto() {
        SujetoPrueba sujeto = sujetoPruebaRepository.save(
                new SujetoPrueba(null, "Chell", "activo", 1, null, null)
        );

        sujeto.setEstado("escapado");
        SujetoPrueba actualizado = sujetoPruebaRepository.save(sujeto);

        assertThat(actualizado.getEstado()).isEqualTo("escapado");
    }
}
