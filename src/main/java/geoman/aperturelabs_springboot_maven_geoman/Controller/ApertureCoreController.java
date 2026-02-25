package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aperture-core")
public class ApertureCoreController {

    @Autowired
    private ApertureCoreService service;

    @GetMapping
    public List<ApertureCoreDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ApertureCoreDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ApertureCoreDTO save(@RequestBody ApertureCoreDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ApertureCoreDTO update(@PathVariable Long id, @RequestBody ApertureCoreDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}