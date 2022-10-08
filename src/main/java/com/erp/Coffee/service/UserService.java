package com.erp.Coffee.service;

import com.erp.Coffee.controller.admin.exception.UserAlreadyExistsException;
import com.erp.Coffee.model.Role;
import com.erp.Coffee.model.User;
import com.erp.Coffee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllBaristas() {
        return userRepository.findAllBaristas("BARISTA").orElseThrow(RuntimeException::new);
    }

    public void saveUser(User user) throws UserAlreadyExistsException {
        Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());

        if (userByUsername.isPresent()) {
            throw new UserAlreadyExistsException("Пользователь уже существует");
        }

        user.setRole(Role.BARISTA);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        user.setRole(Role.BARISTA);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
