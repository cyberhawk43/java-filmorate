package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.film.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;


import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class FilmDbStorage implements FilmRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Film getFilmById(int id) {
        final String sqlQuery = "select FILM_ID, " +
                "FILM_NAME, " +
                "FILM_DESCRIPTION, " +
                "FILM_RELEASE, " +
                "FILM_DURATION, " +
                "RATE, " +
                "ID_MPA, " +
                "ID_RATING, " +
                "NAME_OF_RATING" +
                " from FILMS left join RATING_MPA RM on FILMS.ID_MPA = RM.ID_RATING " +
                " where FILM_ID = ?";
        try {
            return jdbc.queryForObject(sqlQuery, this::makeFilm, id);
        } catch (EmptyResultDataAccessException exp) {
            exp.printStackTrace();
            return null;
        }
    }


    @Override
    public Film saveFilm(Film film) {
        String sqlQuery = "insert into FILMS (FILM_NAME, FILM_DESCRIPTION, FILM_RELEASE, FILM_DURATION, RATE, ID_MPA)" +
                "values (?,?,?,?,?,?); ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            final LocalDate release = film.getReleaseDate();
            if (release == null) {
                statement.setNull(3, Types.DATE);
            } else {
                statement.setDate(3, Date.valueOf(release));
            }
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getRate());
            statement.setInt(6, film.getMpa().getId());
            return statement;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update FILMS set " +
                "FILM_NAME = ?, " +
                "FILM_DESCRIPTION = ?," +
                "FILM_RELEASE = ?," +
                "FILM_DURATION = ?, " +
                "RATE = ?, " +
                "ID_MPA = ?" +
                "where FILM_ID = ?";
        jdbc.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        final String sqlQuery = "select * from FILMS left join RATING_MPA RM on FILMS.ID_MPA = RM.ID_RATING";
        final List<Film> films = jdbc.query(sqlQuery, this::makeFilm);
        return films;
    }

    @Override
    public List<Film> getTopFilms(int count) {
        String sqlQuery = "select FILMS.FILM_ID, " +
                "FILM_NAME, " +
                "FILM_DESCRIPTION, " +
                "FILM_RELEASE, " +
                "FILM_DURATION, " +
                "RATE, ID_MPA, " +
                "RM.NAME_OF_RATING, " +
                "COUNT(l.USER_ID)" +
                " from FILMS " +
                "left join RATING_MPA RM on FILMS.ID_MPA = RM.ID_RATING " +
                "left join LIKES L on FILMS.FILM_ID = L.FILM_ID " +
                "group by FILMS.FILM_ID " +
                "order by COUNT(l.USER_ID) desc " +
                "limit ?";
        List<Film> topFilms = jdbc.query(sqlQuery, this::makeFilm, count);
        return topFilms;
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sqlQuery = "MERGE INTO likes VALUES (?, ?);";
        jdbc.update(sqlQuery,
                filmId,
                userId);
    }

    @Override
    public void deleteLike(int filmId, int user_id) {
        String sqlQuery = "delete from LIKES where FILM_ID = ? and USER_ID = ?";
        jdbc.update(sqlQuery,
                filmId,
                user_id);
    }

    @Override
    public Set<Genre> getFilmGenres(int id) {
        String sql = "select fg.ID_GENRE, g.NAME_OF_GENRE " +
                "from film_genre AS fg " +
                "left join GENRE AS g ON fg.ID_GENRE = g.ID_GENRE " +
                "where fg.ID_FILM = ?";
        List<Genre> genres = jdbc.query(sql, (rs, rowNum) ->
                new Genre(rs.getInt("ID_GENRE"), rs.getString("NAME_OF_GENRE")), id);
        if (!genres.isEmpty()) {
            return new HashSet<>(genres);
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public void addFilmGenre(int filmId, int genreId) {
        String sqlQuery = "merge into FILM_GENRE values ( ?,? )";
        jdbc.update(sqlQuery, filmId, genreId);
    }

    @Override
    public void delGenreOnFilm(int filmId) {
        String sqlQuery = "delete from FILM_GENRE where ID_FILM = ?";
        jdbc.update(sqlQuery, filmId);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film newFilm = new Film(rs.getInt("FILM_ID"),
                rs.getString("FILM_NAME"),
                rs.getString("FILM_DESCRIPTION"),
                rs.getDate("FILM_RELEASE").toLocalDate(),
                rs.getInt("FILM_DURATION"),
                rs.getInt("RATE"),
                new MPA(rs.getInt("ID_MPA"), rs.getString("RATING_MPA.NAME_OF_RATING")));
        newFilm.setGenres(getFilmGenres(newFilm.getId()));
        return newFilm;
    }


}

