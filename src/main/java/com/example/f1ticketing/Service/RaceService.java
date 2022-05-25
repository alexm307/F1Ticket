package com.example.f1ticketing.Service;

import com.example.f1ticketing.DTO.RaceDTO;
import com.example.f1ticketing.Mapper.RaceMapper;
import com.example.f1ticketing.Model.Race;
import com.example.f1ticketing.Model.User;
import com.example.f1ticketing.Repositoy.RaceRepository;
import com.example.f1ticketing.Repositoy.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new race
     * @param raceDTO data for the new race
     */
    public void createRace(RaceDTO raceDTO) {

        RaceMapper raceMapper = new RaceMapper();

        Race newRace = raceMapper.convertFromDTO(raceDTO);

        User organiser = userRepository.getById(raceDTO.getOrganizerID());
        newRace.setOrganizer(organiser);

        raceRepository.save(newRace);
    }

    /**
     * Retrieves all races
     * @return a list of race DTOs
     */
    public List<RaceDTO> getRaces() {
        List<Race> races = raceRepository.findAll();
        List<RaceDTO> raceDTOS = new ArrayList<>();

        RaceMapper raceMapper = new RaceMapper();

        for (Race r : races) {
            raceDTOS.add(raceMapper.convertFromRace(r));
        }

        return raceDTOS;
    }

}
