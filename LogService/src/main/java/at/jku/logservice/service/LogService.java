package at.jku.logservice.service;

import at.jku.logservice.model.Log;
import at.jku.logservice.repo.RepositoryLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    /*@Autowired
    LogRepository logRepository;*/

    @Autowired
    RepositoryLog repositoryLog;

    public String saveLog(Log log) {

        //return logRepository.saveLog(log);
        return repositoryLog.save(log).getMessage();
    }

    public List<Log> getAllLogs() {


        //return logRepository.getAllLogs();
        return repositoryLog.findAll();
    }

    public List<Log> getAllLogsBy(String filter) {
        return repositoryLog.findAll().stream().filter(s -> s.getMessage().contains(filter)).collect(Collectors.toList());
    }


}
