package geoman.aperturelabs_springboot_maven_geoman.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String rol; // "Supervisor", "Diseñador de Pruebas", "QA", "Becario", "R&D", "Secretaria"
    private String departamento;
    private String estado; // "activo", "fallecido", "desaparecido"

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aperture_core_id")
    private ApertureCore apertureCore;
}