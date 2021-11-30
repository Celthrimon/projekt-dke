package at.jku.followingservice.service;

import at.jku.followingservice.model.Hashtag;
import at.jku.followingservice.model.User;
import at.jku.followingservice.repository.HashtagRepository;
import at.jku.followingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingServiceImpl implements FollowingService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HashtagRepository hashtagRepository;

    @Override
    public List<User> findUserFollower(String username) {
        return userRepository.findByFollowedUsersUsername(username);
    }

    @Override
    public List<User> findHashtagFollower(String title) {
        return userRepository.findByFollowedHashtagsTitle(title);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Hashtag findByTitle(String title) {
        return hashtagRepository.findByTitle(title);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User unfollowUser(User follower, User followed) {
        return userRepository.unfollowUser(follower.getUsername(), followed.getUsername());
    }

    @Override
    public User unfollowHashtag(User follower, Hashtag hashtag) {
        return userRepository.unfollowHashtag(follower.getUsername(), hashtag.getTitle());
    }
}
