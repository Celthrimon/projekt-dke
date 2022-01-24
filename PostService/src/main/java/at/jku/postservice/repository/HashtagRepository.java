package at.jku.postservice.repository;

import at.jku.postservice.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, String> {
    Hashtag findHashtagByTitle(String title);
}
