package at.jku.postservice.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "hashtag_id")}
    )
    private Set<Hashtag> hashtags = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy = new HashSet<>();

    private LocalDateTime date;

    public Post() {
    }

    public Post(String content, User author, LocalDateTime date) {
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public void addHashtag(Hashtag newHashtag) {
        hashtags.add(newHashtag);
        newHashtag.getPosts().add(this);
    }

    public void addLike(User newUser) {
        likedBy.add(newUser);
        newUser.getLikedPosts().add(this);
    }

    public void removeLike(User user) {
        if (likedBy.contains(user)) likedBy.remove(user);
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id == post.id && content.equals(post.content) && Objects.equals(emoji, post.emoji) && author.equals(post.author) && hashtags.equals(post.hashtags) && likedBy.equals(post.likedBy) && date.equals(post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, emoji, author, hashtags, likedBy, date);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", emoji='" + emoji + '\'' +
                ", author=" + author +
                ", hashtags=" + hashtags +
                ", likedBy=" + likedBy +
                ", date=" + date +
                '}';
    }
}
