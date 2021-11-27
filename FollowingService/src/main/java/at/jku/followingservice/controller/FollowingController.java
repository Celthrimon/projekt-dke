package at.jku.followingservice.controller;

import at.jku.followingservice.model.User;
import at.jku.followingservice.service.FollowingService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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
        List<User> followedUsers = followingService.findFollowedUsers(username);
        return followedUsers;
    }

    @RequestMapping(value = "followUser/{userId}", method = RequestMethod.POST)
    public ResponseEntity<User> followUser
            (@PathVariable("userId") String userNameFollower, @RequestParam String user) {
        User follower = followingService.findByUsername(userNameFollower);
        User following = followingService.findByUsername(user);
        follower.follow(following);
        return ResponseEntity.ok(followingService.save(follower));
    }
}
