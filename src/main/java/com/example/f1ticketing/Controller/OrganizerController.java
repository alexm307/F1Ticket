package com.example.f1ticketing.Controller;

import com.example.f1ticketing.DTO.RaceDTO;
import com.example.f1ticketing.Service.RaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @Slf4j
@RequestMapping(path = "/organizer")
public class OrganizerController {

    @Autowired
    private RaceService raceService;

    @PostMapping("/addRace")
    public void createRace(@RequestBody RaceDTO raceDTO) {
        log.info("creating race: {}", raceDTO.getName());
        System.out.println(raceDTO.getName());
        raceService.createRace(raceDTO);
    }

    @GetMapping("/getRaces")
    public List<RaceDTO> getRaces() {
        log.info("retrieving all races");
        return raceService.getRaces();
    }
}
