package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

public class FilmService {
    @Autowired
    FilmStorage filmStorage;

    public Film getFilmByID(int filmId) throws ValidationException {
        final Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new ValidationException("Фильм с id=" + filmId + " не найден");
        }
        return film;
    }

    public Film updateFilm(Film updateFilm) throws ValidationException {
        final Film oldFilm = filmStorage.getFilm(updateFilm.getId());
        if (oldFilm == null) {
            throw new ValidationException("Фильм с id=" + updateFilm + " не найден");
        }
        return filmStorage.updateFilm(updateFilm);
    }

    public Film addNewFilm(Film film) throws ValidationException {
        return filmStorage.saveFilm(film);
    }

    public void addNewLike(int filmId, int userId) throws ValidationException {
        final Film film = filmStorage.getFilm(filmId);
        final F friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new ValidationException("Пользователь с id=" + userId + " не найден");
        }
        if (friend == null) {
            throw new ValidationException("Друг с id=" + userId + " не найден");
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

    public List<User> getListFriends(int userId) throws ValidationException{
        List<User> listFriends = new ArrayList<>();
        final User user = userStorage.getUser(userId);
        if (user == null) {
            throw new ValidationException("Пользователь с id=" + userId + " не найден");
        }
        for(Integer friend: user.getFriendsIDs()) {
            listFriends.add(userStorage.getUser(friend));
        }
        return listFriends;
    }
}
