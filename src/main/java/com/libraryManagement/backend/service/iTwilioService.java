package com.libraryManagement.backend.service;

public interface iTwilioService {
    public void sendSms(String phoneNumber, String message);
}
