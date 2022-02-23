package dk.vdw.familymanager.repositories;

import dk.vdw.familymanager.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact, Long> {
    List<Contact> findAll();
    List<Contact> findContactsByFirstName(String firstName);
    List<Contact> findContactsByLastName(String lastName);
    Optional<Contact> findContactByEmail(String email);
    Optional<Contact> findContactByLastName(String lastName);

    @Query("select c from Contact c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Contact> search(@Param("searchTerm") String searchTerm);
}
