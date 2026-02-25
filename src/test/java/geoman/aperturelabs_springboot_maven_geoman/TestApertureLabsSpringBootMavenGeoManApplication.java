package geoman.aperturelabs_springboot_maven_geoman;

import org.springframework.boot.SpringApplication;

public class TestApertureLabsSpringBootMavenGeoManApplication {

    public static void main(String[] args) {
        SpringApplication.from(ApertureLabsSpringBootMavenGeoManApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
