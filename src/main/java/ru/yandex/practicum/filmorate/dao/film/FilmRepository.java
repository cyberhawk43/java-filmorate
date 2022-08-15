package ru.yandex.practicum.filmorate.dao.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;


import java.util.List;
import java.util.Set;

public interface FilmRepository {
    Film getFilmById(int id);

    Film saveFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    List<Film> getTopFilms(int count);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    Set<Genre> getFilmGenres(int id);

    void addFilmGenre(int filmId, int genreId);

    void delGenreOnFilm(int filmId);
}
