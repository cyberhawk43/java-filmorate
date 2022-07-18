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


import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@Getter
@Setter
public class UserController {
    @Autowired
    UserService userService;
    private Map<Integer, User> users = new HashMap<>();
    private int userID = 1;

    public int createId() {
        return userID++;
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /users, метод: POST");
        if (validateUser(user)) {
            user.setId(createId());
            users.put(user.getId(), user);
            log.debug("Создание пользователя прошло успешно!");
        }
        return user;
    }

    @PutMapping("/users/{userID}/friends/{friendID}")
    public void addFriend(@PathVariable int userID, @PathVariable int friendID) throws ValidationException{
        userService.addNewFriend(userID, friendID);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /users, метод: PUT");
        if (validateUser(user)) {
            if (users.size() > 0) {
                if (users.get(user.getId()) != null) {
                    users.put((user.getId()), user);
                    log.debug("Обновление пользователя с id = " + user.getId() + " прошло успешно!");
                } else {
                    log.debug("Пользователя с таким id = " + user.getId() + " не существует!");
                    throw new ValidationException("Пользователя с таким id = " + user.getId() + " не существует!");
                }
            } else {
                log.debug("Список пользователей пуст");
                throw new ValidationException("Список пользователей пуст");
            }
        }
        return user;
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        for (Integer key : users.keySet()) {
            listUsers.add(users.get(key));
        }
        log.info("Получен запрос к эндпоинту: /users, метод: GET");
        return listUsers;
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



