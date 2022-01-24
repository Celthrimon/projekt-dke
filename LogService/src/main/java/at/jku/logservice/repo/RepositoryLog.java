package at.jku.logservice.repo;

import at.jku.logservice.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositoryLog extends MongoRepository<Log, String> {

}
