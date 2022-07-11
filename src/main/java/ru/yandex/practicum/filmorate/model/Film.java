package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Film {
    int id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
}
