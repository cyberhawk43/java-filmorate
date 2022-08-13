package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;




import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TestFilmorate {

    private final UserService userService;
    private final FilmService filmService;
    private final GenreService gs;
    private final MpaService ms;

    private User testUser1 = new User(
            1,
            "test1@email.ru",
            "test1",
            "name1",
            LocalDate.of(2007, 1, 1)
    );
    private User testUser2 = new User(
            2,
            "test2@email.ru",
            "test2",
            "name2",
            LocalDate.of(2014, 1, 1)
    );
    private User testUser3 = new User(
            3,
            "test3@email.ru",
            "test3",
            "name3",
            LocalDate.of(2013, 1, 1)
    );

    private Film film1 = new Film(
            1,
            "film1",
            "desc1",
            LocalDate.of(2003, 4, 12),
            120,
            4,
            new MPA(1, "G"));

    private Film film2 = new Film(
            1,
            "film2",
            "desc2",
            LocalDate.of(2001, 2, 13),
            60,
            4,
            new MPA(2, "PG"));

    @Test
    public void addUser() {
        assertEquals(0, userService.getUsers().size());
        userService.addNewUser(testUser1);
        assertEquals(1, userService.getUsers().size());
        userService.addNewUser(testUser2);
        assertEquals(2, userService.getUsers().size());
    }

    @Test
    public void updateUser() {
        assertEquals(0, userService.getUsers().size());
        userService.addNewUser(testUser1);
        User user = userService.getUserByID(1);
        user.setLogin("otherLogin");
        userService.updateUser(user);
        assertEquals("otherLogin", userService.getUserByID(1).getLogin());
    }

    @Test
    public void addFilm() {
        assertEquals(0, filmService.getAllFilms().size());
        filmService.addNewFilm(film1);
        assertEquals(1, filmService.getAllFilms().size());
        filmService.addNewFilm(film2);
        assertEquals(2, filmService.getAllFilms().size());
    }

    @Test
    public void updateFilm() {
        assertEquals(0, filmService.getAllFilms().size());
        filmService.addNewFilm(film1);
        filmService.updateFilm(film2);
        assertEquals("desc2", filmService.getFilmByID(1).getDescription());
    }

    @Test
    public void getMpa() {
        assertEquals(5, ms.getListMpa().size());
        assertEquals("G", ms.getMpaById(1).getName());
        assertEquals("PG-13", ms.getMpaById(3).getName());
    }

    @Test
    public void getGenre() {
        assertEquals(6, gs.getAllGenre().size());
        assertEquals("Драма", gs.getGenreById(2).getName());
        assertEquals("Мультфильм", gs.getGenreById(3).getName());
        assertEquals("Документальный", gs.getGenreById(5).getName());
    }

    @Test
    public void getTopFilms() {
        filmService.addNewFilm(film1);
        filmService.addNewFilm(film2);
        userService.addNewUser(testUser1);
        userService.addNewUser(testUser2);
        filmService.addNewLike(2, 1);
        filmService.addNewLike(2, 2);
        Film topFilm = filmService.getTop(1).get(0);
        assertEquals(film2.getName(), topFilm.getName());
    }

    @Test
    public void getFriends() {
        userService.addNewUser(testUser1);
        userService.addNewUser(testUser2);
        userService.addNewUser(testUser3);
        userService.addNewFriend(1, 3);
        userService.addNewFriend(2, 3);
        assertEquals(1, userService.getListFriends(1).size());
        assertEquals(1, userService.getListMutualFriends(1, 2).size());
        assertEquals(testUser3, userService.getListMutualFriends(1, 2).get(0));

    }
}





