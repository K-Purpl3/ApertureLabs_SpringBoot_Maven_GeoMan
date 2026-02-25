package geoman.aperturelabs_springboot_maven_geoman.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Entity
@Table(name = "aperture_science")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApertureScience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String slogan;
    private String ubicacion;

    @Column(name = "anio_fundacion")
    private int anioFundacion;

    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL)
    private CEO ceo;
}