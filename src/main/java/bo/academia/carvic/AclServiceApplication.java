package bo.academia.carvic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AclServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AclServiceApplication.class, args);
	}

}
