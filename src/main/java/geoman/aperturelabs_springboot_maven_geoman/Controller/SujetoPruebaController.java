package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sujeto-prueba")
public class SujetoPruebaController {

    @Autowired
    private SujetoPruebaService service;

    @GetMapping
    public List<SujetoPruebaDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SujetoPruebaDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public SujetoPruebaDTO save(@RequestBody SujetoPruebaDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public SujetoPruebaDTO update(@PathVariable Long id, @RequestBody SujetoPruebaDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}