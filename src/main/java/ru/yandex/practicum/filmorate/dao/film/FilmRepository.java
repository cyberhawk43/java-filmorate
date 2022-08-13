package ru.yandex.practicum.filmorate.dao.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;


import java.util.HashSet;
import java.util.List;

public interface FilmRepository {
    Film getFilmById(int id);

    Film saveFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    List<Film> getTopFilms(int count);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    HashSet<Genre> getFilmGenres(int id);

    void addFilmGenre(int filmId, int genreId);

    void delGenreOnFilm(int filmId);
}
