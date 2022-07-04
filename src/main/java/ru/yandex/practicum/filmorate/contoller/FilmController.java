package ru.yandex.practicum.filmorate.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.HashMap;
import java.util.Map;

@RestController
public class FilmController {
    Map<Integer, Film> films = new HashMap<>();

    @PostMapping("/film")
    void addFilm(@RequestBody Film film) {
        films.put(film.getId(), film);
    }

    @PostMapping("/update")
    void updateFilm(@RequestBody Film film) {
        films.put(film.getId(), film);
    }

    @GetMapping("/films")
    Map<Integer, Film> getAllFilms() {
        return films;
    }


}
