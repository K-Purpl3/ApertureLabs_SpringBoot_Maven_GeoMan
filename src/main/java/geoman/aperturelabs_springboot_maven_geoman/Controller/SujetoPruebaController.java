package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sujeto-prueba")
@Tag(name = "Sujeto Prueba", description = "Gestión de Sujetos de Prueba")
public class SujetoPruebaController {

    @Autowired
    private SujetoPruebaService service;

    @GetMapping
    @Operation(summary = "Obtener todos los sujetos de prueba")
    public List<SujetoPruebaDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sujeto de prueba por ID")
    public SujetoPruebaDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo sujeto de prueba")
    public SujetoPruebaDTO save(@RequestBody SujetoPruebaDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sujeto de prueba")
    public SujetoPruebaDTO update(@PathVariable Long id, @RequestBody SujetoPruebaDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sujeto de prueba")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}