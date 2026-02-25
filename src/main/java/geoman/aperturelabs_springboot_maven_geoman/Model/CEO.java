package geoman.aperturelabs_springboot_maven_geoman.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Table(name = "ceo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CEO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String estado; // "vivo", "muerto", "IA"

    @Column(name = "anio_mandato")
    private int anioMandato;

    //one to one, un CEO para una empresa
    @OneToOne
    @JoinColumn(name = "empresa_id")
    private ApertureScience empresa;
}