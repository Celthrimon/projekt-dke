package at.jku.postservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@JsonIgnoreProperties(value = "hibernateLazyInitializer")
public class User {
    @Id
    private String userName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedBy")
    private Set<Post> likedPosts = new HashSet<>();

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void post(Post newPost) {
        newPost.setAuthor(this);
        posts.add(newPost);
    }

    public void like(Post newPost) {
        likedPosts.add(newPost);
        newPost.getLikedBy().add(this);
    }
}
