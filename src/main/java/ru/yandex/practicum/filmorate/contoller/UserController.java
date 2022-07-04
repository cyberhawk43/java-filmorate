package ru.yandex.practicum.filmorate.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;
@RestController
public class UserController {
    Map<Integer, User> users = new HashMap<>();

    @PostMapping("/user")
    void addNewUser(@RequestBody User user) {
        users.put(user.getId(), user);
    }

    @PostMapping("/update-user")
    void updateUser(@RequestBody User user) {
        users.put(user.getId(), user);
    }

    @GetMapping("/users")
    Map<Integer, User> getAllUsers() {
        return users;
    }

}
