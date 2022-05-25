package com.example.f1ticketing.Controller;

import com.example.f1ticketing.DTO.PackageDTO;
import com.example.f1ticketing.DTO.RaceDTO;
import com.example.f1ticketing.Service.PackageService;
import com.example.f1ticketing.Service.RaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @Slf4j
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private RaceService raceService;

    @PostMapping("/addPackage")
    public void addPackage(@RequestBody PackageDTO packageDTO) {
        log.info("Adding package with name {}", packageDTO.getName());
        packageService.addPackage(packageDTO);
    }

    @GetMapping("/packages")
    public List<PackageDTO> getPackages() {
        log.info("retrieving all packages");
        return packageService.getPackages();
    }

    @PostMapping("/races")
    public List<RaceDTO> getRaces() {
        log.info("retirieving all races for the admin");
        return raceService.getRaces();
    }
}
