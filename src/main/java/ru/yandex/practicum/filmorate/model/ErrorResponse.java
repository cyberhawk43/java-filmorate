package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.yandex.practicum.filmorate.exception.ErrorHandler;

@Getter
@Setter
@NoArgsConstructor

public class ErrorResponse {

    String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
