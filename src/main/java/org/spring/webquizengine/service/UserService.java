package org.spring.webquizengine.service;


import lombok.RequiredArgsConstructor;
import org.spring.webquizengine.dto.UserDTO;
import org.spring.webquizengine.entity.UserQuiz;
import org.spring.webquizengine.exceptions.UserAlreadyExistsException;
import org.spring.webquizengine.mappers.UserMapper;
import org.spring.webquizengine.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserQuiz userQuiz = userRepository.findAnyByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(userQuiz.getEmail(), userQuiz.getPassword(), new ArrayList<>());

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public boolean isEmailAvailable(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Given Email is already registered");
        }
        return true;
    }

    public void saveUser(UserDTO userDTO) {
        userRepository.save(userMapper.toEntity(userDTO));
    }

    public UserQuiz getUser(String email) {
        return userRepository.findByEmail(email);
    }
}

