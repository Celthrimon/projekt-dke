package at.jku.postservice.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    private String username;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedBy")
    private Set<Post> likedPosts = new HashSet<>();

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void post(Post newPost){
        newPost.setUser(this);
        posts.add(newPost);
    }

    public void like(Post newPost){
        likedPosts.add(newPost);
        newPost.getLikedBy().add(this);
    }
}
