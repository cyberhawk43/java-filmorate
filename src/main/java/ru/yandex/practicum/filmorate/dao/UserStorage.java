package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorage {
    private Map<Integer, User> userMap = new HashMap<>();
    private int userID = 1;

    public int createId() {
        return userID++;
    }

    public User getUser(int userId) {
        return userMap.get(userId);
    }

    public User saveUser(User user) {
        user.setId(createId());
        userMap.put(user.getId(), user);
        return user;
    }

    public User updateUser(User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    public void addFriend(User user, User friend) {
        user.getFriendsIDs().add(friend.getId());
        friend.getFriendsIDs().add(user.getId());
    }

    public void deleteFriend(User user, User friend) {
        user.getFriendsIDs().remove(friend.getId());
        friend.getFriendsIDs().remove(user.getId());
    }
}