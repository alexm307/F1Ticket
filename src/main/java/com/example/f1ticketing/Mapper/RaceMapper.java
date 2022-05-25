package com.example.f1ticketing.Mapper;

import com.example.f1ticketing.DTO.RaceDTO;
import com.example.f1ticketing.Model.Race;
import com.example.f1ticketing.Model.User;
import com.example.f1ticketing.Repositoy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RaceMapper {


    public Race convertFromDTO(RaceDTO raceDTO){
        Race newRace = new Race();

        newRace.setLocation(raceDTO.getLocation());
        newRace.setName(raceDTO.getName());
        return newRace;
    }

    public RaceDTO convertFromRace(Race race) {
        RaceDTO raceDTO = new RaceDTO();

        raceDTO.setLocation(race.getLocation());
        raceDTO.setName(race.getName());
        raceDTO.setOrganizerID(race.getOrganizer().getId());
        raceDTO.setId(race.getId());

        return raceDTO;
    }
}
