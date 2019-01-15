package com.lardi.service;

import com.lardi.dao.ContactRepository;
import com.lardi.dao.UserRepository;
import com.lardi.entities.Contact;
import com.lardi.entities.User;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ContactDataBaseServiceTest {

    private static final Logger log = Logger.getLogger(ContactDataBaseServiceTest.class);

    @TestConfiguration
    static class ContactServiceTestContextConfiguration {

        @Bean
        public ContactDataBaseService contactService() {
            return new ContactDataBaseService();
        }
    }


    @Autowired
    private ContactDataBaseService contactService;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private UserRepository userRepository;

    private List<Contact> contacts;
    private Contact contact;

    @Before
    public void setUp() {
        User user = User.builder()
                .id(1)
                .username("admin")
                .password("admin")
                .fullName("admin")
                .build();
        contact = Contact.builder()
                .id(1)
                .firstName("admin")
                .lastName("admin")
                .middleName("admin")
                .phoneNumberHome("093-1231323")
                .phoneNumberMobile("093-1231323")
                .address("administration")
                .email("admin@admin.com")
                .user(user)
                .build();
        contacts = new ArrayList<>();
        contacts.add(contact);

        when(contactRepository.findAllContactsByUserId(user.getId())).thenReturn(contacts);
        when(contactRepository.findAllContactsByUserId(user.getId())).thenReturn(contacts);
        when(contactRepository.findAll()).thenReturn(contacts);
        when(contactRepository.findAllContactsBySearchParametersWithId("admin", "", "", 1)).thenReturn(contacts);
        when(contactRepository.findAllContactsBySearchParametersWithId("", "admin", "", 1)).thenReturn(contacts);
        when(contactRepository.findAllContactsBySearchParametersWithId("", "", "123", 1)).thenReturn(contacts);
        when(contactRepository.findAllContactsBySearchParametersWithId("", "", "", 1)).thenReturn(contacts);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

    }

    @Test
    public void findAllContactsByUserId_thenReturnContactListForUser() {
        List<Contact> result = contactService.findAllContactsByUserId(1);
        verify(contactRepository).findAllContactsByUserId(1);
        assertEquals(contacts, result);
    }

    @Test
    public void createNewContactTest() {
        contactService.create(contact, 1);
        verify(userRepository).findById(1);
        verify(contactRepository).save(contact);
    }

    @Test
    public void findAllContactsTest() {
        List<Contact> result = contactService.findAll();
        verify(contactRepository).findAll();
        assertEquals(contacts, result);
    }

    @Test
    public void deleteContactTest() {
        contactService.delete(1);
        verify(contactRepository).deleteById(1);
    }

    @Test
    public void findAllContactsBySearchParameterTest() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "", 1);
        log.info(result);
        assertTrue(result.size() > 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParametersWithId("", "", "", 1);
    }

    @Test
    public void findAllContactsBySearchParameterFirstNameTest() {
        List<Contact> result = contactService.findAllContactsBySearch("admin", "", "", 1);
        log.info(result);
        assertTrue(result.size() > 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParametersWithId("admin", "", "", 1);
    }

    @Test
    public void findAllContactsBySearchParameterFirstNameTest_whenParameterIsWrong() {
        List<Contact> result = contactService.findAllContactsBySearch("qweqweqwe", "", "", 1);
        log.info(result);
        assertTrue(result.size() == 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParametersWithId("qweqweqwe", "", "", 1);
    }

    @Test
    public void findAllContactsBySearchParameterLastNameTest_whenParameterIsCorrect() {
        List<Contact> result = contactService.findAllContactsBySearch("", "admin", "", 1);
        log.info(result);
        assertTrue(result.size() > 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParametersWithId("", "admin", "", 1);
    }

    @Test
    public void findAllContactsBySearchParameterLastNameTest_whenParameterIsWrong() {
        List<Contact> result = contactService.findAllContactsBySearch("", "asdasdasdasd", "", 1);
        log.info(result);
        assertTrue(result.size() == 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParametersWithId("", "asdasdasdasd", "", 1);
    }

    @Test
    public void findAllContactsBySearchParameterPhoneNumberMobileTest_whenParameterIsCorrect() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "123", 1);
        log.info(result);
        assertTrue(result.size() > 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParametersWithId("", "", "123", 1);
    }

    @Test
    public void findAllContactsBySearchParameterPhoneNumberMobileTest_whenParameterIsWrong() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "333", 1);
        log.info(result);
        assertTrue(result.size() == 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParametersWithId("", "", "333", 1);
    }

    @Test
    public void findAllContactsBySearchParameterPhoneNumberMobileTest_whenUserIdIsNull() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "333", null);
        log.info(result);
        assertTrue(result.size() == 0);
        verify(contactRepository, times(1)).findAllContactsBySearchParameters("", "", "333");
    }

}
