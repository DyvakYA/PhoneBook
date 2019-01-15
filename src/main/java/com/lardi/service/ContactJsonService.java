package com.lardi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lardi.entities.Contact;
import com.lardi.entities.User;
import com.lardi.utils.PropertiesReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactJsonService implements ContactService {

    private static final Logger log = Logger.getLogger(ContactJsonService.class);

    private String jsonPath = PropertiesReader.getProperty("contact.service.json.file");

    @Override
    public List<Contact> findAllContactsByUserId(Integer id) {
        return uploadJson().stream()
                .filter(contact -> contact.getUser() != null)
                .filter(contact -> (id).equals(contact.getUser().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void create(Contact contact, Integer id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            User user = User.builder()
                    .id(id)
                    .build();
            contact.setUser(user);
            List<Contact> contacts = uploadJson();
            contacts.add(contact);
            File file = new File(jsonPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> findAll() {
        return uploadJson();
    }

    @Override
    public void delete(Integer id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Contact> contacts = uploadJson()
                    .stream()
                    .filter(contact -> contact.getId() != id)
                    .collect(Collectors.toList());

            File file = new File(jsonPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private synchronized List<Contact> uploadJson() {
        ObjectMapper mapper = new ObjectMapper();
        List<Contact> result = Collections.emptyList();
        try (InputStream inputStream = new FileInputStream("/json")) {
            ArrayNode arrayNode = (ArrayNode) mapper.readTree(inputStream);
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
            result = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(
                    List.class, Contact.class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Contact> findAllContactsBySearch(String firstName, String lastName, String phoneNumberMobile, Integer userId) {
        List<Contact> contacts = uploadJson();
        if (userId == null) {
            return contacts.stream()
                    .filter(contact -> contact.getFirstName().contains(firstName))
                    .filter(contact -> contact.getLastName().contains(lastName))
                    .filter(contact -> contact.getPhoneNumberMobile().contains(phoneNumberMobile))
                    .collect(Collectors.toList());
        } else {
            return contacts.stream()
                    .filter(contact -> contact.getUser() != null)
                    .filter(contact -> userId.equals(contact.getUser().getId()))
                    .filter(contact -> contact.getFirstName().contains(firstName))
                    .filter(contact -> contact.getLastName().contains(lastName))
                    .filter(contact -> contact.getPhoneNumberMobile().contains(phoneNumberMobile))
                    .collect(Collectors.toList());
        }
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }
}
