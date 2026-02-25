package geoman.aperturelabs_springboot_maven_geoman.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApertureScienceDTO {
    private Long id;
    private String nombre;
    private String slogan;
    private String ubicacion;
    private int anioFundacion;
}
