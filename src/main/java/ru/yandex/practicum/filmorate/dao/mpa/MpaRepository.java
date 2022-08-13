package ru.yandex.practicum.filmorate.dao.mpa;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;


public interface MpaRepository {
    List<MPA> getAllMpa();

    MPA getMpaById(int id);

}
