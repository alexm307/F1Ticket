package com.example.f1ticketing;

import com.example.f1ticketing.DTO.LoginDTO;
import com.example.f1ticketing.DTO.RegisterDTO;
import com.example.f1ticketing.Model.Role;
import com.example.f1ticketing.Model.User;
import com.example.f1ticketing.Repositoy.UserRepository;
import com.example.f1ticketing.Service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserService();

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Before
    public void setup() {

    }

    @Test
    public void successfulRegister() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName("name");
        registerDTO.setEmail("asea@yahoo.com");
        registerDTO.setPassword("1435");

        User user = new User();
        user.setUsername("name");
        user.setRole(Role.ADMIN);
        user.setEmail("asea@yahoo.com");
        user.setPassword("1435");

        List<User> users = new ArrayList<>();
        users.add(user);

        Mockito.doReturn(users).when(userRepository).findAll();

        userService.register(registerDTO);

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        LoginDTO loginDTO = new LoginDTO(registerDTO.getName(), registerDTO.getPassword());
        userService.login(loginDTO);
    }
}
