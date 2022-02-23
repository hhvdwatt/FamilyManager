package dk.vdw.familymanager.services;

import dk.vdw.familymanager.model.Contact;
import dk.vdw.familymanager.repositories.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;

@Service
@Slf4j
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Collection<Contact> findAll(){
        var result =  contactRepository.findAll();
        if(result.isEmpty())
            throw new RuntimeException(MessageFormat.format("Empty result set! No [{0}] found!",
                    Contact.class));
        return result;
    }

    public void saveAll(Collection<Contact> contacts) {
        if(contacts.isEmpty())
            throw new RuntimeException(MessageFormat.format("Collection of [{0}] is empty!",
                    Contact.class));
        if(log.isDebugEnabled())
            log.debug(MessageFormat.format("Persisting [{0}] {1} to the database.",
                    contacts.size(),Contact.class));
        contactRepository.saveAll(contacts);
    }

    public Collection<Contact> findContactByFirstnameOrLastname(String search){
        return contactRepository.search(search);
    }
}
