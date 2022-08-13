package ru.yandex.practicum.filmorate.contoller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
@Getter
@Setter
public class MpaController {

    @Autowired
    MpaService ms;

    @GetMapping("/mpa")
    public List<MPA> getListMpa() {
        log.info("получен список мпа рейтинга");
        return ms.getListMpa();
    }

    @GetMapping("/mpa/{id}")
    public MPA getMpaById(@PathVariable int id) {
        log.info("получен мпа рейтинг c id " + id);
        return ms.getMpaById(id);
    }
}
