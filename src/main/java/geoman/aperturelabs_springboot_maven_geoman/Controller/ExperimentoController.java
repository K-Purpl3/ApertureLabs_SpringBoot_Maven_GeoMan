package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experimento")
@Tag(name = "Experimento", description = "Gestión de Experimentos")
public class ExperimentoController {

    @Autowired
    private ExperimentoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los experimentos")
    public List<ExperimentoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener experimento por ID")
    public ExperimentoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo experimento")
    public ExperimentoDTO save(@RequestBody ExperimentoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar experimento")
    public ExperimentoDTO update(@PathVariable Long id, @RequestBody ExperimentoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar experimento")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}