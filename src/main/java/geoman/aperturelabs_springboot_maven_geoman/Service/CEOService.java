package geoman.aperturelabs_springboot_maven_geoman.Service;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Model.*;
import geoman.aperturelabs_springboot_maven_geoman.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CEOService {

    @Autowired
    private CEORepository repository;

    @Autowired
    private ApertureScienceRepository apertureScienceRepository;

    public List<CEODTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CEODTO findById(Long id) {
        CEO entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CEO no encontrado con id: " + id));
        return toDTO(entity);
    }

    public CEODTO save(CEODTO dto) {
        CEO entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public CEODTO update(Long id, CEODTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CEO no encontrado con id: " + id));
        CEO entity = toEntity(dto);
        entity.setId(id);
        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private CEODTO toDTO(CEO entity) {
        return new CEODTO(
                entity.getId(),
                entity.getNombre(),
                entity.getEstado(),
                entity.getAnioMandato(),
                entity.getEmpresa() != null ? entity.getEmpresa().getId() : null
        );
    }

    private CEO toEntity(CEODTO dto) {
        CEO entity = new CEO();
        entity.setNombre(dto.getNombre());
        entity.setEstado(dto.getEstado());
        entity.setAnioMandato(dto.getAnioMandato());
        if (dto.getEmpresaId() != null) {
            ApertureScience empresa = apertureScienceRepository.findById(dto.getEmpresaId())
                    .orElseThrow(() -> new RuntimeException("ApertureScience no encontrada con id: " + dto.getEmpresaId()));
            entity.setEmpresa(empresa);
        }
        return entity;
    }
}