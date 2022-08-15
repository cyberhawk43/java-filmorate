package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.genre.GenreRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genre;

    public GenreService(GenreRepository genre) {
        this.genre = genre;
    }

    public List<Genre> getAllGenre() {
        return genre.getAllGenre();
    }

    public Genre getGenreById(int id) {
        if (id < 0 || id > genre.getAllGenre().size()) {
            throw new NotFoundException("id жанров за пределами таблицы");
        }
        return genre.getGenreById(id);
    }
}
