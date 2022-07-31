package ru.yandex.practicum.filmorate.contoller;


import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@Getter
@Setter
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /users, метод: POST");
        if (validateUser(user)) {
            userService.addNewUser(user);
            log.debug("Создание пользователя прошло успешно!");
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /users, метод: PUT");
        if (validateUser(user)) {
            if (userService.getUserByID(user.getId()) != null) {
                userService.updateUser(user);
                log.debug("Обновление пользователя с id = " + user.getId() + " прошло успешно!");
            } else {
                log.debug("Пользователя с таким id = " + user.getId() + " не существует!");
                throw new ValidationException("Пользователя с таким id = " + user.getId() + " не существует!");
            }
        }
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        log.info("Получен запрос к эндпоинту: /users, метод: GET");
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userService.getUserByID(userId);
    }

    @GetMapping("/users/{userId}/friends")
    public List<User> getFriendsUserById(@PathVariable int userId) {
        return userService.getListFriends(userId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getListMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getListMutualFriends(id, otherId);
    }

    @PutMapping("/users/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.addNewFriend(userId, friendId);
        log.info("Добавление в друзья прошло успешно");
    }

    @DeleteMapping("/users/{userId}/friends/{friendId}")
    public void delFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.deleteFriend(userId, friendId);
    }


    public boolean validateUser(@NotNull User user) throws ValidationException {
        if (user.getEmail().isEmpty()) {
            log.debug("Email пустой");
            throw new ValidationException("Email пустой");
        } else if (!user.getEmail().contains("@")) {
            log.debug("Email не содержит @");
            throw new ValidationException("Email не содержит @");
        } else if (user.getLogin().isEmpty()) {
            log.debug("Логин пустой");
            throw new ValidationException("Логин пустой");
        } else if (user.getLogin().contains(" ")) {
            log.debug("Логин содержит пробел");
            throw new ValidationException("Логин содержит пробел");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Вы из будущего");
            throw new ValidationException("Вы из будущего");
        }
        if (user.getName().isEmpty()) {
            log.debug("Имя пустое, замененно на логин");
            user.setName(user.getLogin());
        }
        return true;
    }
}



