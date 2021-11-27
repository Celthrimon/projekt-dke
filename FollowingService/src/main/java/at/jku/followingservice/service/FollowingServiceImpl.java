package at.jku.followingservice.service;

import at.jku.followingservice.model.User;
import at.jku.followingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingServiceImpl implements FollowingService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findFollowedUsers(String username) {
        return userRepository.findByFollowersUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
