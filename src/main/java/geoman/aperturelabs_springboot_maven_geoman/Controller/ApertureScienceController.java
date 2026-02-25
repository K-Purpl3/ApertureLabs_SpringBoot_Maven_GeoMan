package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/aperture-science")

@Tag(name = "Aperture Science", description = "Gestión de Aperture Science")
public class ApertureScienceController {

    @Autowired
    private ApertureScienceService service;

    @GetMapping
    @Operation(summary = "Obtener todas las empresas")
    public List<ApertureScienceDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empresa por ID")
    public ApertureScienceDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear nueva empresa")
    public ApertureScienceDTO save(@RequestBody ApertureScienceDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empresa")
    public ApertureScienceDTO update(@PathVariable Long id, @RequestBody ApertureScienceDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empresa")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}