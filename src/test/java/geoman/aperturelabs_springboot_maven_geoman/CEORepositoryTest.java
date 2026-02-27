package geoman.aperturelabs_springboot_maven_geoman;

import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureScience;
import geoman.aperturelabs_springboot_maven_geoman.Model.CEO;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureScienceRepository;
import geoman.aperturelabs_springboot_maven_geoman.Repository.CEORepository;
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
class CEORepositoryTest {

    @Autowired
    private CEORepository ceoRepository;

    @Autowired
    private ApertureScienceRepository empresaRepository;

    private ApertureScience empresa;

    @BeforeEach
    void setUp() {
        ceoRepository.deleteAll();
        empresaRepository.deleteAll();
        empresa = empresaRepository.save(
                new ApertureScience(null, "Aperture Science", "We do what we must", "Michigan", 1953, null));
    }

    @Test
    void save_deberiaGuardarCEOConEmpresa() {
        CEO ceo = new CEO(null, "Cave Johnson", "vivo", 1953, empresa);

        CEO guardado = ceoRepository.save(ceo);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Cave Johnson");
        assertThat(guardado.getEstado()).isEqualTo("vivo");
        assertThat(guardado.getEmpresa()).isNotNull();
        assertThat(guardado.getEmpresa().getId()).isEqualTo(empresa.getId());
    }

    @Test
    void findById_deberiaEncontrarCEOPorId() {
        CEO ceo = ceoRepository.save(new CEO(null, "Cave Johnson", "vivo", 1953, empresa));

        Optional<CEO> resultado = ceoRepository.findById(ceo.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Cave Johnson");
    }

    @Test
    void findById_deberiaRetornarVacioSiNoExiste() {
        Optional<CEO> resultado = ceoRepository.findById(9999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findAll_deberiaRetornarTodosLosCEOs() {
        ApertureScience empresa2 = empresaRepository.save(
                new ApertureScience(null, "Aperture Labs", "For Science", "Utah", 1955, null));
        ceoRepository.save(new CEO(null, "Cave Johnson", "vivo", 1953, empresa));
        ceoRepository.save(new CEO(null, "Caroline", "IA", 1994, empresa2));

        List<CEO> resultado = ceoRepository.findAll();

        assertThat(resultado).hasSize(2);
    }

    @Test
    void deleteById_deberiaEliminarCEO() {
        CEO ceo = ceoRepository.save(new CEO(null, "Cave Johnson", "vivo", 1953, empresa));

        ceoRepository.deleteById(ceo.getId());

        assertThat(ceoRepository.findById(ceo.getId())).isEmpty();
    }

    @Test
    void update_deberiaActualizarEstadoCEO() {
        CEO ceo = ceoRepository.save(new CEO(null, "Cave Johnson", "vivo", 1953, empresa));

        ceo.setEstado("muerto");
        CEO actualizado = ceoRepository.save(ceo);

        assertThat(actualizado.getEstado()).isEqualTo("muerto");
    }
}
