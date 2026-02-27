package geoman.aperturelabs_springboot_maven_geoman;

import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureCore;
import geoman.aperturelabs_springboot_maven_geoman.Model.Experimento;
import geoman.aperturelabs_springboot_maven_geoman.Model.SujetoPrueba;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureCoreRepository;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ExperimentoRepository;
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
class ExperimentoRepositoryTest {

    @Autowired
    private ExperimentoRepository experimentoRepository;

    @Autowired
    private SujetoPruebaRepository sujetoPruebaRepository;

    @Autowired
    private ApertureCoreRepository apertureCoreRepository;

    private SujetoPrueba sujetoPrueba;

    @BeforeEach
    void setUp() {
        experimentoRepository.deleteAll();
        sujetoPruebaRepository.deleteAll();
        apertureCoreRepository.deleteAll();

        ApertureCore core = apertureCoreRepository.save(
                new ApertureCore(null, "GLaDOS", "Siniestra", "activo", null, null)
        );
        sujetoPrueba = sujetoPruebaRepository.save(
                new SujetoPrueba(null, "Chell", "activo", 1, core, null)
        );
    }

    @Test
    void save_deberiaGuardarExperimentoSinSujeto() {
        Experimento experimento = new Experimento(null, "Test Gel Portal", "Prueba de gel azul", "en progreso", "Camara-01", 1L, null);

        Experimento guardado = experimentoRepository.save(experimento);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Test Gel Portal");
        assertThat(guardado.getResultado()).isEqualTo("en progreso");
        assertThat(guardado.getCamara()).isEqualTo("Camara-01");
        assertThat(guardado.getSujetoPrueba()).isNull();
    }

    @Test
    void save_deberiaGuardarExperimentoConSujeto() {
        Experimento experimento = new Experimento(null, "Test Gel Portal", "Prueba de gel azul", "en progreso", "Camara-01", 1L, sujetoPrueba);

        Experimento guardado = experimentoRepository.save(experimento);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getSujetoPrueba()).isNotNull();
        assertThat(guardado.getSujetoPrueba().getId()).isEqualTo(sujetoPrueba.getId());
    }

    @Test
    void findById_deberiaEncontrarExperimentoPorId() {
        Experimento experimento = experimentoRepository.save(
                new Experimento(null, "Turret Test", "Prueba de torretas", "exitoso", "Camara-19", 2L, sujetoPrueba)
        );

        Optional<Experimento> resultado = experimentoRepository.findById(experimento.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Turret Test");
        assertThat(resultado.get().getResultado()).isEqualTo("exitoso");
    }

    @Test
    void findById_deberiaRetornarVacioSiNoExiste() {
        Optional<Experimento> resultado = experimentoRepository.findById(9999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findAll_deberiaRetornarTodosLosExperimentos() {
        experimentoRepository.save(new Experimento(null, "Turret Test", "Torretas", "exitoso", "Camara-19", 1L, sujetoPrueba));
        experimentoRepository.save(new Experimento(null, "Gel Test", "Gel azul", "fallido", "Camara-07", 2L, sujetoPrueba));

        List<Experimento> resultado = experimentoRepository.findAll();

        assertThat(resultado).hasSize(2);
    }

    @Test
    void deleteById_deberiaEliminarExperimento() {
        Experimento experimento = experimentoRepository.save(
                new Experimento(null, "Turret Test", "Torretas", "exitoso", "Camara-19", 1L, sujetoPrueba)
        );

        experimentoRepository.deleteById(experimento.getId());

        assertThat(experimentoRepository.findById(experimento.getId())).isEmpty();
    }

    @Test
    void update_deberiaActualizarResultadoDeExperimento() {
        Experimento experimento = experimentoRepository.save(
                new Experimento(null, "Gel Test", "Prueba gel", "en progreso", "Camara-07", 1L, sujetoPrueba)
        );

        experimento.setResultado("exitoso");
        Experimento actualizado = experimentoRepository.save(experimento);

        assertThat(actualizado.getResultado()).isEqualTo("exitoso");
    }
}
