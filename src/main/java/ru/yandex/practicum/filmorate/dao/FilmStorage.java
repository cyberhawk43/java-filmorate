package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmComparator;

import java.util.*;

@Component
public class FilmStorage {
    FilmComparator comparator = new FilmComparator();
    private Map<Integer, Film> filmMap = new HashMap<>();
    private int filmID = 1;

    public int createId() {
        return filmID++;
    }

    public Film getFilm(int filmId) {
        return filmMap.get(filmId);
    }

    public Film saveFilm(Film film) {
        film.setId(createId());
        filmMap.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        filmMap.put(film.getId(), film);
        return film;
    }

    public void addLike(Film film, int userId) {
        filmMap.get(film.getId()).getUserID().add(userId);
    }

    public void deleteLike(Film film, int userId) {
        filmMap.get(film.getId()).getUserID().remove(userId);
    }

    public List<Film> getTopFilms() {
        List<Film> listTopFilms = new ArrayList<>(filmMap.values());
        listTopFilms.sort(comparator.reversed());
        return listTopFilms;
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(filmMap.values());
    }


}
