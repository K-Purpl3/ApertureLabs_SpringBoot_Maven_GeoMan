package geoman.aperturelabs_springboot_maven_geoman.Repository;

import geoman.aperturelabs_springboot_maven_geoman.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApertureScienceRepository extends JpaRepository<ApertureScience, Long> {
}