package geoman.aperturelabs_springboot_maven_geoman.Service;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Model.*;
import geoman.aperturelabs_springboot_maven_geoman.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repository;

    @Autowired
    private ApertureCoreRepository apertureCoreRepository;

    public List<EmpleadoDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EmpleadoDTO findById(Long id) {
        Empleado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        return toDTO(entity);
    }

    public EmpleadoDTO save(EmpleadoDTO dto) {
        Empleado entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public EmpleadoDTO update(Long id, EmpleadoDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        Empleado entity = toEntity(dto);
        entity.setId(id);
        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private EmpleadoDTO toDTO(Empleado entity) {
        return new EmpleadoDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getRol(),
                entity.getDepartamento(),
                entity.getEstado(),
                entity.getApertureCore() != null ? entity.getApertureCore().getId() : null
        );
    }

    private Empleado toEntity(EmpleadoDTO dto) {
        Empleado entity = new Empleado();
        entity.setNombre(dto.getNombre());
        entity.setRol(dto.getRol());
        entity.setDepartamento(dto.getDepartamento());
        entity.setEstado(dto.getEstado());
        if (dto.getApertureCoreId() != null) {
            ApertureCore core = apertureCoreRepository.findById(dto.getApertureCoreId())
                    .orElseThrow(() -> new RuntimeException("ApertureCore no encontrado con id: " + dto.getApertureCoreId()));
            entity.setApertureCore(core);
        }
        return entity;
    }
}