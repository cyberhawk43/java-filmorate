package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.contoller.FilmController;
import ru.yandex.practicum.filmorate.contoller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilmorate {
    private final FilmController fc = new FilmController();
    private final UserController uc = new UserController();

    @BeforeEach
    void init() throws ValidationException {
        User user = new User(0, "@mail", "login1", "name1",
                LocalDate.of(1996, 7, 24));
        Film film = new Film(0, "nameFilm", "description",
                LocalDate.of(2001, 12, 1), 120);
        fc.addFilm(film);
        uc.addNewUser(user);
    }

    @Test
    void addUser() throws ValidationException {
        //создание верного пользователя
        User user = new User(0, "@mail", "login2", "name2",
                LocalDate.of(1996, 7, 24));
        uc.addNewUser(user);
        assertNotNull(uc.getAllUsers().get(1));
        assertEquals(2, uc.getAllUsers().size());

    }

    @Test
    void getUsers() {
        Map<Integer,User> checkMap = new HashMap<>();
        User checkUser = new User(0, "@mail", "login1", "name1",
                LocalDate.of(1996, 7, 24));
        checkMap.put(0,checkUser);
        assertEquals(1, uc.getAllUsers().size());
        assertEquals(checkMap, uc.getAllUsers());
    }

    @Test
    void updateUsers() throws ValidationException {
        User user = new User(0, "@mail", "updateLogin1", "updateName1",
                LocalDate.of(1996, 7, 24));
        uc.updateUser(user);
        assertEquals("updateLogin1", uc.getAllUsers().get(0).getLogin());
    }

    @Test
    void getFilms() {
        Map<Integer, Film> films = new HashMap<>();
        Film film = new Film(0, "nameFilm", "description",
                LocalDate.of(2001, 12, 1), 120);
        films.put(0,film);
        assertEquals(1, fc.getAllFilms().size());
        assertEquals(films, fc.getAllFilms());
    }

    @Test
    void addFilm() throws ValidationException{
        Film film2 = new Film(0, "nameFilm2", "description2",
                LocalDate.of(2002, 1, 1), 120);
        fc.addFilm(film2);
        assertNotNull(fc.getAllFilms().get(1));
        assertEquals(2,fc.getAllFilms().size());
    }

    @Test
    void updateFilm() throws ValidationException{
        Film updateFilm = new Film(0, "updateNameFilm", "updateDescription",
                LocalDate.of(2011, 3, 2), 120);
        fc.updateFilm(updateFilm);
        assertEquals(updateFilm, fc.getAllFilms().get(0));
    }
}
