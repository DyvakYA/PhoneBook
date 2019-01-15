package com.lardi.service;

import com.lardi.dao.ContactRepository;
import com.lardi.dao.UserRepository;
import com.lardi.entities.Contact;
import com.lardi.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContactDataBaseService implements ContactService {

    private static final Logger log = Logger.getLogger(ContactDataBaseService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Transactional(readOnly = true)
    public List<Contact> findAllContactsByUserId(Integer id) {
        log.info("get all contacts");
        return (List<Contact>) contactRepository.findAllContactsByUserId(id);
    }

    @Transactional
    public void create(Contact contact, Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        contact.setUser(user);
        log.info(contact);
        contactRepository.save(contact);
    }

    @Transactional(readOnly = true)
    public List<Contact> findAll() {
        return (List<Contact>) contactRepository.findAll();
    }

    @Transactional
    public void delete(Integer id) {
        contactRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Contact> findAllContactsBySearch(String firstName, String lastName, String phoneNumberMobile, Integer userId) {
        log.info(String.format("Contact DB Service: %s, %s, %s, %s ", firstName, lastName, phoneNumberMobile, userId));
        if(userId == null){
            return contactRepository.findAllContactsBySearchParameters(firstName, lastName, phoneNumberMobile);
        }else{
            return contactRepository.findAllContactsBySearchParametersWithId(firstName, lastName, phoneNumberMobile, userId);
        }
    }
}
