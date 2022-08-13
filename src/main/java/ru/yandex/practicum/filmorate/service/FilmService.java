package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;


import java.util.List;
import java.util.Set;

@Service
public class FilmService {
    @Autowired
    FilmDbStorage filmDb;


    public User getUserByID(int id) {
        final User user = filmDb.getById(id);
        if (user == null) {
            throw new NotFoundException("пользователь с id=" + id + " не найден");
        }

        return user;
    }

    public List<Film> getAllFilms() {
        return filmDb.getAllFilms();
    }

    public Film getFilmByID(int filmId) throws NotFoundException {
        final Film film = filmDb.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }
        return film;
    }

    public Film updateFilm(Film updateFilm) throws NotFoundException {
        final Film oldFilm = filmDb.getFilmById(updateFilm.getId());
        final Set<Genre> genres = updateFilm.getGenres();
        if (genres.size() != 0) {
            filmDb.delGenreOnFilm(updateFilm.getId());
            for (Genre genre : genres) {
                filmDb.addFilmGenre(updateFilm.getId(), genre.getId());
            }
            updateFilm.setGenres(genres);
        } else {
            filmDb.delGenreOnFilm(updateFilm.getId());
        }
        if (oldFilm == null) {
            throw new NotFoundException("Фильм с id=" + updateFilm + " не найден");
        }
        return filmDb.updateFilm(updateFilm);
    }

    public Film addNewFilm(Film film) {
        final Film newFilm = filmDb.saveFilm(film);
        final Set<Genre> genres = film.getGenres();
        final MPA mpa = film.getMpa();
        if (genres != null) {

            for (Genre genre : genres) {
                filmDb.addFilmGenre(film.getId(), genre.getId());
            }
            newFilm.setGenres(genres);
        }
        if (mpa != null) {
            System.out.println("мпа - " + mpa.getId());

        }
        return newFilm;
    }

    public void addNewLike(int filmId, int userId) throws NotFoundException {
        getFilmByID(filmId);
        getUserByID(userId);
        filmDb.addLike(filmId, userId);

    }

    public void deleteLike(int filmId, int userId) throws NotFoundException {
        getFilmByID(filmId);
        getUserByID(userId);
        getFilmByID(filmId).getUserID().remove(userId);
        filmDb.deleteLike(filmId, userId);
    }

    public List<Film> getTop(int count) {
        return filmDb.getTopFilms(count);
    }
}
