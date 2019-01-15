package com.lardi.service;

public interface ContactServiceFactory{

    ContactService getContactService(String selector);
}
