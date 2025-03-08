package io.reflectoring.AddressBookApp.Services;

import io.reflectoring.AddressBookApp.DTOs.ContactDTO;
import io.reflectoring.AddressBookApp.Entities.Contact;
import io.reflectoring.AddressBookApp.Repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactDTO addContact(ContactDTO contactDTO) {
        log.info("Adding new contact: {}", contactDTO.getName());

        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());

        Contact savedContact = contactRepository.save(contact);
        log.info("Contact saved with ID: {}", savedContact.getId());

        return new ContactDTO(savedContact.getId(), savedContact.getName(), savedContact.getEmail(), savedContact.getPhone());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);
        return contactRepository.findById(id)
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone()));
    }

    public List<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts");
        return contactRepository.findAll().stream()
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone()))
                .toList();
    }

    public void deleteContact(Long id) {
        log.warn("Deleting contact with ID: {}", id);
        contactRepository.deleteById(id);
    }
}
