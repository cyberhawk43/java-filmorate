package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public class FilmStorage implements Comparator<Film> {

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
        filmMap.get(film.getId()).getUserID().add(userId);
    }

    public List<Film> getTopFilms() {
        List<Film> listTopFilms = new ArrayList<>(filmMap.values());
        listTopFilms.sort(new FilmStorage());
        return listTopFilms;
    }

    @Override
    public int compare(Film o1, Film o2) {
        if (o1.getUserID().size() > o2.getUserID().size()) {
            return 1;
        } else if (o1.getUserID().size() == o2.getUserID().size()) {
            return 0;
        } else {
            return -1;
        }


    }
}
