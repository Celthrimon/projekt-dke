package at.jku.followingservice.repository;

import at.jku.followingservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, String> {

    User findByUsername(String username);
    List<User> findByFollowersUsername(String username);

    @Query("MATCH (u {user: User})-[r:FOLLOWS]->() DELETE r")
    void unfollow(User follower, User followed);
}
