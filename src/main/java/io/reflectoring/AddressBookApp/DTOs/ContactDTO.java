package io.reflectoring.AddressBookApp.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String name;
    private String email;
    private String phone;
}

