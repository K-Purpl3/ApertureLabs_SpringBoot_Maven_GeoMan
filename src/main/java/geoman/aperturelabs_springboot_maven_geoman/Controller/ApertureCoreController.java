package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aperture-core")
@Tag(name = "Aperture Core", description = "Gestión de Aperture Cores")
public class ApertureCoreController {

    @Autowired
    private ApertureCoreService service;

    @GetMapping
    @Operation(summary = "Obtener todos los Aperture Cores")
    public List<ApertureCoreDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Aperture Core por ID")
    public ApertureCoreDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo Aperture Core")
    public ApertureCoreDTO save(@RequestBody ApertureCoreDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Aperture Core")
    public ApertureCoreDTO update(@PathVariable Long id, @RequestBody ApertureCoreDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Aperture Core")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}