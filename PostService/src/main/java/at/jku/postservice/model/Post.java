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

    private String content, emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "posts")
    @JoinTable(
            name = "PostHashtag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> hashtags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likedPosts")
    @JoinTable(
            name = "PostLikedUser",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy = new HashSet<>();

    private LocalDateTime date;

    public Post() {
    }

    public void addHashtag(Hashtag newHashtag){
        hashtags.add(newHashtag);
        newHashtag.getPosts().add(this);
    }

    public void addLike(User newUser){
        likedBy.add(newUser);
        newUser.getLikedPosts().add(this);
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id == post.id && content.equals(post.content) && Objects.equals(emoji, post.emoji) && user.equals(post.user) && hashtags.equals(post.hashtags) && likedBy.equals(post.likedBy) && date.equals(post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, emoji, user, hashtags, likedBy, date);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", emoji='" + emoji + '\'' +
                ", user=" + user +
                ", hashtags=" + hashtags +
                ", likedBy=" + likedBy +
                ", date=" + date +
                '}';
    }
}
