package io.reflectoring.AddressBookApp.Interfaces;

import io.reflectoring.AddressBookApp.DTOs.UserDTO;

public interface IUserService {
    String registerUser(UserDTO userDTO);
    String loginUser(UserDTO userDTO);
}

