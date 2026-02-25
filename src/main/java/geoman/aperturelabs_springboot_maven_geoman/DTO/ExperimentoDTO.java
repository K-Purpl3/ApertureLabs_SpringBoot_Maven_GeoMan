package geoman.aperturelabs_springboot_maven_geoman.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String resultado;
    private String camara;
    private Long supervisorId;
    private Long sujetoPruebaId;
}