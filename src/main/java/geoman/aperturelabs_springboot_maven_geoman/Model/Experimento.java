package geoman.aperturelabs_springboot_maven_geoman.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experimento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String resultado; // "exitoso", "fallido", "en progreso"
    private String camara; // numero o nombre de la camara de prueba

    @Column(name = "supervisor_id")
    private Long supervisorId;

    @ManyToOne
    @JoinColumn(name = "sujeto_prueba_id")
    private SujetoPrueba sujetoPrueba;
}