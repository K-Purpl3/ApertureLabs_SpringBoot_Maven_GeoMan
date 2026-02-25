package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experimento")
public class ExperimentoController {

    @Autowired
    private ExperimentoService service;

    @GetMapping
    public List<ExperimentoDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ExperimentoDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ExperimentoDTO save(@RequestBody ExperimentoDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ExperimentoDTO update(@PathVariable Long id, @RequestBody ExperimentoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}