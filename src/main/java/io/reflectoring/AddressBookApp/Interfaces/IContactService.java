package io.reflectoring.AddressBookApp.Interfaces;

import io.reflectoring.AddressBookApp.DTOs.ContactDTO;
import java.util.List;
import java.util.Optional;

public interface IContactService {
    ContactDTO addContact(ContactDTO contactDTO);
    Optional<ContactDTO> getContactById(Long id);
    List<ContactDTO> getAllContacts();
    void deleteContact(Long id);
}
