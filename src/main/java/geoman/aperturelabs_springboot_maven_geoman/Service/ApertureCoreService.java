package geoman.aperturelabs_springboot_maven_geoman.Service;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Model.*;
import geoman.aperturelabs_springboot_maven_geoman.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApertureCoreService {

    @Autowired
    private ApertureCoreRepository repository;

    public List<ApertureCoreDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ApertureCoreDTO findById(Long id) {
        ApertureCore entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApertureCore no encontrado con id: " + id));
        return toDTO(entity);
    }

    public ApertureCoreDTO save(ApertureCoreDTO dto) {
        ApertureCore entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public ApertureCoreDTO update(Long id, ApertureCoreDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApertureCore no encontrado con id: " + id));
        ApertureCore entity = toEntity(dto);
        entity.setId(id);
        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ApertureCoreDTO toDTO(ApertureCore entity) {
        return new ApertureCoreDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getPersonalidad(),
                entity.getEstado()
        );
    }

    private ApertureCore toEntity(ApertureCoreDTO dto) {
        ApertureCore entity = new ApertureCore();
        entity.setNombre(dto.getNombre());
        entity.setPersonalidad(dto.getPersonalidad());
        entity.setEstado(dto.getEstado());
        return entity;
    }
}