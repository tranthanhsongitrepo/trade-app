package com.backend.tradeappbackend.admin;

import com.backend.tradeappbackend.user.User;
import com.backend.tradeappbackend.user.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdminService {
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public Collection<User> getAllUsers(int page, int size) {
        return this.userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public User addUser(User user) {
        return this.userRepository.save(user);
    }
}
