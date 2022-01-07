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
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository, HashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
    }

    // TODO: change userId to userName in general

    /**
     * gets posts with optional properties
     *
     * @param userId  user id of the user that created the posts
     * @param date    the date when these posts where created
     * @param hashtag the hashtags the post must contain
     *
     * @return the posts with the required properties
     */
    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam("userId") Optional<String> userId,
                               @RequestParam("date") Optional<LocalDateTime> date,
                               @RequestParam("hashtag") Optional<String> hashtag) {

        if (userId.isPresent() && date.isPresent() && hashtag.isPresent()) {
            return postRepository.findPostByAuthorAndDateAndHashtagsIsContaining(userRepository.getById(userId.get()), date.get(), hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
        } else if (userId.isPresent() && date.isPresent()) {
            return postRepository.findPostByAuthorAndDate(userRepository.getById(userId.get()), date.get()).orElse(new ArrayList<>());
        } else if (userId.isPresent() && hashtag.isPresent()) {
            return postRepository.findPostByAuthorAndHashtagsIsContaining(userRepository.getById(userId.get()), hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
        } else if (date.isPresent() && hashtag.isPresent()) {
            return postRepository.findPostByDateAndHashtagsIsContaining(date.get(), hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
        } else if (userId.isPresent()) {
            return postRepository.findPostByAuthor(userRepository.getById(userId.get())).orElse(new ArrayList<>());
        } else if (date.isPresent()) {
            return postRepository.findPostByDate(date.get()).orElse(new ArrayList<>());
        } else
            return postRepository.findPostByHashtagsIsContaining(hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
    }

    // TODO: create mood
    @GetMapping("/mood/{userId}")
    public void getMood(@PathVariable Long userId) {

    }

    /**
     * create a new post
     *
     * @param newPost the post with its required parameters (userId: String, date: LocalDateTime, content: String
     *
     * @return HTTP CREATED response if the post was created else an exception is thrown
     */
    @PostMapping("/post")
    public ResponseEntity<Post> newPost(@RequestBody Post newPost) {
        if (ObjectUtils.isEmpty(newPost.getAuthor())) {
            throw new InvalidArgumentException("userId is required!");
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
     * @param userId id of the user that likes the post
     */
    @PostMapping("/like/{postId}")
    public void likePost(@PathVariable long postId, @RequestParam("user") String userId) {
        if (ObjectUtils.isEmpty(postRepository.getById(postId)))
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");

        if (ObjectUtils.isEmpty(userId)) throw new InvalidArgumentException("userId is required!");

        if (ObjectUtils.isEmpty(userRepository.getById(userId)))
            throw new ResourceNotFoundException("No such user found (id: " + postId + " )");

        postRepository.getById(postId).addLike(userRepository.getById(userId));
    }

    /**
     * delete a post
     *
     * @param postId id of the post to be deleted
     *
     * @return HTTP OK response if the post was deleted else an exception is thrown
     */
    @DeleteMapping("/post/{postId}")
    public ResponseEntity.BodyBuilder deletePost(@PathVariable Long postId) {
        if (!postRepository.findById(postId).isPresent()) {
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");
        }

        postRepository.deleteById(postId);
        return ResponseEntity.ok();
    }

    //TODO: remove testing APIs

    /**
     * gets a user
     * ## for testing purposes ##
     *
     * @param userId the users id
     *
     * @return the required user
     */
    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user exits with id:" + userId));
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
