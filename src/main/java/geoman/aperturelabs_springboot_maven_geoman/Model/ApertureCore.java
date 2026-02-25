package geoman.aperturelabs_springboot_maven_geoman.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "aperture_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApertureCore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // "Wheatley", "Space Core", "Fact Core"...
    private String personalidad;
    private String estado; // "activo", "destruido", "en orbita"

    @OneToOne(mappedBy = "apertureCore")
    private Empleado empleado;

    @OneToMany(mappedBy = "apertureCore", cascade = CascadeType.ALL)
    private List<SujetoPrueba> sujetosPrueba;
}