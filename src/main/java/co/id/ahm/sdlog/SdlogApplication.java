package co.id.ahm.sdlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "co.id.ahm.sdlog")
@EnableJpaRepositories(basePackages = "co.id.ahm.sdlog.dao", entityManagerFactoryRef = "entityManagerFactory")
public class SdlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdlogApplication.class, args);
	}

}
