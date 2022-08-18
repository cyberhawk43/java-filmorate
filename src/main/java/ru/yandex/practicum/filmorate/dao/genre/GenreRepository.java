package ru.yandex.practicum.filmorate.dao.genre;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;


public interface GenreRepository {
    Genre getGenreById(int id);

    List<Genre> getAllGenre();

}
