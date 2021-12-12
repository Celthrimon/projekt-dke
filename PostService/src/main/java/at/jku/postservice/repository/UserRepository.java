package at.jku.postservice.repository;

import at.jku.postservice.model.Post;
import at.jku.postservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<List<Post>> findByUsername(String username);

}
