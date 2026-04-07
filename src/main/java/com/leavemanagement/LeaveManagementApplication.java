package com.leavemanagement;

import com.leavemanagement.entity.Role;
import com.leavemanagement.entity.User;
import com.leavemanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LeaveManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaveManagementApplication.class, args);
    }

    /**
     * Seeds three default users (one per role) on startup if they don't exist.
     * Passwords are BCrypt-encoded.
     *
     * Credentials for Postman:
     *   employee / password  → role: EMPLOYEE
     *   manager  / password  → role: MANAGER
     *   hr       / password  → role: HR
     */
    @Bean
    public CommandLineRunner dataInitializer(UserRepository userRepository,
                                             PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("employee").isEmpty()) {
                userRepository.save(User.builder()
                        .username("employee")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.EMPLOYEE)
                        .build());
            }

            if (userRepository.findByUsername("manager").isEmpty()) {
                userRepository.save(User.builder()
                        .username("manager")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.MANAGER)
                        .build());
            }

            // Added missing HR seed user
            if (userRepository.findByUsername("hr").isEmpty()) {
                userRepository.save(User.builder()
                        .username("hr")
                        .password(passwordEncoder.encode("password"))
                        .role(Role.HR)
                        .build());
            }
        };
    }
}
