package at.jku.logservice;

import at.jku.logservice.repo.RepositoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LogServiceApplication {

    @Autowired
    RepositoryLog repositoryLog;


    public static void main(String[] args) {
        SpringApplication.run(LogServiceApplication.class, args);
    }


}
