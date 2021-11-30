package at.jku.followingservice.controller;

import at.jku.followingservice.model.FollowerCountWrapper;
import at.jku.followingservice.model.Hashtag;
import at.jku.followingservice.model.User;
import at.jku.followingservice.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FollowingController {

    final FollowingService followingService;

    @Autowired
    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @RequestMapping(value = "followUser/{userId}", method = RequestMethod.GET)
    public List<User> findFollowedUsers(@PathVariable("userId") String username) {
        User user = followingService.findByUsername(username);
        return new ArrayList<>(user.getFollowedUsers());
    }

    @RequestMapping(value = "followUser/{userId}", method = RequestMethod.POST)
    public ResponseEntity<User> followUser
            (@PathVariable("userId") String userNameFollower, @RequestParam String user) {
        User follower = followingService.findByUsername(userNameFollower);
        User followed = followingService.findByUsername(user);
        follower.followUser(followed);
        return ResponseEntity.ok(followingService.save(follower));
    }

    @RequestMapping(value = "followUser/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> unfollowUser
            (@PathVariable("userId") String userNameFollower, @RequestParam String user) {
        User follower = followingService.findByUsername(userNameFollower);
        User followed = followingService.findByUsername(user);
        return ResponseEntity.ok(followingService.unfollowUser(follower, followed));
    }

    @RequestMapping(value = "followHashtag/{userId}", method = RequestMethod.GET)
    public List<Hashtag> findFollowedHashtags(@PathVariable("userId") String username) {
        User user = followingService.findByUsername(username);
        return new ArrayList<>(user.getFollowedHashtags());
    }

    @RequestMapping(value = "followHashtag/{userId}", method = RequestMethod.POST)
    public ResponseEntity<User> followHashtag
            (@PathVariable("userId") String username, @RequestParam String title) {
        User follower = followingService.findByUsername(username);
        Hashtag hashtag = followingService.findByTitle(title);
        follower.followHashtag(hashtag);
        return ResponseEntity.ok(followingService.save(follower));
    }

    @RequestMapping(value = "followHashtag/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> unfollowHashtag
            (@PathVariable("userId") String username, @RequestParam String title) {
        User follower = followingService.findByUsername(username);
        Hashtag hashtag = followingService.findByTitle(title);
        return ResponseEntity.ok(followingService.unfollowHashtag(follower, hashtag));
    }

    @RequestMapping(value = "followerUser/{userId}", method = RequestMethod.GET)
    public ResponseEntity<FollowerCountWrapper> getFollowerAndFollowedCount(@PathVariable("userId") String username) {
        User user = followingService.findByUsername(username);
        List<User> follower = followingService.findUserFollower(user.getUsername());
        FollowerCountWrapper wrapper = new FollowerCountWrapper();
        wrapper.setFollowerCount(follower.size());
        wrapper.setFollowedCount(user.getFollowedUsers().size());
        return ResponseEntity.ok(wrapper);
    }

    @RequestMapping(value = "followerHashtag/{title}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getFollowerCount(@PathVariable("title") String title) {
        String correctTitle = "#" + title;
        return ResponseEntity.ok(followingService.findHashtagFollower(correctTitle).size());
    }
}
