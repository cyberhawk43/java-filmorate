package ru.yandex.practicum.filmorate.service;


import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    private final UserDbStorage userDB;

    public UserService(UserDbStorage userDB) {
        this.userDB = userDB;
    }

    public User getUserByID(int userId) throws NotFoundException {
        final User user = userDB.getById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        return user;
    }

    public User updateUser(User updateUser) throws NotFoundException {
        final User oldUser = userDB.getById(updateUser.getId());
        if (oldUser == null) {
            throw new NotFoundException("Пользователь с id=" + updateUser + " не найден");
        }
        return userDB.updateUser(updateUser);
    }

    public User addNewUser(User user) {
        return userDB.createUser(user);
    }

    public void addNewFriend(int userId, int friendId) throws NotFoundException {
        final User user = userDB.getById(userId);
        final User friend = userDB.getById(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id=" + friendId + " не найден");
        }
        userDB.addFriend(user, friend);
    }

    public void deleteFriend(int userId, int friendId) throws NotFoundException {
        final User user = userDB.getById(userId);
        final User friend = userDB.getById(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id=" + userId + " не найден");
        }
        userDB.delFriend(user, friend);
    }

    public List<User> getListFriends(int userId) throws NotFoundException {
        List<User> listFriends = new ArrayList<>();
        final User user = userDB.getById(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        for (Integer friend : userDB.getIdFriends(userId)) {
            user.getFriendsIDs().add(friend);
            listFriends.add(userDB.getById(friend));
        }
        return listFriends;
    }

    public List<User> getListMutualFriends(int userId, int otherId) throws NotFoundException {
        List<User> listMutualFriends = new ArrayList<>();
        final User user = userDB.getById(userId);
        final User otherUser = userDB.getById(otherId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (otherUser == null) {
            throw new NotFoundException("Другого пользователь с id=" + otherId + " не найден");
        }
        for (Integer friend : userDB.getIdMutualFriend(userId, otherId)) {
            listMutualFriends.add(userDB.getById(friend));
        }
        return listMutualFriends;
    }

    public List<User> getUsers() {
        return userDB.getAllUsers();
    }

}
