package geoman.aperturelabs_springboot_maven_geoman.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CEODTO {
    private Long id;
    private String nombre;
    private String estado;
    private int anioMandato;
    private Long empresaId;
}