package at.jku.followingservice.repository;

import at.jku.followingservice.model.Hashtag;
import at.jku.followingservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, String> {

    User findByUsername(String username);

    List<User> findByFollowedUsersUsername(String username);

    List<User> findByFollowedHashtagsTitle(String title);

    @Query("MATCH(f:User {username:$follower})-[r:FOLLOWS]-(fd:User {username:$followed}) DELETE r RETURN f;")
    User unfollowUser(String follower, String followed);

    @Query("MATCH(f:User {username:$follower})-[r:FOLLOWS]-(h:Hashtag {title:$title}) DELETE r RETURN f;")
    User unfollowHashtag(String follower, String title);
}