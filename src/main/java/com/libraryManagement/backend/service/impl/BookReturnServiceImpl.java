package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.repository.IssuancesRepository;
import com.libraryManagement.backend.service.iBookReturnService;
import com.libraryManagement.backend.service.iTwilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookReturnServiceImpl implements iBookReturnService {

    private final IssuancesRepository issuancesRepository;
    private final iTwilioService twilioService;

    @Override
    @Scheduled(cron = "0 56 12 * * *", zone = "Asia/Kolkata")
//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Kolkata")
    public void sendBookReturnReminder() {
        LocalDateTime startOfTomorrow = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfTomorrow = startOfTomorrow.plusDays(1).minusSeconds(1);
        List<Issuances> dueTomorrow = issuancesRepository.findAllByReturnDateBetweenAndStatus(startOfTomorrow, endOfTomorrow, "Issued");

        System.out.println("Scheduler called" + dueTomorrow);

        for (Issuances issuance : dueTomorrow) {
            String message = String.format("Reminder:" + "Return the book, Book Title: '%s'" + "Book Author: '%s'"+
                            "by Book Return Date: (%s).",
                    issuance.getBooks().getBookTitle(), issuance.getBooks().getBookAuthor(),
                    issuance.getReturnDate().toLocalDate());
//            twilioService.sendSms(issuance.getUsers().getUserCredential(), message);
        }

//        System.out.println("DUO TOMORROW" + dueTomorrow);

    }

    }
