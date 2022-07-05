package ru.yandex.practicum.filmorate.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    Map<Integer, User> users = new HashMap<>();

    @PostMapping("/user")
    void addNewUser(@RequestBody User user) {
        try {
            if (!validateUser(user)) {
                throw new ValidationException("Ошибка валидации");
            }
            users.put(user.getId(), user);
            System.out.println("Создание нового пользователя прошло успешно!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/update-user")
    void updateUser(@RequestBody User user) {
        try {
            if (!validateUser(user)) {
                throw new ValidationException("Ошибка валидации");
            }
            if (users.get(user.getId()) != null) {
                users.put(user.getId(), user);
                System.out.println("Обновление пользователя с id = " + user.getId() + " прошло успешно!");
            } else {
                System.out.println("Пользователя с таким id = " + user.getId() + " не существует!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/users")
    Map<Integer, User> getAllUsers() {
        System.out.println("Список пользователей");
        return users;
    }

    boolean validateUser(@NotNull User user) {
        return !(user.getEmail().isEmpty()
                && user.getEmail().contains("@")
                && user.getLogin().isEmpty()
                && user.getLogin().contains(" ")
                && user.getBirthday().isAfter(LocalDateTime.now())
        );
    }


}



