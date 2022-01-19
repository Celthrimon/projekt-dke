package at.jku.postservice.controller;

import at.jku.postservice.exception.InvalidArgumentException;
import at.jku.postservice.exception.ResourceNotFoundException;
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
     * @param userName username of the user that created the posts
     * @param dateStartOptional the starting date when these posts where created
     *                          --> if not defined: set to the current date
     * @param dateEndOptional the ending date when these posts where created
     *                        --> if not defined: set to 1 month difference to the starting date
     * @param hashtag the hashtags the post must contain
     *
     * @return the posts with the required properties
     */
    @RequestMapping(value = "posts", method = RequestMethod.GET)
    public List<Post> getPosts(@RequestParam("userName") Optional<String> userName,
                               @RequestParam("dateStart") Optional<LocalDateTime> dateStartOptional,
                               @RequestParam("dateEnd") Optional<LocalDateTime> dateEndOptional,
                               @RequestParam("hashtag") Optional<String> hashtag) {

        LocalDateTime dateStart, dateEnd;

        if(!dateStartOptional.isPresent() || dateStartOptional.isEmpty()) dateStart = LocalDateTime.now();
        else dateStart = dateEndOptional.get();
        if(!dateEndOptional.isPresent() || dateEndOptional.isEmpty()) dateEnd = dateStart.plusMonths(1);
        else dateEnd = dateEndOptional.get();


        if(userName.isPresent() && hashtag.isPresent()){
            return postRepository.findPostByAuthorAndHashtagsIsContainingAndDateBetweenOrderByDate(
                    userRepository.getById(userName.get()),
                    hashtagRepository.getById(hashtag.get()),
                    dateStart,
                    dateEnd
            ).orElse(new ArrayList<>());
        } else if(userName.isPresent()) {
            return postRepository.findPostByAuthorAndDateBetweenOrderByDate(
                    userRepository.getById(userName.get()),
                    dateStart,
                    dateEnd
            ).orElse(new ArrayList<>());
        } else if(hashtag.isPresent()){
            return postRepository.findPostByHashtagsIsContainingAndDateBetweenOrderByDate(
                    hashtagRepository.getById(hashtag.get()),
                    dateStart,
                    dateEnd
            ).orElse(new ArrayList<>());
        } else {
            return postRepository.findPostByDateBetweenOrderByDate(
                    dateStart,
                    dateEnd
            ).orElse(new ArrayList<>());
        }
    }

    // TODO: create mood
    @RequestMapping(value = "mood/{userName}", method = RequestMethod.GET)
    public void getMood(@PathVariable Long userName) {

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

        return new ResponseEntity<>(postRepository.save(newPost), HttpStatus.CREATED);
    }

    /**
     * user likes a post
     *
     * @param postId id of the post to be liked
     * @param userName id of the user that likes the post
     */
    @RequestMapping(value = "like/{postId}", method = RequestMethod.POST)
    public void likePost(@PathVariable long postId, @RequestParam("user") String userName) {
        if (ObjectUtils.isEmpty(postRepository.getById(postId)))
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");

        if (ObjectUtils.isEmpty(userName)) throw new InvalidArgumentException("userName is required!");

        if (ObjectUtils.isEmpty(userRepository.getById(userName)))
            throw new ResourceNotFoundException("No such user found (id: " + postId + " )");

        postRepository.getById(postId).addLike(userRepository.getById(userName));
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
        if (!postRepository.findById(postId).isPresent()) {
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");
        }

        postRepository.deleteById(postId);
        return ResponseEntity.ok();
    }

    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    //TODO: remove testing APIs

    /**
     * gets a user
     * ## for testing purposes ##
     *
     * @param userName the users name
     *
     * @return the required user
     */
    @GetMapping("/user/{userName}")
    public User getUser(@PathVariable String userName) {
        return userRepository.findById(userName).orElseThrow(() -> new ResourceNotFoundException("No user exits with id:" + userName));
    }

    /**
     * gets a post
     * ## for testing purposes ##
     *
     * @param postId the posts id
     *
     * @return the required post
     */
    @GetMapping("/post/{postId}")
    public Post getPost(@PathVariable long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("No post exits with id:" + postId));
    }
}
