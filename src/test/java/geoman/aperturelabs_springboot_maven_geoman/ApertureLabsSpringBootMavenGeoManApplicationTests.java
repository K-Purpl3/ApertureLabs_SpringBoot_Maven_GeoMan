package geoman.aperturelabs_springboot_maven_geoman;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ApertureLabsSpringBootMavenGeoManApplicationTests {

    @Test
    void contextLoads() {
    }

}
