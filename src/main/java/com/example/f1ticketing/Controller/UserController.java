package com.example.f1ticketing.Controller;

import com.example.f1ticketing.DTO.*;
import com.example.f1ticketing.Model.User;
import com.example.f1ticketing.Service.OrderService;
import com.example.f1ticketing.Service.PackageService;
import com.example.f1ticketing.Service.RaceService;
import com.example.f1ticketing.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController @Slf4j
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PackageService packageService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        log.info("user {} attempting to log in", loginDTO.getUsername());
        User user = userService.login(loginDTO);
        System.out.println(loginDTO.getUsername() + " "  + loginDTO.getPassword());
        if (user == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something was wrong when logging in!");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setName(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        log.info("new register request has been placed");
        User newUser = userService.register(registerDTO);
        if (newUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something was wrong when registering!");
        return ResponseEntity.status(HttpStatus.OK).body("Succesfully registered!");
    }

    @GetMapping("/PackByRest/{rID}")
    public List<PackageDTO> getPackagesByRaceID(@PathVariable Integer rID) {
        log.info("retrieving packages based on race ID: {}", rID);
        List<PackageDTO> packageDTOS = packageService.getPackages()
                                                     .stream()
                                                     .filter(item -> item.getRaceID().equals(rID))
                                                     .collect(Collectors.toList());
        System.out.println(packageDTOS.size());
        return packageDTOS;
    }

    @GetMapping("/getRaces")
    public List<RaceDTO> getRaces() {
        log.info("Retrieving races");
        return raceService.getRaces();
    }

    @PostMapping("/addToCart/{packID}")
    public void addToCart(@PathVariable Integer packID) {
        log.info("Adding package {} to cart", packID);
        orderService.addToCart(packID);
    }

    @PostMapping("/order/{userID}")
    public void placeOrder(@PathVariable Integer userID) {
        log.info("placing a order for the user with ID {}",userID);
        orderService.placeOrder(userID);
    }

    @PostMapping("/getOrders/{userID}")
    public List<OrderDTO> getOrders(@PathVariable Integer userID) {
        log.info("Getting order history of user {}", userID);
        return orderService.getOrdersByUser(userID);
    }
}
