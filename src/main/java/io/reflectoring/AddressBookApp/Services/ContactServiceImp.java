package io.reflectoring.AddressBookApp.Services;

import io.reflectoring.AddressBookApp.DTOs.ContactDTO;
import io.reflectoring.AddressBookApp.Entities.Contact;
import io.reflectoring.AddressBookApp.Interfaces.IContactService;
import io.reflectoring.AddressBookApp.Repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactServiceImp implements IContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    @CacheEvict(value = "contacts", allEntries = true)
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

    @Override
    @Cacheable(value = "contacts", key = "#id")
    public Optional<ContactDTO> getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);
        return contactRepository.findById(id)
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone()));
    }

    @Override
    @Cacheable(value = "contacts")
    public List<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts");
        return contactRepository.findAll().stream()
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone()))
                .toList();
    }

    @Override
    @CacheEvict(value = "contacts", key = "#id")
    public void deleteContact(Long id) {
        log.warn("Deleting contact with ID: {}", id);
        contactRepository.deleteById(id);
    }
}
