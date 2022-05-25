package com.example.f1ticketing.Service;

import com.example.f1ticketing.DTO.PackageDTO;
import com.example.f1ticketing.Mapper.PackageMapper;
import com.example.f1ticketing.Model.Package;
import com.example.f1ticketing.Model.Race;
import com.example.f1ticketing.Repositoy.PackageRepository;
import com.example.f1ticketing.Repositoy.RaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private RaceRepository raceRepository;

    /**
     * creates a new package
     * @param packageDTO data for the package
     */
    public void addPackage(PackageDTO packageDTO) {
        log.info("Attempting to add a new Package");
        PackageMapper packageMapper = new PackageMapper();

        Package newPackage = packageMapper.convertFromDTO(packageDTO);

        Race race = raceRepository.getById(packageDTO.getRaceID());
        newPackage.setRace(race);

        packageRepository.save(newPackage);
    }

    /**
     * Retrieves all packages
     * @return list pf package DTOs
     */
    public List<PackageDTO> getPackages() {
        List<Package> packages = packageRepository.findAll();
        List<PackageDTO> packageDTOS = new ArrayList<>();

        PackageMapper packageMapper = new PackageMapper();

        for (Package p : packages) {
            packageDTOS.add(packageMapper.convertToDTO(p));
        }
        return packageDTOS;
    }
}
