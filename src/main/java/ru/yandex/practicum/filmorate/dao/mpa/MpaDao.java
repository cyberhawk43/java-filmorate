package ru.yandex.practicum.filmorate.dao.mpa;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Select;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MpaDao implements MpaRepository {
    private final JdbcTemplate jdbc;

    @Override
    public List<MPA> getAllMpa() {
        String sqlQuery = "select * from RATING_MPA";
        List<MPA> listMpa = jdbc.query(sqlQuery, MpaDao::makeMPA);
        return listMpa;
    }

    @Override
    public MPA getMpaById(int id) {
        if (id > 5) {

        }
        String sqlQuery = "select * from RATING_MPA where ID_RATING = ?";
        List<MPA> listMpa = jdbc.query(sqlQuery, MpaDao::makeMPA, id);
        if (listMpa.size() > 1) {
            return null;
        }
        return listMpa.get(0);
    }

    static MPA makeMPA(ResultSet rs, int rowNum) throws SQLException {
        return new MPA(rs.getInt("ID_RATING"),
                rs.getString("RATING_MPA.NAME_OF_RATING"));
    }


}
