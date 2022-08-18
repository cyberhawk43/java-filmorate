package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.mpa.MpaRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@Service
public class MpaService {

    private final MpaRepository mpaRepository;

    public MpaService(MpaRepository mpaRepository) {
        this.mpaRepository = mpaRepository;
    }

    public List<MPA> getListMpa() {
        return mpaRepository.getAllMpa();
    }

    public MPA getMpaById(int id) {
        if (id < 0 || id > getListMpa().size()) {
            throw new NotFoundException("id мпа за пределами таблицы");
        }
        return mpaRepository.getMpaById(id);
    }
}
