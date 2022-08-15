package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    @JsonIgnore
    Set<Integer> friendsIDs = new HashSet<>();

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
