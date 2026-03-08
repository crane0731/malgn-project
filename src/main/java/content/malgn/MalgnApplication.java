package content.malgn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MalgnApplication {

	public static void main(String[] args) {
		SpringApplication.run(MalgnApplication.class, args);
	}

}
