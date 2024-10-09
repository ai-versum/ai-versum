package aiversum.backend.rest;

import aiversum.backend.model.User;
import aiversum.backend.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User registeredUser = userService.save(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("User with email: " + user.getEmail() + " already exists.");
        }
    }
}
