package ru.yandex.practicum.filmorate.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        if (localDate != null) {
            jsonWriter.value(localDate.format(FORMATTER));
            return;
        }
        jsonWriter.value(String.valueOf(localDate));
    }


    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        final String text = jsonReader.nextString();
        LocalDate time;
        if (text.equals("null")) {
            return null;
        }
        time = LocalDate.parse(text, FORMATTER);
        return time;
    }

}