package geoman.aperturelabs_springboot_maven_geoman.Service;

import geoman.aperturelabs_springboot_maven_geoman.DTO.*;
import geoman.aperturelabs_springboot_maven_geoman.Model.*;
import geoman.aperturelabs_springboot_maven_geoman.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperimentoService {

    @Autowired
    private ExperimentoRepository repository;

    @Autowired
    private SujetoPruebaRepository sujetoPruebaRepository;

    public List<ExperimentoDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExperimentoDTO findById(Long id) {
        Experimento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experimento no encontrado con id: " + id));
        return toDTO(entity);
    }

    public ExperimentoDTO save(ExperimentoDTO dto) {
        Experimento entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public ExperimentoDTO update(Long id, ExperimentoDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experimento no encontrado con id: " + id));
        Experimento entity = toEntity(dto);
        entity.setId(id);
        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ExperimentoDTO toDTO(Experimento entity) {
        return new ExperimentoDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getResultado(),
                entity.getCamara(),
                entity.getSupervisorId(),
                entity.getSujetoPrueba() != null ? entity.getSujetoPrueba().getId() : null
        );
    }

    private Experimento toEntity(ExperimentoDTO dto) {
        Experimento entity = new Experimento();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setResultado(dto.getResultado());
        entity.setCamara(dto.getCamara());
        entity.setSupervisorId(dto.getSupervisorId());
        if (dto.getSujetoPruebaId() != null) {
            SujetoPrueba sujeto = sujetoPruebaRepository.findById(dto.getSujetoPruebaId())
                    .orElseThrow(() -> new RuntimeException("SujetoPrueba no encontrado con id: " + dto.getSujetoPruebaId()));
            entity.setSujetoPrueba(sujeto);
        }
        return entity;
    }
}