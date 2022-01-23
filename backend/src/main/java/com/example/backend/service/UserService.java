package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) throws IOException {
        Resource image = new ClassPathResource("/static/assets/images/default-profile.jpg");
        user.setProfilePhoto(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
        userRepository.save(user);
    }
}
