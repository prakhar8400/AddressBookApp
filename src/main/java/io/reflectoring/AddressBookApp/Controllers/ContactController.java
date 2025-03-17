package io.reflectoring.AddressBookApp.Controllers;

import io.reflectoring.AddressBookApp.DTOs.ContactDTO;
import io.reflectoring.AddressBookApp.Interfaces.IContactService;
import jakarta.validation.Valid;
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
    private IContactService contactService;

    @PostMapping("/add")
    public ContactDTO addContact(@Valid @RequestBody ContactDTO contactDTO) {
        log.info("Adding new contact");
        return contactService.addContact(contactDTO);
    }

    @GetMapping("/{id}")
    public Optional<ContactDTO> getContactById(@PathVariable Long id) {
        log.info("Fetching contact with ID: {}", id);
        return contactService.getContactById(id);
    }

    @GetMapping("/all")
    public List<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts");
        return contactService.getAllContacts();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        log.warn("Deleting contact with ID: {}", id);
        contactService.deleteContact(id);
        return "Contact deleted successfully";
    }
}
