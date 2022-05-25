package com.example.f1ticketing.Service;

import com.example.f1ticketing.DTO.OrderDTO;
import com.example.f1ticketing.Model.Order;
import com.example.f1ticketing.Model.Package;
import com.example.f1ticketing.Model.Race;
import com.example.f1ticketing.Model.User;
import com.example.f1ticketing.Repositoy.OrderRepository;
import com.example.f1ticketing.Repositoy.PackageRepository;
import com.example.f1ticketing.Repositoy.RaceRepository;
import com.example.f1ticketing.Repositoy.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class OrderService {

    private Integer raceID;
    private HashMap<Package, Integer> cart;
    private int totalPrice;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Empties the cart
     */
    public void emptyCart() {
        log.info("Cart is being emptied");
        raceID = null;
        cart = new HashMap<>();
        totalPrice = 0;
    }

    /**
     * Adds a new package to the cart
     *
     * @param packID id of the pack to be added
     */
    public void addToCart(Integer packID) {
        Package p = packageRepository.getById(packID);

        log.info("Placing pack {} to cart", packID);

        if (cart == null)
            cart = new HashMap<>();
        if (cart.isEmpty()) {
            raceID = p.getRace().getId();
            totalPrice = p.getPrice();
        }
        else
        if (raceID != p.getRace().getId()) {          // here I am making sure that
            emptyCart();                                            // the client cannot order from different
            raceID = p.getRace().getId();           // races within the same order
            totalPrice = p.getPrice();
        }
        else
            totalPrice += p.getPrice();
        cart.putIfAbsent(p, 1);
        cart.computeIfPresent(p, (a, b)->b + 1);
    }

    /**
     * Places an order for the given user.
     *
     * @param userID the id of the user placing the order
     */
    public void placeOrder(Integer userID) {
        Race race = raceRepository.getById(raceID);

        log.info("Placing an order for user with ID {}", userID);

        User user = userRepository.getById(userID);

        Order newOrder = new Order();
        newOrder.setRace(race);
        newOrder.setTotalPrice(totalPrice);
        newOrder.setUser(user);

        orderRepository.save(newOrder);
    }

    /**
     * Fetches orders based on a given user which has placed them.
     *
     * @param userID id of the particular user
     * @return a list of orders
     */
    public List<OrderDTO> getOrdersByUser(Integer userID) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        List<Order> orders = orderRepository.findAll().stream().filter(item -> item.getUser().getId() == userID).collect(Collectors.toList());

        log.info("Getting order history of user {}", userID);

        for (Order o : orders) {
            OrderDTO newOrderDTO = new OrderDTO();
            newOrderDTO.setPrice(o.getTotalPrice());
            newOrderDTO.setrName(o.getRace().getName());
            orderDTOS.add(newOrderDTO);
        }
        return orderDTOS;
    }
}
