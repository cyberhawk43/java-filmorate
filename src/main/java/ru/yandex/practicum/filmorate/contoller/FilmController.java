package ru.yandex.practicum.filmorate.contoller;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;


@RestController
@Slf4j
@Getter
@Setter
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private int filmID = 1;


    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /films, метод: POST");
        if (validateFilm(film)) {
            filmService.addNewFilm(film);
            log.debug("Создание фильма успешно");
        }
        return film;
    }


    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос к эндпоинту: /films, метод: PUT");
        if (validateFilm(film)) {
            if (filmService.getFilmByID(film.getId()) != null) {
                filmService.updateFilm(film);
                log.debug("Обновление фильма с id = " + film.getId() + " прошло успешно!");
            } else {
                log.debug("Фильма с таким id = " + film.getId() + " не существует!");
                throw new ValidationException("Фильма с таким id = " + film.getId() + " не существует!");
            }
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        log.info("Получен запрос к эндпоинту: /films, метод: GET");
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("Получен фильм по id=" + id);
        return filmService.getFilmByID(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addNewLike(id, userId);
        log.info("Поставлен Like фильму с id=" + id + " от пользователя с userID=" + userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void delLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
        log.info("Удален Like с фильма с id=" + id + " от пользователя с userID=" + userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getCountTopFilm(@RequestParam(required = false) Integer count) {
        if (count == null) {
            count = 10;
        }
        return filmService.getTop(count);
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
