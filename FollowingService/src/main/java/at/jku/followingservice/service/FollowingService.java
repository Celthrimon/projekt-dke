package at.jku.followingservice.service;

import at.jku.followingservice.model.Hashtag;
import at.jku.followingservice.model.User;

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

    boolean checkUserFollowsUser(User follower, User followed);

    boolean checkUserFollowsHashtag(User follower, Hashtag hashtag);
}
