package at.jku.postservice.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "HASHTAGS")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "hashtags")
    private Set<Post> posts = new HashSet<>();

    public Hashtag() {
    }

    public void addPost(Post newPost){
        posts.add(newPost);
        newPost.getHashtags().add(this);
    }

    public Set<Post> getPosts() {
        return posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hashtag)) return false;
        Hashtag hashtag = (Hashtag) o;
        return id.equals(hashtag.id) && title.equals(hashtag.title) && posts.equals(hashtag.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, posts);
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posts=" + posts +
                '}';
    }
}
