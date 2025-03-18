package io.reflectoring.AddressBookApp.Services;

import io.reflectoring.AddressBookApp.DTOs.UserDTO;
import io.reflectoring.AddressBookApp.Entities.User;
import io.reflectoring.AddressBookApp.Interfaces.IUserService;
import io.reflectoring.AddressBookApp.Repository.UserRepository;
import io.reflectoring.AddressBookApp.Security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil; // Injecting JWT Util Class

    @Override
    public String registerUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            log.error("User already exists");
            return "User already registered";
        }

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(hashedPassword);

        userRepository.save(newUser);
        log.info("User registered successfully");
        return "User registered successfully";
    }

    @Override
    public String loginUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                log.info("User login successful");

                // ✅ Generating JWT Token after successful login
                String token = jwtUtil.generateToken(user.getEmail());
                return token; // Returning JWT Token
            } else {
                log.error("Invalid credentials");
                return "Invalid credentials";
            }
        } else {
            log.error("User not found");
            return "User not found";
        }
    }
}
