package com.example.coursework.controller;

import com.example.coursework.model.Role;
import com.example.coursework.model.ShoppingCart;
import com.example.coursework.model.User;
import com.example.coursework.repository.RoleRepository;
import com.example.coursework.repository.UserRepository;
import com.example.coursework.service.ShoppingCartService;
import com.example.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String userName,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String phone,
                               @RequestParam String email,
                               @RequestParam String password) {
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);

        Role role = roleRepository.findByName("USER");
        user.getRoles().add(role);

        userService.saveUser(user);

        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(user.getId());

        return "redirect:/login";
    }
}
