package geoman.aperturelabs_springboot_maven_geoman.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "sujeto_prueba")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SujetoPrueba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String estado; // "activo", "fallecido", "escapado"
    private int numeroCaso;

    @ManyToOne
    @JoinColumn(name = "aperture_core_id")
    private ApertureCore apertureCore;

    @OneToMany(mappedBy = "sujetoPrueba", cascade = CascadeType.ALL)
    private List<Experimento> experimentos;
}