package com.example.coursework.service;

import com.example.coursework.model.User;
import com.example.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), Collections.emptyList());
    }
}
