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

    Optional<List<Post>> findPostByHashtagsIsContaining(Hashtag hashtag);

    Optional<List<Post>> findPostByUserAndDate(User user, LocalDateTime date);

    Optional<List<Post>> findPostByUserAndHashtagsIsContaining(User user, Hashtag hashtag);

    Optional<List<Post>> findPostByDateAndHashtagsIsContaining(LocalDateTime date, Hashtag hashtag);

    Optional<List<Post>> findPostByUserAndDateAndHashtagsIsContaining(User user, LocalDateTime date, Hashtag hashtag);
}
