package ru.yandex.practicum.filmorate.contoller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.DurationAdapter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.LocalDateAdapter;



import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    GsonBuilder GB = new GsonBuilder();
    private Gson gson = GB
            .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    @PostMapping("/film")
    void addFilm(@RequestBody Film film) {
        try {
            if (validateFilm(film)) {
                throw new ValidationException("Ошибка валидации фильма");
            }
            films.put(film.getId(), film);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/update")
    void updateFilm(@RequestBody Film film) {
        try {
            if (validateFilm(film)) {
                throw new ValidationException("Ошибка валидации фильма");
            }
            if (films.get(film.getId()) != null) {
                films.put(film.getId(),film);
                System.out.println("Обновление фильма с id = " + film.getId() + " прошло успешно!");
            } else {
                System.out.println("Фильма с таким id = " + film.getId() + " не существует!");
            }
            films.put(film.getId(), film);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/films")
    Map<Integer, Film> getAllFilms() {
        return films;
    }

    boolean validateFilm(@NotNull Film film) {
        return !(film.getName().isEmpty()
                && film.getDescription().length() > 200
                && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))
                && film.getDuration().isNegative());
    }


}
