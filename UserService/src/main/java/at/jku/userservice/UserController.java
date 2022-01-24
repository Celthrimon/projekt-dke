package at.jku.userservice;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("mymood/user")
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    List<User> all() {
        List<User> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @PostMapping("/")
    User newUser(@RequestBody User user) {
        if (repository.findById(user.getUsername()).isPresent()) throw new RuntimeException("User already exists");
        return repository.save(user);
    }

    @PutMapping("/")
    User updateUser(@RequestBody User user) {
        if (!repository.findById(user.getUsername()).isPresent()) throw new RuntimeException("User does not exist");
        User u = repository.findById(user.getUsername()).get();
        if (user.getAttributes() != null) u.setAttributes(user.getAttributes());
        if (user.getPassword() != null) u.setPasswordRaw(user.getPassword());
        return repository.save(u);
    }

    @DeleteMapping("/{username}")
    void deleteUser(@PathVariable("username") String username) {
        if (!repository.findById(username).isPresent()) throw new RuntimeException("User does not exist");
        repository.deleteById(username);
    }

    @PostMapping("/login")
    String login(@RequestBody User user) {
        if (!repository.findById(user.getUsername()).isPresent()) throw new RuntimeException("User does not exist");
        User u = repository.findById(user.getUsername()).get();
        if (!user.getPassword().equals(u.getPassword())) throw new RuntimeException("Wrong password");

        return JWT.jwt(u);
    }

    @GetMapping("/login")
    String istrue(@RequestBody String jwt) {
        return "" + JWT.isValid(jwt);
    }
}
