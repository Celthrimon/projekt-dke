package at.jku.followingservice.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
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
    public Set<User> followers;

    public void follow(User user) {
        if (followers == null) {
            followers = new HashSet<>();
        }
        followers.add(user);
    }

    @Override
    public String toString() {
        return this.username + "'s followers: "
                + Optional.ofNullable(this.followers).orElse(
                Collections.emptySet()).stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
