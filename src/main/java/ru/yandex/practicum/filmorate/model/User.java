package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    int id;
    String email;
    String login;
    String name;
    LocalDate birthday;
}
