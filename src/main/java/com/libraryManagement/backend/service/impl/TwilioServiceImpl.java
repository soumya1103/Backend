package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.service.iTwilioService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioServiceImpl implements iTwilioService {


    @Value("${twilio.account_sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth_token}")
    private String twilioAuthToken;

    @Value("${twilio.trial_number}")
    private String twilioFromPhoneNumber;

    @Override
    public void sendSms(String phoneNumber, String message) {

        try {
            Twilio.init(twilioAccountSid, twilioAuthToken);

            if (!phoneNumber.startsWith("+")) {
                phoneNumber = "+91" + phoneNumber;
            }
            System.out.println(phoneNumber);

            Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioFromPhoneNumber),
                    message
            ).create();

        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            e.printStackTrace();
        }

    }
}