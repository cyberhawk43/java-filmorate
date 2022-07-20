package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    @Autowired
    UserStorage userStorage;

    public User getUserByID(int userId) throws NotFoundException {
        final User user = userStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        return user;
    }

    public User updateUser(User updateUser) throws NotFoundException {
        final User oldUser = userStorage.getUser(updateUser.getId());
        if (oldUser == null) {
            throw new NotFoundException("Пользователь с id=" + updateUser + " не найден");
        }
        return userStorage.updateUser(updateUser);
    }

    public User addNewUser(User user) {
        return userStorage.saveUser(user);
    }

    public void addNewFriend(int userId, int friendId) throws NotFoundException {
        final User user = userStorage.getUser(userId);
        final User friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id=" + friendId + " не найден");
        }
        userStorage.addFriend(user, friend);
    }

    public void deleteFriend(int userId, int friendId) throws NotFoundException {
        final User user = userStorage.getUser(userId);
        final User friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new NotFoundException("Друг с id=" + userId + " не найден");
        }
        userStorage.deleteFriend(user, friend);
    }

    public List<User> getListFriends(int userId) throws NotFoundException {
        List<User> listFriends = new ArrayList<>();
        final User user = userStorage.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        for (Integer friend : user.getFriendsIDs()) {
            listFriends.add(userStorage.getUser(friend));
        }
        return listFriends;
    }

    public List<User> getListMutualFriends(int userId, int otherId) throws NotFoundException {
        List<User> listMutualFriends = new ArrayList<>();
        final User user = userStorage.getUser(userId);
        final User otherUser = userStorage.getUser(otherId);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (otherUser == null) {
            throw new NotFoundException("Другого пользователь с id=" + otherId + " не найден");
        }
        for (Integer friend : user.getFriendsIDs()) {
            if(otherUser.getFriendsIDs().contains(friend)) {
                listMutualFriends.add(userStorage.getUser(friend));
            }
        }
        return listMutualFriends;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

}
