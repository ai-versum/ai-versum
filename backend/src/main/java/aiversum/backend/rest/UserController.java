package aiversum.backend.rest;

import aiversum.backend.model.User;
import aiversum.backend.repository.UserRepository;
import aiversum.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        User registeredUser = userService.save(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
