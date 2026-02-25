package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ceo")
@Tag(name = "CEO", description = "Gestión de CEOs")
public class CEOController {

    @Autowired
    private CEOService service;

    @GetMapping
    @Operation(summary = "Obtener todos los CEOs")
    public List<CEODTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener CEO por ID")
    public CEODTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo CEO")
    public CEODTO save(@RequestBody CEODTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar CEO")
    public CEODTO update(@PathVariable Long id, @RequestBody CEODTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar CEO")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}