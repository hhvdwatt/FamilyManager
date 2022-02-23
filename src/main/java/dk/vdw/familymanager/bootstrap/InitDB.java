package dk.vdw.familymanager.bootstrap;

import dk.vdw.familymanager.model.Contact;
import dk.vdw.familymanager.repositories.ContactRepository;
import dk.vdw.familymanager.services.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class InitDB implements ApplicationListener<ContextRefreshedEvent> {

    private final ContactService contactService;

    public InitDB(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        contactService.saveAll(loadContacts());
        log.debug(MessageFormat.format("Initialized DB with [{0}] Contact objects.",
                (long) loadContacts().size()));
    }

    private Collection<Contact> loadContacts(){
        Contact hendrik = new Contact();
        Contact henru = new Contact();
        Contact juanita = new Contact();
        List<Contact> contacts = new ArrayList<>();

        hendrik.setFirstName("Hendrik");
        hendrik.setLastName("van der Watt");
        hendrik.setEmail("hh@vdw.dk");
        hendrik.setMobile("+45 5124 0846");
        contacts.add(hendrik);

        juanita.setFirstName("Juanita");
        juanita.setLastName("van der Watt");
        juanita.setEmail("jb@vdw.dk");
        juanita.setMobile("+45 5124 7475");
        contacts.add(juanita);

        henru.setFirstName("Henru");
        henru.setLastName("van der Watt");
        henru.setEmail("hjvdw.dk@icloud.com");
        henru.setMobile("+45 5150 0882");
        contacts.add(henru);

        return contacts;

    }
}
