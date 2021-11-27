package at.jku.followingservice;

import at.jku.followingservice.model.User;
import at.jku.followingservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class FollowingServiceApplication {


    private final static Logger log = LoggerFactory.getLogger(FollowingServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FollowingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(UserRepository userRepository) {
        return args -> {
            userRepository.deleteAll();

            User mario = new User("Mario");
            User beni = new User("Beni");
            User felix = new User("Felix");
            User max = new User("Max");

            userRepository.save(mario);
            userRepository.save(beni);
            userRepository.save(felix);
            userRepository.save(max);

            mario = userRepository.findByUsername(mario.getUsername());
            mario.follow(beni);
            mario.follow(felix);
            mario.follow(max);
            userRepository.save(mario);

            beni = userRepository.findByUsername(beni.getUsername());
            beni.follow(max);
            userRepository.save(beni);
            log.info("The following users follow Max: ");
            List<User> followers = userRepository.findByFollowersUsername(max.getUsername());
            followers.stream().forEach(u -> log.info("\t" + u.getUsername()));
            mario = userRepository.findByUsername(mario.getUsername());
            userRepository.save(mario);
        };
    }
}
