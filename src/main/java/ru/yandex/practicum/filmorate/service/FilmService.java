package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        final Set<Integer> users = filmStorage.getFilm(filmId).getUserID();
        if (film == null) {
            throw new ValidationException("Фильм с id=" + userId + " не найден");
        }
        if (users.contains(userId)) {
            throw new ValidationException("Пользователь c id=" + userId + " уже ставил лайк");
        }
        filmStorage.addLike(film, userId);
    }

    public void deleteLike(int filmId, int userId) throws ValidationException {
        final Film film = filmStorage.getFilm(filmId);
        final Set<Integer> users = filmStorage.getFilm(filmId).getUserID();
        if (film == null) {
            throw new ValidationException("Фильм с id=" + userId + " не найден");
        }
        if (!users.contains(userId)) {
            throw new ValidationException("Пользователь с id=" + userId + " не ставил лайк");
        }
        filmStorage.deleteLike(film, userId);
    }

    public List<Film> getTop() throws ValidationException {
        List<Film> topFilms = filmStorage.getTopFilms();
        List<Film> top10Films = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            top10Films.add(topFilms.get(i));
        }
        return top10Films;
    }
}
