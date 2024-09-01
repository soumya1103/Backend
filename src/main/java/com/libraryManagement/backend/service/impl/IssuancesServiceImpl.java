package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.mapper.IssuancesMapper;
import com.libraryManagement.backend.mapper.UsersMapper;
import com.libraryManagement.backend.repository.BooksRepository;
import com.libraryManagement.backend.repository.IssuancesRepository;
import com.libraryManagement.backend.repository.UsersRepository;
import com.libraryManagement.backend.service.iIssuancesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<IssuancesOutDto> findAll() {
        List<IssuancesOutDto> issuancesOutDto = issuancesRepository.findAll()
                .stream().map(IssuancesMapper::mapToIssuancesDto).toList();

        return issuancesOutDto;
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

//    @Override
//    public List<IssuancesOutDto> findByUserId(Users userId) {
//        return issuancesRepository.findByUserId(userId)
//                .stream()
//                .map(IssuancesMapper::mapToIssuancesDto)
//                .toList();
//    }
//
//    @Override
//    public List<IssuancesOutDto> findByBookId(Books bookId) {
//        return issuancesRepository.findByBookId(bookId)
//                .stream()
//                .map(IssuancesMapper::mapToIssuancesDto)
//                .toList();
//    }
}
