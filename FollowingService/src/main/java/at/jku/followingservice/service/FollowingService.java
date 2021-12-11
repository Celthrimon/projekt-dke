package at.jku.followingservice.service;

import at.jku.followingservice.model.Hashtag;
import at.jku.followingservice.model.User;
import at.jku.followingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface FollowingService {

    List<User> findUserFollower(String username);

    List<User> findHashtagFollower(String title);

    User findByUsername(String username);

    Hashtag findByTitle(String title);

    Hashtag save(Hashtag hashtag);

    User save(User user);

    User unfollowUser(User follower, User followed);

    User unfollowHashtag(User follower, Hashtag hashtag);

    void remove(User user);
}
