package at.jku.postservice.controller;

import at.jku.postservice.exception.InvalidArgumentException;
import at.jku.postservice.exception.ResourceNotFoundException;
import at.jku.postservice.model.Post;
import at.jku.postservice.model.User;
import at.jku.postservice.repository.HashtagRepository;
import at.jku.postservice.repository.PostRepository;
import at.jku.postservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PostRepository postRepository;
    private UserRepository userRepository;
    private HashtagRepository hashtagRepository;

    public PostController(PostRepository postRepository, UserRepository userRepository, HashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam("userId") Optional<String> userId,
                               @RequestParam("date") Optional<LocalDateTime> date,
                               @RequestParam("hashtag") Optional<String> hashtag) {

        if (userId.isPresent() && date.isPresent() && hashtag.isPresent()) {
            return postRepository.findPostByUserAndDateAndHashtagsIsContaining(userRepository.getById(userId.get()), date.get(), hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
        } else if (userId.isPresent() && date.isPresent()) {
            return postRepository.findPostByUserAndDate(userRepository.getById(userId.get()), date.get()).orElse(new ArrayList<>());
        } else if (userId.isPresent() && hashtag.isPresent()) {
            return postRepository.findPostByUserAndHashtagsIsContaining(userRepository.getById(userId.get()), hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
        } else if (date.isPresent() && hashtag.isPresent()) {
            return postRepository.findPostByDateAndHashtagsIsContaining(date.get(), hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
        } else if (userId.isPresent()) {
            return postRepository.findPostByUser(userRepository.getById(userId.get())).orElse(new ArrayList<>());
        } else if (date.isPresent()) {
            return postRepository.findPostByDate(date.get()).orElse(new ArrayList<>());
        } else return postRepository.findPostByHashtagsIsContaining(hashtagRepository.getById(hashtag.get())).orElse(new ArrayList<>());
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user exits with id:" + userId));
    }

    @GetMapping("/post/{postId}")
    public Post getUser(@PathVariable long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("No post exits with id:" + postId));
    }

    @GetMapping("/mood/{userId}")
    public void getMood(@PathVariable Long userId) {

    }

    @PostMapping("/post")
    public ResponseEntity<Post> newPost(@RequestBody Post newPost) {
        if (ObjectUtils.isEmpty(newPost.getUser())) {
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

    @PostMapping("/like/{postId}")
    public void likePost(@PathVariable long postId, @RequestParam("user") String userId) {
        if(ObjectUtils.isEmpty(postRepository.getById(postId)))
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");

        if(ObjectUtils.isEmpty(userId)) throw new InvalidArgumentException("userId is required!");

        if(ObjectUtils.isEmpty(userRepository.getById(userId)))
            throw new ResourceNotFoundException("No such user found (id: " + postId + " )");

        postRepository.getById(postId).addLike(userRepository.getById(userId));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity.BodyBuilder deletePost(@PathVariable Long postId) {
        if (!postRepository.findById(postId).isPresent())
            throw new ResourceNotFoundException("No such post found (id: " + postId + " )");

        postRepository.deleteById(postId);

        return ResponseEntity.ok();
    }
}
