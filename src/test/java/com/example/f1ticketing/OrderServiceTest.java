package com.example.f1ticketing;

import com.example.f1ticketing.Model.Order;
import com.example.f1ticketing.Model.Package;
import com.example.f1ticketing.Model.Race;
import com.example.f1ticketing.Model.User;
import com.example.f1ticketing.Repositoy.OrderRepository;
import com.example.f1ticketing.Repositoy.PackageRepository;
import com.example.f1ticketing.Repositoy.RaceRepository;
import com.example.f1ticketing.Repositoy.UserRepository;
import com.example.f1ticketing.Service.OrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderServiceTest {

    private Race race;
    private List<Package> packageList;
    private User user;
    private User organizer;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService = new OrderService();

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @Before
    public void setup() {
        packageList = new ArrayList<>();
        organizer = new User();
        organizer.setUsername("org");

        user = new User();
        user.setUsername("user");

        race = new Race();
        race.setName("Some GP");
        race.setLocation("Spain without P");
        race.setOrganizer(organizer);

        Package p = new Package();
        p.setRace(race);
        p.setPrice(10);

        packageList.add(p);
    }

    @Test
    public void successfulOrder() {
        Mockito.doReturn(packageList.get(0)).when(packageRepository).getById(packageList.get(0).getId());
        Mockito.doReturn(race).when(raceRepository).getById(race.getId());
        Mockito.doReturn(user).when(userRepository).getById(user.getId());

        orderService.addToCart(packageList.get(0).getId());
        orderService.placeOrder(user.getId());

        Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());
        Assert.assertEquals(orderArgumentCaptor.getValue().getTotalPrice(), packageList.get(0).getPrice());
    }
}
