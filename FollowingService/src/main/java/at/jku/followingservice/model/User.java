package at.jku.followingservice.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Node
public class User {

    @Id
    private String username;

    private User() {
    };

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Relationship(type = "FOLLOWS")
    protected Set<User> followedUsers;

    @Relationship(type = "FOLLOWS")
    protected Set<Hashtag> followedHashtags;

    public void followUser(User user) {
        if (followedUsers == null)
            followedUsers = new HashSet<>();
        followedUsers.add(user);
    }

    public void followHashtag(Hashtag hashtag) {
        if(followedHashtags == null)
            followedHashtags = new HashSet<>();
        followedHashtags.add(hashtag);
    }

    public Set<User> getFollowedUsers() {
        return followedUsers;
    }

    public Set<Hashtag> getFollowedHashtags() {
        return followedHashtags;
    }

    @Override
    public String toString() {
        return this.username + "'s followers: "
                + Optional.ofNullable(this.followedUsers).orElse(
                Collections.emptySet()).stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
