package kz.zhanbolat.springboot.controller;

import kz.zhanbolat.springboot.entity.User;
import kz.zhanbolat.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{userId}")
    public User getUser(@PathVariable("userId") int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User cannot be found"));
    }

    @PostMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@PathVariable("userId") int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User cannot be found"));
        userRepository.delete(user);
        return user;
    }
}
