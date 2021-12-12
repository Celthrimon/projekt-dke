package at.jku.postservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties(value = "hibernateLazyInitializer")
public class User {
    @Id
    private String username;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedBy")
    private Set<Post> likedPosts = new HashSet<>();

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Set<Post> getPosts() {
        return posts;
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
