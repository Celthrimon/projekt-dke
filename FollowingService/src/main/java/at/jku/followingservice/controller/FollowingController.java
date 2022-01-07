package at.jku.followingservice.controller;

import at.jku.followingservice.model.FollowerCountWrapper;
import at.jku.followingservice.model.Hashtag;
import at.jku.followingservice.model.User;
import at.jku.followingservice.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("following")
public class FollowingController {

    final FollowingService followingService;
    //private final Driver driver;

    @Autowired
    public FollowingController(FollowingService followingService) {
        this.followingService = followingService;
        //this.driver = driver;
    }

    @RequestMapping(value = "followUser/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<User>> findFollowedUsers(@PathVariable("userId") String username) {
        User user = followingService.findByUsername(username);
        System.out.println("Service");
        return ResponseEntity.ok(new ArrayList<>(user.getFollowedUsers()));
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

    @RequestMapping(value = "createUserNode/{userId}", method = RequestMethod.POST)
    private ResponseEntity<String> createUserNode(@PathVariable("userId") String username) {
        User user = followingService.findByUsername(username);
        if (user == null) {
            user = new User(username);
        }
        followingService.save(user);
        return ResponseEntity.ok("");
    }

    /*@RequestMapping(value = "updateUserNode/{oldUserId}", method = RequestMethod.PUT)
    private void updateUserNode(@PathVariable("oldUserId") String oldUsername, @RequestParam String newUser) {
        User user = followingService.findByUsername(oldUsername);
        try (Session session = driver.session(); Transaction tx = session.beginTransaction()) {
            try {
                if (user != null) {
                    user.setUsername(newUser);
                    followingService.save(user);
                    tx.commit();
                }
            } finally {
                tx.close();
            }
        }
        if (user != null) {
            user.setUsername(newUser);
            followingService.save(user);
            followingService.remove(followingService.findByUsername(oldUsername));
        }
    }*/

    @RequestMapping(value = "deleteUserNode/{userId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteUserNode(@PathVariable("userId") String username) {
        User user = followingService.findByUsername(username);
        if (user != null) {
            followingService.remove(user);
        }
        return ResponseEntity.ok("");
    }

    @RequestMapping(value = "createHashtagNodes", method = RequestMethod.POST)
    private ResponseEntity<List<Hashtag>> createHashtagNode(@RequestBody List<Hashtag> hashtags) {
        List<Hashtag> newlyCreatedHashtags = new LinkedList<>();
        for (Hashtag hashtag : hashtags) {
            if (followingService.findByTitle(hashtag.getTitle()) == null) {
                followingService.save(hashtag);
                newlyCreatedHashtags.add(hashtag);
            }
        }
        return ResponseEntity.ok(newlyCreatedHashtags);
    }
}















