package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.mpa.MpaRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@Service
public class MpaService {
    @Autowired
    MpaRepository mr;

    public List<MPA> getListMpa() {
        return mr.getAllMpa();
    }

    public MPA getMpaById(int id) {
        if (id < 1 || id > 5) {
            throw new NotFoundException("Нет такого id у мпа");
        }
        return mr.getMpaById(id);
    }
}
