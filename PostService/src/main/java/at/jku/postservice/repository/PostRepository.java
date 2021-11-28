package at.jku.postservice.repository;

import at.jku.postservice.model.Hashtag;
import at.jku.postservice.model.Post;
import at.jku.postservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findPostByUser(User user);

    Optional<List<Post>> findPostByDate(LocalDateTime date);

    Optional<List<Post>> findPostByHashtag(Hashtag hashtag);

    Optional<List<Post>> findPostByUserAndDate(User user, LocalDateTime date);

    Optional<List<Post>> findPostByUserAndHashtag(User user, Hashtag hashtag);

    Optional<List<Post>> findPostByDateAndHashtag(LocalDateTime date, Hashtag hashtag);

    Optional<List<Post>> findPostByUserAndDateAndHashtag(User user, LocalDateTime date, Hashtag hashtag);


}
