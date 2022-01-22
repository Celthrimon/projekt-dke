package at.jku.postservice.repository;

import at.jku.postservice.model.Hashtag;
import at.jku.postservice.model.Post;
import at.jku.postservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findPostByDateBetweenOrderByDate(LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<List<Post>> findPostByAuthorAndDateBetweenOrderByDate(User author, LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<List<Post>> findPostByHashtagsIsContainingAndDateBetweenOrderByDate(Hashtag hashtag, LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<List<Post>> findPostByAuthorAndHashtagsIsContainingAndDateBetweenOrderByDate(User author, Hashtag hashtag, LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<Post> findPostByAuthorAndMoodIsNotNullOrderByDate(User author);
}