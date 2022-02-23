package dk.vdw.familymanager;

import dk.vdw.familymanager.configuration.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class FamilyManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyManagerApplication.class, args);
    }

}
