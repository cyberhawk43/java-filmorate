package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class MPA {
    private final int id;
    private final String name;
}
