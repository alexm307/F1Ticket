package com.example.f1ticketing.Mapper;

import com.example.f1ticketing.DTO.PackageDTO;
import com.example.f1ticketing.Model.Package;
import com.example.f1ticketing.Model.Race;
import com.example.f1ticketing.Model.Standard;
import com.example.f1ticketing.Repositoy.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

public class PackageMapper {

    public Package convertFromDTO(PackageDTO packageDTO) {
        Package aPackage = new Package();

        System.out.println(packageDTO.getNoOfTickets());

        aPackage.setName(packageDTO.getName());
        aPackage.setPrice(packageDTO.getPrice());
        aPackage.setNumberOfTickets(packageDTO.getNoOfTickets());
        aPackage.setStandard(Standard.valueOf(packageDTO.getStandard()));
        aPackage.setFoodIncluded(packageDTO.getFoodIncluded().toLowerCase(Locale.ROOT).contains("y"));

        return aPackage;
    }

    public PackageDTO convertToDTO(Package p) {
        PackageDTO packageDTO = new PackageDTO();

        if (p.getFoodIncluded())
            packageDTO.setFoodIncluded("yes :)");
        else
            packageDTO.setFoodIncluded("no :(");
        packageDTO.setName(p.getName());
        packageDTO.setPrice(p.getPrice());
        packageDTO.setStandard(p.getStandard().name());
        packageDTO.setRaceID(p.getRace().getId());
        packageDTO.setNoOfTickets(p.getNumberOfTickets());
        packageDTO.setId(p.getId());
        System.out.println(p.getId());

        return packageDTO;
    }
}
