package ru.yandex.practicum.filmorate.dao.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserRepository {
    User getById(int id);

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    void addFriend(User user, User friend);

    void delFriend(User user, User friend);

    List<Integer> getIdFriends(int userId);

    List<Integer> getIdMutualFriend(int userId, int otherUserId);
}
