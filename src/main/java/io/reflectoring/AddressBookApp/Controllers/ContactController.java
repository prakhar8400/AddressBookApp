package io.reflectoring.AddressBookApp.Controllers;

import io.reflectoring.AddressBookApp.DTOs.ContactDTO;
import io.reflectoring.AddressBookApp.DTOs.ResponseDTO;
import io.reflectoring.AddressBookApp.Services.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/post")
    public ResponseDTO addContact(@RequestBody ContactDTO contactDTO) {
        log.info("Received request to add contact: {}", contactDTO.getName());
        ContactDTO savedContact = contactService.addContact(contactDTO);
        log.info("Contact added with ID: {}", savedContact.getId());
        return new ResponseDTO("Contact added successfully", savedContact);
    }

    @GetMapping("/{id}")
    public ResponseDTO getContactById(@PathVariable Long id) {
        log.info("Fetching contact with ID: {}", id);
        Optional<ContactDTO> contact = contactService.getContactById(id);
        return contact.map(value -> {
            log.info("Returning contact: {}", value.getName());
            return new ResponseDTO("Returning contact", value);
        }).orElseGet(() -> {
            log.warn("Contact with ID {} not found", id);
            return new ResponseDTO("Contact not found", null);
        });
    }

    @GetMapping
    public List<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts");
        return contactService.getAllContacts();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteContact(@PathVariable Long id) {
        log.warn("Deleting contact with ID: {}", id);
        contactService.deleteContact(id);
        return new ResponseDTO("Deleted contact", null);
    }
}
