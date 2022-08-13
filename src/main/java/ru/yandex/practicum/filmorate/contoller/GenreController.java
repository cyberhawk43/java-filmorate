package ru.yandex.practicum.filmorate.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;

import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
public class GenreController {

    @Autowired
    GenreService gs;

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return gs.getAllGenre();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable int id) {
        return gs.getGenreById(id);
    }
}
