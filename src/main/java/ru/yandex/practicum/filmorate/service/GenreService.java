package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.genre.GenreRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    GenreRepository genre;

    public List<Genre> getAllGenre() {
        return genre.getAllGenre();
    }

    public Genre getGenreById(int id) {
        if (id < 1 || id > 6) {
            throw new NotFoundException("Нет такого id у жанров");
        }
        return genre.getGenreById(id);
    }
}
