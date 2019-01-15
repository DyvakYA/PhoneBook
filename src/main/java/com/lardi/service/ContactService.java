package com.lardi.service;

import com.lardi.entities.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAllContactsByUserId(Integer id);

    void create(Contact contact, Integer id);

    List<Contact> findAll();

    void delete(Integer id);

    List<Contact> findAllContactsBySearch(String firstName, String lastName, String phoneNumberMobile, Integer userId);
}
