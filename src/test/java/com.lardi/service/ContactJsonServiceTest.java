package com.lardi.service;

import com.lardi.entities.Contact;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ContactJsonServiceTest {

    private static Logger log = Logger.getLogger(ContactJsonService.class);

    private ContactService contactService;

    private Contact contact;

    @Before
    public void setUp() {

        contactService = new ContactJsonService();
        ((ContactJsonService) contactService).setJsonPath("/json");

        contact = Contact.builder()
                .id(22)
                .firstName("Nickolas")
                .lastName("White")
                .middleName("Frank")
                .phoneNumberMobile("123123123")
                .phoneNumberHome("321321321")
                .address("Springfield")
                .email("homer@simpson.com")
                .build();
    }

    @Test
    public void findAllContactsByUserIdTest_whenIdPresentInJsonFile() {
        List<Contact> contacts = contactService.findAll();
        contacts = contacts.stream()
                .filter(contact -> contact.getUser() != null)
                .collect(Collectors.toList());
        log.info(contacts);
        Integer userId = contacts.get(0).getUser().getId();
        log.info(userId);
        List<Contact> found = contactService.findAllContactsByUserId(userId);
        log.info(found);
        assertTrue(found.size() > 0);
    }

    @Test
    public void findAllContactsByUserIdTest_whenIdIsNotPresentInJsonFile() {
        List<Contact> contacts = contactService.findAllContactsByUserId(0);
        log.info(contacts);
        assertTrue(contacts.size() == 0);
    }

    @Test
    public void createNewContactTest() {
        List<Contact> contacts1 = contactService.findAll();
        Integer count1 = contacts1.size();
        log.info(count1 + " - " + contacts1);
        contactService.create(contact, 1);

        List<Contact> contacts2 = contactService.findAll();
        Integer count2 = contacts2.size();
        log.info(count2 + " - " + contacts2);
        assertTrue(count1 + 1 == count2);
    }

    @Test
    public void findAllContactsTest() {
        List<Contact> contacts = contactService.findAll();
        log.info(contacts);
        assertTrue(contacts.size() > 0);
    }

    @Test
    public void deleteContactsByIdTest() {
        List<Contact> contacts1 = contactService.findAll();
        Integer count1 = contacts1.size();
        log.info(count1 + " - " + contacts1);
        contactService.delete(contacts1.get(0).getId());

        List<Contact> contacts2 = contactService.findAll();
        Integer count2 = contacts2.size();
        log.info(count2 + " - " + contacts2);
        assertTrue(count1 > count2);
    }

    @Test
    public void findAllContactsBySearchParameterTest() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "", 1);
        log.info(result);
        assertTrue(result.size() > 0);
    }

    @Test
    public void findAllContactsBySearchParameterFirstNameTest() {
        List<Contact> result = contactService.findAllContactsBySearch("Nick", "", "", 1);
        log.info(result);
        assertTrue(result.size() > 0);
    }

    @Test
    public void findAllContactsBySearchParameterFirstNameTest_whenParameterIsWrong() {
        List<Contact> result = contactService.findAllContactsBySearch("qweqweqwe", "", "", 1);
        log.info(result);
        assertTrue(result.size() == 0);
    }

    @Test
    public void findAllContactsBySearchParameterLastNameTest_whenParameterIsCorrect() {
        List<Contact> result = contactService.findAllContactsBySearch("", "Whi", "", 1);
        log.info(result);
        assertTrue(result.size() > 0);
    }

    @Test
    public void findAllContactsBySearchParameterLastNameTest_whenParameterIsWrong() {
        List<Contact> result = contactService.findAllContactsBySearch("", "asdasdasdasd", "", 1);
        log.info(result);
        assertTrue(result.size() == 0);
    }

    @Test
    public void findAllContactsBySearchParameterPhoneNumberMobileTest_whenParameterIsCorrect() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "123", 1);
        log.info(result);
        assertTrue(result.size() > 0);
    }

    @Test
    public void findAllContactsBySearchParameterPhoneNumberMobileTest_whenParameterIsWrong() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "333", 1);
        log.info(result);
        assertTrue(result.size() == 0);
    }

    @Test
    public void findAllContactsBySearchParameterPhoneNumberMobileTest_whenUserIdIsNull() {
        List<Contact> result = contactService.findAllContactsBySearch("", "", "123", null);
        log.info(result);
        assertTrue(result.size() >0);
    }

}
