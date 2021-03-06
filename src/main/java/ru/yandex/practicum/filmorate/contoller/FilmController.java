package ru.yandex.practicum.filmorate.contoller;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@Getter
@Setter
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private int filmID = 1;

    public int createId() {
        return filmID++;
    }


    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /films, метод: POST");
        if (validateFilm(film)) {
            film.setId(createId());
            films.put(film.getId(), film);
            log.debug("Создание фильма успешно");
        }
        return film;
    }


    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /films, метод: PUT");
        if (validateFilm(film)) {
            if (films.size() > 0) {
                if (films.get(film.getId()) != null) {
                    films.put(film.getId(), film);
                    log.debug("Обновление фильма с id = " + film.getId() + " прошло успешно!");
                } else {
                    log.debug("Фильма с таким id = " + film.getId() + " не существует!");
                    throw new ValidationException("Фильма с таким id = " + film.getId() + " не существует!");
                }
            } else {
                log.debug("Список фильмов пуст");
                throw new ValidationException("Список фильмов пуст!");
            }
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        List<Film> listFilms = new ArrayList<>();
        for (Integer key : films.keySet()) {
            listFilms.add(films.get(key));
        }
        log.info("Получен запрос к эндпоинту: /films, метод: GET");
        return listFilms;
    }

    public boolean validateFilm(@NotNull Film film) throws ValidationException {
        if (film.getName().isEmpty()) {
            log.debug("Фильм пустой");
            throw new ValidationException("Фильм пустой");
        } else if (film.getDescription().length() > 200) {
            log.debug("Описание больше двухсот символов");
            throw new ValidationException("Описание больше двухсот символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.debug("Фильм раньше даты 28.12.1895");
            throw new ValidationException("Фильм раньше даты 28.12.1895");
        } else if (film.getDuration() < 0) {
            log.debug("Продолжительность отрицательная");
            throw new ValidationException("Продолжительность отрицательная");
        }
        return true;
    }

}
