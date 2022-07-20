package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {
    @Autowired
    FilmStorage filmStorage;

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmByID(int filmId) throws NotFoundException {
        final Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }
        return film;
    }

    public Film updateFilm(Film updateFilm) throws NotFoundException {
        final Film oldFilm = filmStorage.getFilm(updateFilm.getId());
        if (oldFilm == null) {
            throw new NotFoundException("Фильм с id=" + updateFilm + " не найден");
        }
        return filmStorage.updateFilm(updateFilm);
    }

    public Film addNewFilm(Film film) {
        return filmStorage.saveFilm(film);
    }

    public void addNewLike(int filmId, int userId) throws NotFoundException {
        final Film film = filmStorage.getFilm(filmId);
        final Set<Integer> users = filmStorage.getFilm(filmId).getUserID();
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + userId + " не найден");
        }
        if (users.contains(userId)) {
            throw new NotFoundException("Пользователь c id=" + userId + " уже ставил лайк");
        }
        filmStorage.addLike(film, userId);
    }

    public void deleteLike(int filmId, int userId) throws NotFoundException {
        final Film film = filmStorage.getFilm(filmId);
        final Set<Integer> users = filmStorage.getFilm(filmId).getUserID();
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + userId + " не найден");
        }
        if (!users.contains(userId)) {
            throw new NotFoundException("Пользователь с id=" + userId + " не ставил лайк");
        }
        filmStorage.deleteLike(film, userId);
    }

    public List<Film> getTop(int count) {
        List<Film> topFilms = filmStorage.getTopFilms();
        List<Film> topCountFilms = new ArrayList<>();
        if (count > topFilms.size()) {
            count = topFilms.size();
        }
        for (int i = 0; i < count; i++) {
            topCountFilms.add(topFilms.get(i));
        }
        return topCountFilms;
    }
}
