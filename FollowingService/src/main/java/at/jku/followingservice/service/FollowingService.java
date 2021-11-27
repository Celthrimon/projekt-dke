package at.jku.followingservice.service;

import at.jku.followingservice.model.User;
import at.jku.followingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface FollowingService {

    List<User> findFollowedUsers(String username);

    User findByUsername(String username);

    User save(User user);
}
