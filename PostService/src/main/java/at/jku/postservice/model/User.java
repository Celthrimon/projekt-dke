package at.jku.postservice.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedBy")
    private Set<Post> likedPosts = new HashSet<>();

    public User() {
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void addPost(Post newPost){
        newPost.setUser(this);
        posts.add(newPost);
    }

    public void likePost(Post newPost){
        likedPosts.add(newPost);
        newPost.getLikedBy().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && posts.equals(user.posts) && likedPosts.equals(user.likedPosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, posts, likedPosts);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", posts=" + posts +
                ", likedPosts=" + likedPosts +
                '}';
    }
}
