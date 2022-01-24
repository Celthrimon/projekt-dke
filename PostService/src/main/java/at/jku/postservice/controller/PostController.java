package at.jku.postservice.controller;

import at.jku.postservice.exception.InvalidArgumentException;
import at.jku.postservice.exception.ResourceNotFoundException;
import at.jku.postservice.model.Hashtag;
import at.jku.postservice.model.Post;
import at.jku.postservice.model.User;
import at.jku.postservice.repository.HashtagRepository;
import at.jku.postservice.repository.PostRepository;
import at.jku.postservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("mymood/posting")
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository, HashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
    }

    /**
     * gets posts with optional properties
     *
     * @param userName          username of the user that created the posts
     * @param dateStartOptional the starting date when these posts where created
     *                          --> if not defined: set to the current date
     * @param dateEndOptional   the ending date when these posts where created
     *                          --> if not defined: set to 1 month difference to the starting date
     * @param hashtag           the hashtags the post must contain
     *
     * @return the posts with the required properties
     */
    @RequestMapping(value = "posts", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> getPosts(@RequestParam("userName") Optional<String> userName,
                                               @RequestParam("dateStart") Optional<LocalDateTime> dateStartOptional,
                                               @RequestParam("dateEnd") Optional<LocalDateTime> dateEndOptional,
                                               @RequestParam("hashtag") Optional<String> hashtag) {

        LocalDateTime dateStart, dateEnd;

        if (!dateStartOptional.isPresent() || dateStartOptional.isEmpty()) dateStart = LocalDateTime.now();
        else dateStart = dateEndOptional.get();
        if (!dateEndOptional.isPresent() || dateEndOptional.isEmpty()) dateEnd = dateStart.minusMonths(1);
        else dateEnd = dateEndOptional.get();

        if (userName.isPresent() && hashtag.isPresent()) {
            return ResponseEntity.ok(postRepository.findPostByAuthorAndHashtagsIsContainingAndDateBetweenOrderByDate(
                    userRepository.findUserByUserName(userName.get()),
                    hashtagRepository.getById(hashtag.get()),
                    dateEnd,
                    dateStart
            ).orElse(new ArrayList<>()));
        } else if (userName.isPresent()) {
            return ResponseEntity.ok(postRepository.findPostByAuthorAndDateBetweenOrderByDate(
                    userRepository.findUserByUserName(userName.get()),
                    dateEnd,
                    dateStart
            ).orElse(new ArrayList<>()));
        } else if (hashtag.isPresent()) {
            return ResponseEntity.ok(postRepository.findPostByHashtagsIsContainingAndDateBetweenOrderByDate(
                    hashtagRepository.getById(hashtag.get()),
                    dateEnd,
                    dateStart
            ).orElse(new ArrayList<>()));
        } else {
            return ResponseEntity.ok(postRepository.findPostByDateBetweenOrderByDate(
                    dateEnd,
                    dateStart
            ).orElse(new ArrayList<>()));
        }
    }

    @RequestMapping(value = "mood/{userName}", method = RequestMethod.GET)
    public ResponseEntity<Post> getMood(@PathVariable String userName) {
        if (userName.isEmpty() || userName.isBlank()) throw new InvalidArgumentException("userName is required!");

        User user = userRepository.getById(userName);

        if (ObjectUtils.isEmpty(user))
            throw new ResourceNotFoundException("No such user found (id: " + userName + " )");

        return ResponseEntity.ok(postRepository.findPostByAuthorAndMoodIsNotNullOrderByDate(user).get());
    }

    /**
     * create a new post
     *
     * @param newPost the post with its required parameters (userName: String, date: LocalDateTime, content: String
     *
     * @return HTTP CREATED response if the post was created else an exception is thrown
     */
    @RequestMapping(value = "post", method = RequestMethod.POST)
    public ResponseEntity<Post> newPost(@RequestBody Post newPost) {
        if (ObjectUtils.isEmpty(newPost.getAuthor())) {
            throw new InvalidArgumentException("userName is required!");
        }

        if (ObjectUtils.isEmpty(newPost.getDate())) {
            throw new InvalidArgumentException("date is required!");
        }

        if (ObjectUtils.isEmpty(newPost.getContent())) {
            throw new InvalidArgumentException("content is required!");
        }

        User user = userRepository.getById(newPost.getAuthor().getUserName());
        //if (ObjectUtils.isEmpty(user)) user = userRepository.save(new User(newPost.getAuthor().getUserName()));

        for(Hashtag h: newPost.getHashtags()){
            Hashtag hashtag = hashtagRepository.getById(h.getTitle());
            if (ObjectUtils.isEmpty(hashtag)) hashtagRepository.save(new Hashtag(h.getTitle()));
        }

        return new ResponseEntity<>(postRepository.save(newPost), HttpStatus.CREATED);
    }

    /**
     * user likes a post
     *
     * @param postId   id of the post to be liked
     * @param userName id of the user that likes the post
     */
    @RequestMapping(value = "like/{postId}", method = RequestMethod.POST)
    public ResponseEntity.BodyBuilder likePost(@PathVariable long postId, @RequestParam("userName") String userName) {
        if (ObjectUtils.isEmpty(postId)) throw new InvalidArgumentException("postId is required!");

        Post post = postRepository.getById(postId);
        if (ObjectUtils.isEmpty(post))
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");

        if (userName.isEmpty() || userName.isBlank()) throw new InvalidArgumentException("userName is required!");

        User user = userRepository.getById(userName);
        //if (ObjectUtils.isEmpty(user)) user = userRepository.save(new User(userName));

        post.addLike(user);

        postRepository.save(post);
        userRepository.save(user);

        return ResponseEntity.ok();
    }

    /**
     * user removes like a post
     *
     * @param postId   id of the post to be liked
     * @param userName id of the user that likes the post
     */
    @RequestMapping(value = "unlike/{postId}", method = RequestMethod.POST)
    public ResponseEntity.BodyBuilder unlikePost(@PathVariable long postId, @RequestParam("userName") String userName) {
        if (ObjectUtils.isEmpty(postId)) throw new InvalidArgumentException("postId is required!");

        Post post = postRepository.getById(postId);
        if (ObjectUtils.isEmpty(post))
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");

        if (userName.isEmpty() || userName.isBlank()) throw new InvalidArgumentException("userName is required!");

        User user = userRepository.getById(userName);
        if (ObjectUtils.isEmpty(user)) user = userRepository.save(new User(userName));

        post.removeLike(user);

        postRepository.save(post);
        userRepository.save(user);

        return ResponseEntity.ok();
    }

    /**
     * delete a post
     *
     * @param postId id of the post to be deleted
     *
     * @return HTTP OK response if the post was deleted else an exception is thrown
     */
    @RequestMapping(value = "post/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity.BodyBuilder deletePost(@PathVariable Long postId) {
        if (ObjectUtils.isEmpty(postId)) throw new InvalidArgumentException("postId is required!");

        if (!postRepository.findById(postId).isPresent())
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");

        postRepository.deleteById(postId);

        return ResponseEntity.ok();
    }

    @RequestMapping(value = "createUserNode/{userName}", method = RequestMethod.POST)
    private ResponseEntity<String> createUser(@PathVariable("userName") String userName){
        User user = userRepository.findUserByUserName(userName);
        if(ObjectUtils.isEmpty(user)) {
            user = new User(userName);
        }
        userRepository.save(user);
        return ResponseEntity.ok("");
    }
}
