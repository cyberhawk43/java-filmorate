package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class Film {
    Integer id;
    String name;
    String description;
    LocalDateTime releaseDate;
    Duration duration;
}
