package io.reflectoring.AddressBookApp.Controllers;

import io.reflectoring.AddressBookApp.DTOs.ContactDTO;
import io.reflectoring.AddressBookApp.DTOs.ResponseDTO;
import io.reflectoring.AddressBookApp.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/post")
    public ResponseDTO addContact(@RequestBody ContactDTO contactDTO) {
        ContactDTO savedContact = contactService.addContact(contactDTO);
        return new ResponseDTO("Contact added successfully", savedContact);
    }

    @GetMapping("/{id}")
    public ResponseDTO getContactById(@PathVariable Long id) {
        Optional<ContactDTO> contact = contactService.getContactById(id);
        return contact.map(value -> new ResponseDTO("Returning contact", value))
                .orElseGet(() -> new ResponseDTO("Contact not found", null));
    }

    @GetMapping
    public List<ContactDTO> getAllContacts() {
        return contactService.getAllContacts();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return new ResponseDTO("Deleted contact", null);
    }
}
