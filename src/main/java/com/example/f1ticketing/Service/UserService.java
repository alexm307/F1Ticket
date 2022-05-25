package com.example.f1ticketing.Service;

import com.example.f1ticketing.DTO.LoginDTO;
import com.example.f1ticketing.DTO.RegisterDTO;
import com.example.f1ticketing.Mapper.UserMapper;
import com.example.f1ticketing.Model.Role;
import com.example.f1ticketing.Model.User;
import com.example.f1ticketing.Repositoy.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findAll().stream().filter(item->item.getUsername().equals(username)).collect(Collectors.toList()).get(0);
        if (user == null) {
            log.error("User {} not found in the database!", username);
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    /**
     * Registers a new user
     *
     * @param registerDTO the new account data of the user
     * @return the new user
     */
    public User register(RegisterDTO registerDTO) {
        User user = new UserMapper().convertFromDTO(registerDTO);

        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        //user.setRole(Role.REGULAR);
        //user.setRole(Role.ORGANIZER);

        log.info("New user registered: {}", registerDTO.getName());

        if (Validator.getInstance().isUserValid(user))
            return userRepository.save(user);
        else return null;
    }

    /**
     * Logs the user in
     *
     * @param loginDTO log in data
     * @return the user
     */
    public User login(LoginDTO loginDTO) {

        List<User> users = userRepository.findAll();
        users = users.stream().filter(item -> item.getUsername().equals(loginDTO.getUsername())).collect(Collectors.toList());
        if (users.isEmpty()) {
            System.out.println("NO USER WITH SUCH USERNAME");
            log.error("Credentials for user {} do not match!", loginDTO.getUsername());
            return null;
        }
        if (users.get(0).getPassword().equals(loginDTO.getPassword()))
            return users.get(0);
        return null;
    }
}
