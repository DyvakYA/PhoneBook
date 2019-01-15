package com.lardi.controller;


import com.lardi.entities.Contact;
import com.lardi.entities.dto.ContactDto;
import com.lardi.entities.dto.SearchDto;
import com.lardi.service.ContactService;
import com.lardi.service.ContactServiceFactory;
import com.lardi.utils.PropertiesReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contacts") // Specified controller path
public class ContactController {

    private final static Logger log = Logger.getLogger(ContactController.class);

    @Autowired
    private ContactServiceFactory contactServiceFactory;

    private ContactService contactService;


    // Here we chose the particular ContactService by parameter from custom property file
    // Used ServiceFactory
    @PostConstruct
    public void init() {
        String datasource = PropertiesReader.getProperty("contact.service.class");
        this.contactService = contactServiceFactory.getContactService(datasource);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Contact> contacts() {
        log.info("get all contact");
        // Invoke the appropriate service method and return
        List<Contact> result = contactService.findAll();
        log.info(result);
        return result;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<Contact> contacts(@PathVariable(value = "id") Integer id) {
        log.info("get all contact by id");
        // Invoke the appropriate service method and return
        List<Contact> result = contactService.findAllContactsByUserId(id);
        log.info(result);
        return result;
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        log.info("get all contact");
        // Invoke the appropriate service method and return
        contactService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/search", method = RequestMethod.POST)
    public List<Contact> getContactsBySearchParameters(@RequestBody SearchDto searchDto) {
        log.info("get all contact by search and id");
        String firstName = searchDto.getFirstName();
        String lastName = searchDto.getLastName();
        String phoneNumberMobile = searchDto.getPhoneNumberMobile();
        Integer userId = searchDto.getUserId();

        // Invoke the appropriate service method and return
        List<Contact> result = contactService.findAllContactsBySearch(firstName, lastName, phoneNumberMobile, userId);
        log.info(result);
        return result;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity contacts(@RequestBody @Valid ContactDto contactDto) {
        log.info("create new contact");
        log.info(contactDto);

        Contact contact = Contact.builder()
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .middleName(contactDto.getMiddleName())
                .phoneNumberMobile(contactDto.getPhoneNumberMobile())
                .phoneNumberHome(contactDto.getPhoneNumberHome())
                .address(contactDto.getAddress())
                .email(contactDto.getEmail())
                .build();
        // Invoke the appropriate service method and return
        log.info(contactDto);
        contactService.create(contact, contactDto.getUserId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    public void setService(ContactService service) {
        this.contactService = service;
    }
}
