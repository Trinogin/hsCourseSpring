package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Usr;
import recipes.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid Usr user) {
        userService.registerUser(user).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
