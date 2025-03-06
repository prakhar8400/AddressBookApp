package io.reflectoring.AddressBookApp.Services;

import io.reflectoring.AddressBookApp.DTOs.ContactDTO;
import io.reflectoring.AddressBookApp.Entities.Contact;
import io.reflectoring.AddressBookApp.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setName(contactDTO.getName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());

        //UC3
        Contact savedContact = contactRepository.save(contact);

        return new ContactDTO(savedContact.getId(), savedContact.getName(), savedContact.getEmail(), savedContact.getPhone());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        return contactRepository.findById(id)
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone()));
    }

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone()))
                .toList();
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
