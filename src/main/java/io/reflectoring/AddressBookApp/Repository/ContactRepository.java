package io.reflectoring.AddressBookApp.Repository;

import io.reflectoring.AddressBookApp.Entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
