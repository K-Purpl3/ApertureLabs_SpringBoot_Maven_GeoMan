package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleado")
@Tag(name = "Empleado", description = "Gestión de Empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los empleados")
    public List<EmpleadoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID")
    public EmpleadoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo empleado")
    public EmpleadoDTO save(@RequestBody EmpleadoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado")
    public EmpleadoDTO update(@PathVariable Long id, @RequestBody EmpleadoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}