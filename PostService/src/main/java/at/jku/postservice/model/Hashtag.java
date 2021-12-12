package at.jku.postservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "HASHTAGS")
public class Hashtag {
    @Id
    private String title;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "hashtags")
    private Set<Post> posts = new HashSet<>();

    public Hashtag() {
    }

    public Hashtag(String title) {
        this.title = title;
    }

    public void addPost(Post newPost){
        posts.add(newPost);
        newPost.getHashtags().add(this);
    }

    public String getTitle() {
        return title;
    }

    public Set<Post> getPosts() {
        return posts;
    }
}
