package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.user.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDbStorage implements UserRepository {

    private final JdbcTemplate jdbc;

    public UserDbStorage(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User getById(int id) {
        final String sqlQuery = "select * " +
                "from USERS " +
                "where USER_ID = ?";
        final List<User> users = jdbc.query(sqlQuery, UserDbStorage::makeUser, id);
        if (users.size() != 1) {
            return null;
        }
        return users.get(0);
    }

    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("USER_ID"),
                rs.getString("USER_EMAIL"),
                rs.getString("USER_LOGIN"),
                rs.getString("USER_NAME"),
                rs.getDate("BIRTHDAY").toLocalDate());
    }

    @Override
    public List<User> getAllUsers() {
        final String sqlQuery = "select *" +
                "FROM USERS";
        return jdbc.query(sqlQuery, UserDbStorage::makeUser);
    }

    @Override
    public User createUser(User user) {
        final String sqlQuery = "insert into USERS(USER_EMAIL, USER_LOGIN, USER_NAME, BIRTHDAY) " +
                "values ( ?,?,?,? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement statement = con.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            final LocalDate birthday = user.getBirthday();
            if (birthday == null) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(4, Date.valueOf(birthday));
            }
            return statement;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update USERS set " +
                "USER_EMAIL = ?, " +
                "USER_LOGIN = ?, " +
                "USER_NAME = ?, " +
                "BIRTHDAY = ? " +
                "where USER_ID = ?";
        jdbc.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public void addFriend(User user, User friend) {
        String sqlQuery = "merge into FRIENDS (USER_ID, FRIEND_ID) values ( ?,?)";
        jdbc.update(sqlQuery,
                user.getId(),
                friend.getId());
    }

    @Override
    public void delFriend(User user, User friend) {
        String sqlQuery = "delete from FRIENDS " +
                "where FRIEND_ID = ? and USER_ID = ?";
        jdbc.update(sqlQuery,
                friend.getId(),
                user.getId()

        );
    }

    static int makeFriendId(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("FRIEND_ID");
    }

    @Override
    public List<Integer> getIdFriends(int userId) {
        String sqlQuery = "select FRIEND_ID from FRIENDS where USER_ID = ?";
        List<Integer> friendsId = jdbc.query(sqlQuery, UserDbStorage::makeFriendId, userId);
        return friendsId;
    }

    @Override
    public List<Integer> getIdMutualFriend(int userId, int otherUserId) {
        String sqlQuery = "SELECT F1.FRIEND_ID FROM FRIENDS AS F1 join FRIENDS AS F2 WHERE " +
                "F1.USER_ID = ? AND F2.USER_ID = ? AND F1.FRIEND_ID = F2.FRIEND_ID";
        List<Integer> mutualFriendsId = jdbc.query(sqlQuery, UserDbStorage::makeFriendId, userId, otherUserId);
        return mutualFriendsId;
    }

}
