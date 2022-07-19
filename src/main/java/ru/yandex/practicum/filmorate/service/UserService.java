package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserStorage userStorage;

    public User getUserByID(int userId) throws ValidationException {
        final User user = userStorage.getUser(userId);
        if (user == null) {
            throw new ValidationException("Пользователь с id=" + userId + " не найден");
        }
        return user;
    }

    public User updateUser(User updateUser) throws ValidationException {
        final User oldUser = userStorage.getUser(updateUser.getId());
        if (oldUser == null) {
            throw new ValidationException("Пользователь с id=" + updateUser + " не найден");
        }
        return userStorage.updateUser(updateUser);
    }

    public User addNewUser(User user) throws ValidationException {
        return userStorage.saveUser(user);
    }

    public void addNewFriend(int userId, int friendId) throws ValidationException {
        final User user = userStorage.getUser(userId);
        final User friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new ValidationException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new ValidationException("Друг с id=" + friendId + " не найден");
        }
        userStorage.addFriend(user, friend);
    }

    public void deleteFriend(int userId, int friendId) throws ValidationException {
        final User user = userStorage.getUser(userId);
        final User friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new ValidationException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new ValidationException("Друг с id=" + userId + " не найден");
        }
        userStorage.deleteFriend(user, friend);
    }

    public List<User> getListFriends(int userId) throws ValidationException {
        List<User> listFriends = new ArrayList<>();
        final User user = userStorage.getUser(userId);
        if (user == null) {
            throw new ValidationException("Пользователь с id=" + userId + " не найден");
        }
        for (Integer friend : user.getFriendsIDs()) {
            listFriends.add(userStorage.getUser(friend));
        }
        return listFriends;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

}
