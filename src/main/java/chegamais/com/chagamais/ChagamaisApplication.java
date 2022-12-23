package chegamais.com.chagamais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class ChagamaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChagamaisApplication.class, args);
		System.out.println("Hello");
	}
}
