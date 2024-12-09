package user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EntityScan
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"user.util","merchant.service"})
public class SpringcloudMovieServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringcloudMovieServerApplication.class, args);
	}
}

