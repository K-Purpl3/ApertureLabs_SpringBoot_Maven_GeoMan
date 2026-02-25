package geoman.aperturelabs_springboot_maven_geoman.Controller;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ceo")
public class CEOController {

    @Autowired
    private CEOService service;

    @GetMapping
    public List<CEODTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CEODTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public CEODTO save(@RequestBody CEODTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public CEODTO update(@PathVariable Long id, @RequestBody CEODTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}