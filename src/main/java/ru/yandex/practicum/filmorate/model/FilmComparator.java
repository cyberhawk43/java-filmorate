package ru.yandex.practicum.filmorate.model;

import java.util.Comparator;

public class FilmComparator implements Comparator<Film> {
    @Override
    public int compare(Film film1, Film film2) {
        return Integer.compare(film1.getUserID().size(), film2.getUserID().size());
    }
}
