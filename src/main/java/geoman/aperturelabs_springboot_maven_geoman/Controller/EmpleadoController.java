package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping
    public List<EmpleadoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EmpleadoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public EmpleadoDTO save(@RequestBody EmpleadoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public EmpleadoDTO update(@PathVariable Long id, @RequestBody EmpleadoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}