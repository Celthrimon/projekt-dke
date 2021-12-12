package at.jku.postservice;

import at.jku.postservice.model.Hashtag;
import at.jku.postservice.model.Post;
import at.jku.postservice.model.User;
import at.jku.postservice.repository.HashtagRepository;
import at.jku.postservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class PostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(UserRepository userRepository, HashtagRepository hashtagRepository) {
        return args -> {
            userRepository.deleteAll();

            User mario = new User("Mario");
            User beni = new User("Beni");
            User felix = new User("Felix");
            User max = new User("Max");

            Hashtag inflation = new Hashtag("#inflation");
            Hashtag corona = new Hashtag("#corona");

            userRepository.save(mario);
            userRepository.save(beni);
            userRepository.save(felix);
            userRepository.save(max);

            hashtagRepository.save(inflation);
            hashtagRepository.save(corona);

            Post post1;
        };
    }
}
