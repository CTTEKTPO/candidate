package com.example.candidate.component;

import com.example.candidate.model.User;
import com.example.candidate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Проверяем наличие пользователя admin
        if (userRepository.countByUsername("admin") == 0) {
            // Если пользователя нет, создаем его
            User adminUser = User.builder()
                    .employeeName("Admin User")
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .enabled(true)
                    .authority("full")
                    .build();

            userRepository.save(adminUser);
        }
    }
}
