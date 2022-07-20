package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class TestFilmorate {

    UserStorage us = new UserStorage();


    FilmStorage fs = new FilmStorage();


    @BeforeEach
    void init() {
        User user = new User(1, "@mail", "login1", "name1",
                LocalDate.of(1996, 7, 24), new HashSet<>());
        Film film = new Film(1, "nameFilm", "description",
                LocalDate.of(2001, 12, 1), 120, new HashSet<>());
        fs.saveFilm(film);
        us.saveUser(user);
    }

    @Test
    void addUser() {
        //создание верного пользователя
        User user = new User(1, "@mail", "login2", "name2",
                LocalDate.of(1996, 7, 24), new HashSet<>());
        us.saveUser(user);
        assertNotNull(us.getUsers().get(1));
        assertEquals(2, us.getUsers().size());

    }

    @Test
    void getUsers() {
        List<User> checkList = new ArrayList<>();
        User checkUser = new User(1, "@mail", "login1", "name1",
                LocalDate.of(1996, 7, 24), new HashSet<>());
        checkList.add(checkUser);
        assertEquals(1, us.getUsers().size());
        assertEquals(checkList, us.getUsers());
    }

    @Test
    void updateUsers() {
        User user = new User(1, "@mail", "updateLogin1", "updateName1",
                LocalDate.of(1996, 7, 24), new HashSet<>());
        us.updateUser(user);
        assertEquals("updateLogin1", us.getUsers().get(0).getLogin());
    }

    @Test
    void getFilms() {
        List<Film> films = new ArrayList<>();
        Film film = new Film(1, "nameFilm", "description",
                LocalDate.of(2001, 12, 1), 120, new HashSet<>());
        films.add(film);
        assertEquals(1, fs.getAllFilms().size());
        assertEquals(films, fs.getAllFilms());
    }

    @Test
    void addFilm() {
        Film film2 = new Film(1, "nameFilm2", "description2",
                LocalDate.of(2002, 1, 1), 120, new HashSet<>());
        fs.saveFilm(film2);
        assertNotNull(fs.getAllFilms().get(1));
        assertEquals(2, fs.getAllFilms().size());
    }

    @Test
    void updateFilm() {
        Film updateFilm = new Film(1, "updateNameFilm", "updateDescription",
                LocalDate.of(2011, 3, 2), 120, new HashSet<>());
        fs.updateFilm(updateFilm);
        assertEquals(updateFilm, fs.getAllFilms().get(0));
    }

    @Test
    void addLike() {
        fs.addLike(fs.getFilm(1), 1);
        assertEquals(1, fs.getFilm(1).getUserID().size());
    }

    @Test
    void delLike() {
        fs.addLike(fs.getFilm(1), 1);
        fs.deleteLike(fs.getFilm(1), 1);
        assertEquals(0, fs.getFilm(1).getUserID().size());
    }

    @Test
    void addNewFriend() {
        User user = new User(1, "@mail", "login3", "name3",
                LocalDate.of(2006, 1, 14), new HashSet<>());
        us.saveUser(user);
        us.addFriend(us.getUser(1), us.getUser(2));
        assertEquals(1, us.getUser(2).getFriendsIDs().size());
        assertEquals(1, us.getUser(1).getFriendsIDs().size());
    }

    @Test
    void delFriend() {
        User user = new User(1, "@mail", "login3", "name3",
                LocalDate.of(2006, 1, 14), new HashSet<>());
        us.saveUser(user);
        us.addFriend(us.getUser(1), us.getUser(2));
        us.deleteFriend(us.getUser(1), us.getUser(2));
        assertEquals(0, us.getUser(2).getFriendsIDs().size());
        assertEquals(0, us.getUser(1).getFriendsIDs().size());
    }
}
