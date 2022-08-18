package ru.yandex.practicum.filmorate.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenreDao implements GenreRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Genre getGenreById(int id) {
        String sqlQuery = "select * from GENRE where ID_GENRE = ? ";
        List<Genre> listGenre = jdbc.query(sqlQuery, GenreDao::makeGenre, id);
        if (listGenre.size() > 1) {
            return null;
        }
        return listGenre.get(0);
    }

    @Override
    public List<Genre> getAllGenre() {
        String sqlQuery = "select * from GENRE";
        return jdbc.query(sqlQuery, GenreDao::makeGenre);
    }

    static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("ID_GENRE"),
                rs.getString("NAME_OF_GENRE"));
    }
}
