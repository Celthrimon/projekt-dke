package at.jku.postservice;

import at.jku.postservice.model.Hashtag;
import at.jku.postservice.model.Post;
import at.jku.postservice.model.User;
import at.jku.postservice.repository.HashtagRepository;
import at.jku.postservice.repository.PostRepository;
import at.jku.postservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class PostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(PostRepository postRepository, UserRepository userRepository, HashtagRepository hashtagRepository) {
        return args -> {
            userRepository.deleteAll();
            postRepository.deleteAll();
            hashtagRepository.deleteAll();

            User mario = new User("Mario");
            User beni = new User("Beni");
            User felix = new User("Felix");
            User max = new User("Max");
            User general = new User("General");

            Hashtag services = new Hashtag("#services");
            Hashtag aufwandsschaetzung = new Hashtag("#aufwandsschätzung");
            Hashtag architecture = new Hashtag("#architecture");

            userRepository.save(mario);
            userRepository.save(beni);
            userRepository.save(felix);
            userRepository.save(max);
            userRepository.save(general);

            hashtagRepository.save(services);
            hashtagRepository.save(aufwandsschaetzung);
            hashtagRepository.save(architecture);

            Post postAufwandsschaetzung1 = new Post("#aufwandsschätzung \n\nEinarbeiten in sämtliche Technologien \n-> geschätzt: 100h \n-> tatsächlich: 60h", userRepository.findById(general.getUserName()).get(), LocalDateTime.now(), "\uD83E\uDD28");
            Post postAufwandsschaetzung2 = new Post("#aufwandsschätzung \n\nProjekte anlegen, Git initialisieren, Docker Files erstellen, Modelle erstellen, \n" +
                    "Schnittstellen definieren, Kommunikation zwischen Services und Datenbanken, Server \n-> geschätzt: 45h \n-> tatsächlich: 35h", userRepository.findById(general.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE0A");
            Post postAufwandsschaetzung3 = new Post("#aufwandsschätzung \n\nRest Schnittstellen für alle Services anlegen \n-> geschätzt: 80h \n-> tatsächlich: 60h", userRepository.findById(general.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE42");
            Post postAufwandsschaetzung4 = new Post("#aufwandsschätzung \n\nZusammenführen der Rest Schnittstellen (Proxy) \n-> geschätzt: 20h \n-> tatsächlich: 15h", userRepository.findById(general.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE0F");
            Post postAufwandsschaetzung5 = new Post("#aufwandsschätzung \n\nTests für Rest Schnittstellen erstellen \n-> geschätzt: 100h \n-> tatsächlich: 35h", userRepository.findById(general.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE2C");
            Post postAufwandsschaetzung6 = new Post("#aufwandsschätzung \n\nFrontend entwickeln \n-> geschätzt: 150h \n-> tatsächlich: 85h", userRepository.findById(general.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE44");

            postAufwandsschaetzung1.addHashtag(aufwandsschaetzung);
            postAufwandsschaetzung2.addHashtag(aufwandsschaetzung);
            postAufwandsschaetzung3.addHashtag(aufwandsschaetzung);
            postAufwandsschaetzung4.addHashtag(aufwandsschaetzung);
            postAufwandsschaetzung5.addHashtag(aufwandsschaetzung);
            postAufwandsschaetzung6.addHashtag(aufwandsschaetzung);

            postRepository.save(postAufwandsschaetzung1);
            postRepository.save(postAufwandsschaetzung2);
            postRepository.save(postAufwandsschaetzung3);
            postRepository.save(postAufwandsschaetzung4);
            postRepository.save(postAufwandsschaetzung5);
            postRepository.save(postAufwandsschaetzung6);

            Post postService1 = new Post("#services \n\nFOLLOWINGSERIVCE\n-> Java Spring Boot\n-> Graphen Datenbank\n-> NEO4J\n", userRepository.findById(mario.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE04");
            Post postService2 = new Post("#services \n\nPOSTSERVICE \n-> Java Spring Boot\n-> Relationale Datenbank\n-> Postgres\n", userRepository.findById(felix.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE01");
            Post postService3 = new Post("#services \n\nUSERSERVICE \n-> Java Spring Boot\n-> Relationale Datenbank\n-> Postgres\n", userRepository.findById(beni.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE42");
            Post postService4 = new Post("#services \n\nLOGSERVICE \n-> Java Spring Boot\n-> Relationale Datenbank\n-> Postgres\n", userRepository.findById(max.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE05");
            Post postService5 = new Post("#services \n\nNOTIFICATIONERIVCE \n-> Java Spring Boot\n", userRepository.findById(max.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDE0A");

            postService1.addHashtag(services);
            postService2.addHashtag(services);
            postService3.addHashtag(services);
            postService4.addHashtag(services);
            postService5.addHashtag(services);

            postRepository.save(postService1);
            postRepository.save(postService2);
            postRepository.save(postService3);
            postRepository.save(postService4);
            postRepository.save(postService5);

            Post postArchitecture = new Post("#architecture \n\nhttps://i.imgur.com/de5WxtBh.jpg", userRepository.findById(general.getUserName()).get(), LocalDateTime.now(), "\uD83D\uDDA5");
            postArchitecture.addHashtag(architecture);
            postRepository.save(postArchitecture);
        };
    }
}
