package at.jku.userservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(UserServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new User("Max", "1234", "Heiss"));
            repository.save(new User("Beni", "1234", "Bachmayr"));
            repository.save(new User("Felix", "1234", "Steiner"));
            repository.save(new User("Mario", "1234", "Lanz"));
            repository.save(new User("General", "1234", "general"));
        };
    }

}
