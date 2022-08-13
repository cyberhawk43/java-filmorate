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
    private int rate;
    private MPA mpa;
    private Set<Genre> genres = new HashSet<>();

    private Set<Integer> userID = new HashSet<>();

    public Film(int id, String name, String description, LocalDate releaseDate, int duration, int rate,MPA mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
        this.mpa = mpa;
    }
}
