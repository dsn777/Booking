/*
package com.example.Booking.RestControllers;

import com.example.Booking.Entity.User;
import com.example.Booking.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/new")
    public String register() {
        return "regForm";
    }

    @PostMapping("/new")
    @ResponseBody
    public User saveUser(User user) {
//        String password = user.getPassword();
//        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }

    @ResponseBody
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(new User());
    }
}
*/
