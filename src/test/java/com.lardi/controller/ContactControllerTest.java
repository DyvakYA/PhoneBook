package com.lardi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lardi.entities.Contact;
import com.lardi.entities.dto.SearchDto;
import com.lardi.service.ContactDataBaseService;
import com.lardi.service.ContactService;
import com.lardi.service.ContactServiceFactory;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    private final static Logger log = Logger.getLogger(ContactControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContactServiceFactory contactServiceFactory;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ContactController contactController;

    private Contact contact;

    @Before
    public void setUp() {

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

        when(contactServiceFactory.getContactService("contactDataBaseService"))
                .thenReturn(new ContactDataBaseService());

    }

    @Test
    public void givenContacts_whenGetContacts_thenReturnJsonArray()
            throws Exception {

        List<Contact> contacts = Arrays.asList(contact);
        contactController.setService(contactService);

        given(contactService.findAll()).willReturn(contacts);

        mvc.perform(get("/api/contacts")
                .header("Authorization", "Access allowed")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(contact.getId())))
                .andExpect(jsonPath("$[0].firstName", is(contact.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(contact.getLastName())))
                .andExpect(jsonPath("$[0].middleName", is(contact.getMiddleName())))
                .andExpect(jsonPath("$[0].email", is(contact.getEmail())))
                .andExpect(jsonPath("$[0].phoneNumberHome", is(contact.getPhoneNumberHome())))
                .andExpect(jsonPath("$[0].phoneNumberMobile", is(contact.getPhoneNumberMobile())));
        verify(contactService).findAll();
    }

    @Test
    public void givenContacts_whenGetContactsByUserId_thenReturnJsonArray()
            throws Exception {

        List<Contact> contacts = Arrays.asList(contact);
        contactController.setService(contactService);

        given(contactService.findAllContactsByUserId(1)).willReturn(contacts);

        mvc.perform(get("/api/contacts/1")
                .header("Authorization", "Access allowed")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(contact.getId())))
                .andExpect(jsonPath("$[0].firstName", is(contact.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(contact.getLastName())))
                .andExpect(jsonPath("$[0].middleName", is(contact.getMiddleName())))
                .andExpect(jsonPath("$[0].email", is(contact.getEmail())))
                .andExpect(jsonPath("$[0].phoneNumberHome", is(contact.getPhoneNumberHome())))
                .andExpect(jsonPath("$[0].phoneNumberMobile", is(contact.getPhoneNumberMobile())));
        verify(contactService).findAllContactsByUserId(1);
    }

    @Test
    public void givenContacts_whenDeleteContactById_thenReturnStatusOk()
            throws Exception {

        contactController.setService(contactService);

        mvc.perform(get("/api/contacts/delete/1")
                .header("Authorization", "Access allowed")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(contactService).delete(1);
    }

    @Test
    public void givenContacts_whenGetContactsBySearchParameters_thenReturnContacts()
            throws Exception {

        when(contactService.findAllContactsBySearch(null, null, null, 1)).thenReturn(Collections.singletonList(contact));

        contactController.setService(contactService);

        SearchDto searchDto = new SearchDto();
        searchDto.setUserId(1);

        mvc.perform(post("/api/contacts/search")
                .header("Authorization", "Access allowed")
                .content(asJsonString(searchDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(contact.getId())))
                .andExpect(jsonPath("$[0].firstName", is(contact.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(contact.getLastName())))
                .andExpect(jsonPath("$[0].middleName", is(contact.getMiddleName())))
                .andExpect(jsonPath("$[0].email", is(contact.getEmail())))
                .andExpect(jsonPath("$[0].phoneNumberHome", is(contact.getPhoneNumberHome())))
                .andExpect(jsonPath("$[0].phoneNumberMobile", is(contact.getPhoneNumberMobile())));

        verify(contactService).findAllContactsBySearch(null, null, null, 1);
    }


    @Test
    public void whenPostContactForSaving()
            throws Exception {

        List<Contact> contacts = Arrays.asList(contact);
        contactController.setService(contactService);

        given(contactService.findAllContactsByUserId(1)).willReturn(contacts);

        mvc.perform(post("/api/contacts/add")
                .header("Authorization", "Access allowed")
                .content(asJsonString(contact))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(contactService).create(any(), any());
    }

    private byte[] asJsonString(Object object) {
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
