package user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
public class SpringcloudMovieServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMovieServerApplication.class, args);
	}
}

