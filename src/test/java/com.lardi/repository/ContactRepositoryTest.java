package com.lardi.repository;

import com.lardi.config.H2JpaConfig;
import com.lardi.dao.ContactRepository;
import com.lardi.dao.UserRepository;
import com.lardi.entities.Contact;
import com.lardi.entities.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@OverrideAutoConfiguration(enabled = true)
@ContextConfiguration(classes = {H2JpaConfig.class})
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContactRepositoryTest {

    private static final Logger log = Logger.getLogger(ContactRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    User newUser;

    private Contact contact1;
    private Contact contact2;

    @Before
    public void setUp() {
        User user = User.builder()
                .username("admin")
                .password("admin")
                .fullName("admin")
                .build();
        userRepository.save(user);

        Optional<User> optional = userRepository.findUserByUsername("admin");

        if(optional.isPresent())
            newUser = optional.get();
        log.info(newUser);
        contact1 = Contact.builder()
                .firstName("admin")
                .lastName("admin")
                .middleName("admin")
                .phoneNumberHome("093-1231323")
                .phoneNumberMobile("093-1231323")
                .address("administration")
                .email("admin@admin.com")
                .user(newUser)
                .build();
        contactRepository.save(contact1);
        log.info(contact1);
        log.info(((List<Contact>) contactRepository.findAll()).size());


        contact2 = Contact.builder()
                .firstName("Vasya")
                .lastName("Petrov")
                .middleName("Fedya")
                .phoneNumberHome("093-1231323")
                .phoneNumberMobile("093-9999999")
                .address("administration")
                .email("admin@admin.com")
                .user(newUser)
                .build();
        contactRepository.save(contact2);
        log.info(contact2);
        log.info(((List<Contact>) contactRepository.findAll()).size());
    }

    @Test
    public void findAllContactsByUserId_thenReturnContacts() {
        List<Contact> contacts = contactRepository.findAllContactsByUserId(newUser.getId());
        assertEquals(2, contacts.size());
    }

    @Test
    public void findAllContactsBySearchParameterFirstName_thenReturnContact() {

        List<Contact> contacts = contactRepository.findAllContactsBySearchParametersWithId("adm", "", "", newUser.getId());
        log.info(contacts);
        assertEquals(1, contacts.size());
        assertEquals(contacts.get(0), contact1);
    }

    @Test
    public void findAllContactsBySearchParameterFirstName_whenNullParameters_thenReturnContact() {

        List<Contact> contacts = contactRepository.findAllContactsBySearchParametersWithId("adm", null, null, null);
        log.info(contacts);
        assertEquals(0, contacts.size());

    }

    @Test
    public void findAllContactsBySearchParameterLastName_thenReturnContacts() {
        List<Contact> contacts = contactRepository.findAllContactsBySearchParametersWithId("", "adm", "", newUser.getId());
        log.info(contacts);
        assertEquals(1, contacts.size());
        assertEquals(contacts.get(0), contact1);
    }

    @Test
    public void findAllContactsBySearchParameterPhoneNumberMobile_thenReturnContacts() {
        List<Contact> contacts = contactRepository.findAllContactsBySearchParametersWithId("", "", "999", newUser.getId());
        log.info(contacts);
        assertEquals(1, contacts.size());
        assertEquals(contacts.get(0), contact2);
    }

    @Test
    public void findAllContactsBySearchParametersNull_thenReturnContacts() {
        List<Contact> contacts = contactRepository.findAllContactsBySearchParametersWithId(null, null, null, newUser.getId());
        log.info(contacts);
        assertEquals(2, contacts.size());
        assertEquals(contacts.get(0), contact1);
        assertEquals(contacts.get(1), contact2);
    }

    @Test
    public void findAllContactsBySearchParametersPhoneNumber_thenReturnContacts() {
        List<Contact> contacts = contactRepository.findAllContactsBySearchParameters("", "", "999");
        log.info(contacts);
        assertEquals(1, contacts.size());
        assertEquals(contacts.get(0), contact2);
    }

}
