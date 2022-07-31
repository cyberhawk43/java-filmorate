package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    Set<Integer> userID = new HashSet<>();
}
