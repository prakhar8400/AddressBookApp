package io.reflectoring.AddressBookApp.Controllers;

import io.reflectoring.AddressBookApp.DTOs.UserDTO;
import io.reflectoring.AddressBookApp.Services.UserService;
import io.reflectoring.AddressBookApp.Security.JWTUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public String registerUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Registering new user");
        return userService.registerUser(userDTO);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO userDTO) {
        log.info("User login attempt");
        String user = userService.loginUser(userDTO);
        if (user != null) {
            return jwtUtil.generateToken(userDTO.getEmail());   // Return JWT Token
        } else {
            return "Invalid Credentials!";
        }
    }
}
