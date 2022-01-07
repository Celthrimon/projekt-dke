package at.jku.followingservice.repository;

import at.jku.followingservice.model.Hashtag;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface HashtagRepository extends Neo4jRepository<Hashtag, String> {
    Hashtag findByTitle(String title);
}
