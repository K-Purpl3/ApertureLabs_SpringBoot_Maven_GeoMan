package geoman.aperturelabs_springboot_maven_geoman;

import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureCore;
import geoman.aperturelabs_springboot_maven_geoman.Model.Empleado;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureCoreRepository;
import geoman.aperturelabs_springboot_maven_geoman.Repository.EmpleadoRepository;
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
class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ApertureCoreRepository apertureCoreRepository;

    private ApertureCore core;

    @BeforeEach
    void setUp() {
        empleadoRepository.deleteAll();
        apertureCoreRepository.deleteAll();
        core = apertureCoreRepository.save(
                new ApertureCore(null, "GLaDOS", "Calculadora", "activo", null, null)
        );
    }

    @Test
    void save_deberiaGuardarEmpleadoSinCore() {
        Empleado empleado = new Empleado(null, "Doug Rattmann", "R&D", "Investigacion", "activo", null);

        Empleado guardado = empleadoRepository.save(empleado);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getNombre()).isEqualTo("Doug Rattmann");
        assertThat(guardado.getRol()).isEqualTo("R&D");
        assertThat(guardado.getDepartamento()).isEqualTo("Investigacion");
        assertThat(guardado.getEstado()).isEqualTo("activo");
        assertThat(guardado.getApertureCore()).isNull();
    }

    @Test
    void save_deberiaGuardarEmpleadoConCore() {
        Empleado empleado = new Empleado(null, "Supervisor Test", "Supervisor", "Testing", "activo", core);

        Empleado guardado = empleadoRepository.save(empleado);

        assertThat(guardado.getId()).isNotNull();
        assertThat(guardado.getApertureCore()).isNotNull();
        assertThat(guardado.getApertureCore().getId()).isEqualTo(core.getId());
    }

    @Test
    void findById_deberiaEncontrarEmpleadoPorId() {
        Empleado empleado = empleadoRepository.save(
                new Empleado(null, "Doug Rattmann", "R&D", "Investigacion", "desaparecido", null)
        );

        Optional<Empleado> resultado = empleadoRepository.findById(empleado.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Doug Rattmann");
        assertThat(resultado.get().getEstado()).isEqualTo("desaparecido");
    }

    @Test
    void findById_deberiaRetornarVacioSiNoExiste() {
        Optional<Empleado> resultado = empleadoRepository.findById(9999L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void findAll_deberiaRetornarTodosLosEmpleados() {
        empleadoRepository.save(new Empleado(null, "Doug Rattmann", "R&D", "Investigacion", "desaparecido", null));
        empleadoRepository.save(new Empleado(null, "Companion Cube Handler", "QA", "Testing", "activo", null));

        List<Empleado> resultado = empleadoRepository.findAll();

        assertThat(resultado).hasSize(2);
    }

    @Test
    void deleteById_deberiaEliminarEmpleado() {
        Empleado empleado = empleadoRepository.save(
                new Empleado(null, "Doug Rattmann", "R&D", "Investigacion", "activo", null)
        );

        empleadoRepository.deleteById(empleado.getId());

        assertThat(empleadoRepository.findById(empleado.getId())).isEmpty();
    }

    @Test
    void update_deberiaActualizarEstadoEmpleado() {
        Empleado empleado = empleadoRepository.save(
                new Empleado(null, "Doug Rattmann", "R&D", "Investigacion", "activo", null)
        );

        empleado.setEstado("desaparecido");
        Empleado actualizado = empleadoRepository.save(empleado);

        assertThat(actualizado.getEstado()).isEqualTo("desaparecido");
    }
}
