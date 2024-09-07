package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.mapper.IssuancesMapper;
import com.libraryManagement.backend.repository.BooksRepository;
import com.libraryManagement.backend.repository.IssuancesRepository;
import com.libraryManagement.backend.repository.UsersRepository;
import com.libraryManagement.backend.service.iIssuancesService;
import com.libraryManagement.backend.service.iTwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IssuancesServiceImpl implements iIssuancesService {

    @Autowired
    private IssuancesRepository issuancesRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private UsersRepository usersRepository;

    private final iTwilioService twilioService;

    public IssuancesServiceImpl(iTwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @Override
    public Page<IssuancesOutDto> getIssuances(Pageable pageable) {
        Page<Issuances> issuancesPage;
        issuancesPage = issuancesRepository.findAll(pageable);

        return issuancesPage.map(IssuancesMapper::mapToIssuancesDto);
    }

    @Override
    public IssuancesOutDto findById(int issuanceId) {
        Issuances issuances = issuancesRepository.findById(issuanceId).
                orElseThrow(() -> new RuntimeException("Issuance not found with id: " + issuanceId));

        IssuancesOutDto issuancesOutDto = IssuancesMapper.mapToIssuancesDto(issuances);
        return issuancesOutDto;
    }

    @Override
    @Transactional
    public IssuancesOutDto saveIssuances(IssuancesInDto issuancesInDto) {
        Books books = booksRepository.findById(issuancesInDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Users users = usersRepository.findById(issuancesInDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Issuances issuances = IssuancesMapper.mapToIssuancesEntity(issuancesInDto, books, users);
        issuances.setIssueDate(LocalDateTime.now());
        issuances.setStatus("Issued");
        issuances = issuancesRepository.save(issuances);


        String message = String.format("Issued book: '%s'" + " Author name: '%s'",
                issuances.getBooks().getBookTitle(),
                issuances.getBooks().getBookAuthor());

//        if (issuances.getIssuanceType() == "Remote") {
//        twilioService.sendSms(issuances.getUsers().getUserCredential(), message);
//        }

        return IssuancesMapper.mapToIssuancesDto(issuances);
    }

    @Override
    @Transactional
    public IssuancesOutDto updateIssuance(int issuanceId, IssuancesInDto issuancesInDto) {
        Optional<Issuances> issuances = issuancesRepository.findById(issuanceId);
        if (issuances.isEmpty()) {
            throw new RuntimeException("Issuance not found with id: " + issuanceId);
        }

        Issuances existingIssuance = issuances.get();

        if (issuancesInDto.getBookId() != 0) {
            Books books = booksRepository.findById(issuancesInDto.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + issuancesInDto.getBookId()));
            existingIssuance.setBooks(books);
        }

        if (issuancesInDto.getUserId() != 0) {
            Users users = usersRepository.findById(issuancesInDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + issuancesInDto.getUserId()));
            existingIssuance.setUsers(users);
        }

        if (issuancesInDto.getReturnDate() != null) {
            existingIssuance.setReturnDate(issuancesInDto.getReturnDate());
        }

        if (issuancesInDto.getStatus() != null) {
            existingIssuance.setStatus(issuancesInDto.getStatus());
        }

        if (issuancesInDto.getIssuanceType() != null) {
            existingIssuance.setIssuanceType(issuancesInDto.getIssuanceType());
        }

        Issuances updatedIssuance = issuancesRepository.save(existingIssuance);

        return IssuancesMapper.mapToIssuancesDto(updatedIssuance);

    }

    @Override
    public void deleteById(int issuanceId) {
        issuancesRepository.deleteById(issuanceId);
    }

    @Override
    public List<IssuancesOutDto> getIssuanceByIssuanceType(String issuanceType) {
        List<Issuances> issuances = issuancesRepository.findByIssuanceType(issuanceType);
        List<IssuancesOutDto> issuancesOutDto = new ArrayList<>();

        issuances.forEach(issuance -> issuancesOutDto.add(IssuancesMapper.mapToIssuancesDto(issuance)));

        return issuancesOutDto;
    }

    @Override
    public List<IssuancesOutDto> getIssuanceByStatus(String status) {
        List<Issuances> issuances = issuancesRepository.findByStatus(status);
        List<IssuancesOutDto> issuancesOutDto = new ArrayList<>();

        issuances.forEach(issuance -> issuancesOutDto.add(IssuancesMapper.mapToIssuancesDto(issuance)));

        return issuancesOutDto;
    }

    @Override
    public long getIssuanceCountByType() {
        return issuancesRepository.count();
    }

    @Override
    public List<IssuancesOutDto> getIssuanceByUserCredential(String userCredential) {
        List<Issuances> issuances = issuancesRepository.findByUserCredential(userCredential);
        List<IssuancesOutDto> issuancesOutDto = new ArrayList<>();

        issuances.forEach(issuance -> issuancesOutDto.add(IssuancesMapper.mapToIssuancesDto(issuance)));

        return issuancesOutDto;
    }

    @Override
    public List<IssuancesOutDto> findByBookId(int bookId) {
        List<Issuances> issuances = issuancesRepository.findByBookId(bookId);
        List<IssuancesOutDto> issuancesOutDto = new ArrayList<>();

        issuances.forEach(issuance -> issuancesOutDto.add(IssuancesMapper.mapToIssuancesDto(issuance)));

        return issuancesOutDto;
    }

    @Override
    public List<IssuancesOutDto> findByUserId(int userId) {
        List<Issuances> issuances = issuancesRepository.findByUserId(userId);
        List<IssuancesOutDto> issuancesOutDto = new ArrayList<>();

        issuances.forEach(issuance -> issuancesOutDto.add(IssuancesMapper.mapToIssuancesDto(issuance)));

        return issuancesOutDto;
    }

    @Override
    public List<IssuancesOutDto> searchCredential(String keywords) {
        List<Issuances> issuances = issuancesRepository.findByUserCredentialOrBookTitleContaining("%" + keywords + "%");
        return issuances.stream().map(IssuancesMapper::mapToIssuancesDto).toList();
    }
}
