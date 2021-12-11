package at.jku.followingservice;

import at.jku.followingservice.model.Hashtag;
import at.jku.followingservice.model.User;
import at.jku.followingservice.repository.HashtagRepository;
import at.jku.followingservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class FollowingServiceApplication {

    private final static Logger log = LoggerFactory.getLogger(FollowingServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FollowingServiceApplication.class, args);
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

            mario = userRepository.findByUsername(mario.getUsername());
            mario.followUser(beni);
            mario.followUser(felix);
            mario.followUser(max);
            mario.followHashtag(inflation);
            userRepository.save(mario);

            beni = userRepository.findByUsername(beni.getUsername());
            beni.followUser(max);
            beni.followUser(mario);
            beni.followHashtag(inflation);
            beni.followHashtag(corona);
            userRepository.save(beni);
            log.info("The following users follow Max: ");
            List<User> followers = userRepository.findByFollowedUsersUsername(max.getUsername());
            followers.forEach(u -> log.info("\t" + u.getUsername()));
            log.info("\n");
            log.info("The following users follow the hashtag #inflation: ");
            List<User> hashtagFollowers = userRepository.findByFollowedHashtagsTitle(inflation.getTitle());
            hashtagFollowers.forEach(u -> log.info("\t" + u.getUsername()));

            /*log.info("\n");
            log.info("After unfollows the following users follow Max: ");
            userRepository.unfollowUser(mario.getUsername(), max.getUsername());
            List<User> followersNow = userRepository.findByFollowersUsername(max.getUsername());
            followersNow.stream().forEach(u -> log.info("\t" + u.getUsername()));

            log.info("\n");
            log.info("After unfollows the following users follow hashtag #infaltion: ");
            userRepository.unfollowHashtag(beni.getUsername(), inflation.getTitle());
            List<User> hashtagFollowersNow = userRepository.findByHashtagFollowersTitle(inflation.getTitle());
            hashtagFollowersNow.stream().forEach(u -> log.info("\t" + u.getUsername()));
            log.info("\n");
            log.info("Mario follows the following hashtags: ");
            mario.followedHashtags.forEach(h -> log.info("\t" + h.getTitle()));*/
        };
    }
}
