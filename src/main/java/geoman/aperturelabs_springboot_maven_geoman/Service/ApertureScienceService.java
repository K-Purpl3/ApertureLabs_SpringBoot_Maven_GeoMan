package geoman.aperturelabs_springboot_maven_geoman.Service;

import geoman.aperturelabs_springboot_maven_geoman.DTO.ApertureScienceDTO;
import geoman.aperturelabs_springboot_maven_geoman.Model.ApertureScience;
import geoman.aperturelabs_springboot_maven_geoman.Repository.ApertureScienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApertureScienceService {

    @Autowired
    private ApertureScienceRepository repository;

    public List<ApertureScienceDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ApertureScienceDTO findById(Long id) {
        ApertureScience entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApertureScience no encontrada con id: " + id));
        return toDTO(entity);
    }

    public ApertureScienceDTO save(ApertureScienceDTO dto) {
        ApertureScience entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public ApertureScienceDTO update(Long id, ApertureScienceDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApertureScience no encontrada con id: " + id));
        ApertureScience entity = toEntity(dto);
        entity.setId(id);
        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ApertureScienceDTO toDTO(ApertureScience entity) {
        return new ApertureScienceDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getSlogan(),
                entity.getUbicacion(),
                entity.getAnioFundacion()
        );
    }

    private ApertureScience toEntity(ApertureScienceDTO dto) {
        ApertureScience entity = new ApertureScience();
        entity.setNombre(dto.getNombre());
        entity.setSlogan(dto.getSlogan());
        entity.setUbicacion(dto.getUbicacion());
        entity.setAnioFundacion(dto.getAnioFundacion());
        return entity;
    }
}