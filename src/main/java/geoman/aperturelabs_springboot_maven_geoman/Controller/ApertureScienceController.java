package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aperture-science")
public class ApertureScienceController {

    @Autowired
    private ApertureScienceService service;

    @GetMapping
    public List<ApertureScienceDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ApertureScienceDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ApertureScienceDTO save(@RequestBody ApertureScienceDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ApertureScienceDTO update(@PathVariable Long id, @RequestBody ApertureScienceDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}