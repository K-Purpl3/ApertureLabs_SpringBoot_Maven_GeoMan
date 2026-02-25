package geoman.aperturelabs_springboot_maven_geoman.Service;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Model.*;
import geoman.aperturelabs_springboot_maven_geoman.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SujetoPruebaService {

    @Autowired
    private SujetoPruebaRepository repository;

    @Autowired
    private ApertureCoreRepository apertureCoreRepository;

    public List<SujetoPruebaDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SujetoPruebaDTO findById(Long id) {
        SujetoPrueba entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SujetoPrueba no encontrado con id: " + id));
        return toDTO(entity);
    }

    public SujetoPruebaDTO save(SujetoPruebaDTO dto) {
        SujetoPrueba entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public SujetoPruebaDTO update(Long id, SujetoPruebaDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SujetoPrueba no encontrado con id: " + id));
        SujetoPrueba entity = toEntity(dto);
        entity.setId(id);
        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private SujetoPruebaDTO toDTO(SujetoPrueba entity) {
        return new SujetoPruebaDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getEstado(),
                entity.getNumeroCaso(),
                entity.getApertureCore() != null ? entity.getApertureCore().getId() : null
        );
    }

    private SujetoPrueba toEntity(SujetoPruebaDTO dto) {
        SujetoPrueba entity = new SujetoPrueba();
        entity.setNombre(dto.getNombre());
        entity.setEstado(dto.getEstado());
        entity.setNumeroCaso(dto.getNumeroCaso());
        if (dto.getApertureCoreId() != null) {
            ApertureCore core = apertureCoreRepository.findById(dto.getApertureCoreId())
                    .orElseThrow(() -> new RuntimeException("ApertureCore no encontrado con id: " + dto.getApertureCoreId()));
            entity.setApertureCore(core);
        }
        return entity;
    }
}