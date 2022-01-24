package at.jku.logservice;

import at.jku.logservice.model.Log;
import at.jku.logservice.repo.LogRepository;
import at.jku.logservice.repo.RepositoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;

@SpringBootApplication
@EnableMongoRepositories
public class LogServiceApplication {

    @Autowired
    RepositoryLog repositoryLog;




    public static void main(String[] args) {
        SpringApplication.run(LogServiceApplication.class, args);
    }


}
