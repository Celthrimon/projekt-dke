package at.jku.logservice.repo;

import at.jku.logservice.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LogRepository {

    @Autowired
    MongoTemplate mongoTemplate;


    public String saveLog(Log log) {
        return mongoTemplate.save(log).getMessage();
    }

    public List<Log> getAllLogs(){
        return mongoTemplate.findAll(Log.class);
    }

    public List<Log> getAllLogsBy(String filter){
        return getAllLogs().stream().filter(s-> s.getMessage().contains(filter)).collect(Collectors.toList());
    }
}
