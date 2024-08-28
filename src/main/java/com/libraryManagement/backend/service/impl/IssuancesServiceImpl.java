package com.libraryManagement.backend.service.impl;

import com.libraryManagement.backend.dto.IssuancesInDto;
import com.libraryManagement.backend.dto.IssuancesOutDto;
import com.libraryManagement.backend.entity.Books;
import com.libraryManagement.backend.entity.Issuances;
import com.libraryManagement.backend.entity.Users;
import com.libraryManagement.backend.mapper.IssuancesMapper;
import com.libraryManagement.backend.repository.IssuancesRepository;
import com.libraryManagement.backend.service.iIssuancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssuancesServiceImpl implements iIssuancesService {

    private IssuancesRepository issuancesRepository;

    @Autowired
    public IssuancesServiceImpl(IssuancesRepository issuancesRepository) {
        this.issuancesRepository = issuancesRepository;
    }

    @Override
    public List<IssuancesOutDto> findAll() {
        List<IssuancesOutDto> issuancesOutDto = issuancesRepository.findAll()
                .stream().map(IssuancesMapper::mapToIssuancesDto).toList();

        return issuancesOutDto;
    }

    @Override
    public Optional<IssuancesOutDto> findById(int issuanceId) {
        Optional<IssuancesOutDto> issuancesOutDto = Optional.ofNullable(issuancesRepository.findById(issuanceId)
                .map(IssuancesMapper::mapToIssuancesDto)
                .orElseThrow(() -> new RuntimeException("Issuance not found with id: " + issuanceId)));

        return issuancesOutDto;
    }

    @Override
    public List<IssuancesOutDto> findByUserId(Users userId) {
        return issuancesRepository.findByUserId(userId)
                .stream()
                .map(IssuancesMapper::mapToIssuancesDto)
                .toList();
    }

    @Override
    public List<IssuancesOutDto> findByBookId(Books bookId) {
        return issuancesRepository.findByBookId(bookId)
                .stream()
                .map(IssuancesMapper::mapToIssuancesDto)
                .toList();
    }

    @Override
    public Issuances save(Issuances issuances) {
        return issuancesRepository.save(issuances);
    }

    @Override
    public IssuancesOutDto updateIssuance(IssuancesInDto issuancesInDto) {
        Optional<Issuances> issuances = issuancesRepository.findById(issuancesInDto.getIssuanceId());

        if(!issuances.isPresent()) {
            throw new RuntimeException("Issuance not found");
        }

        Issuances issuanceToUpdate = issuances.get();

        if (issuancesInDto.getUserId() != null) {
            issuanceToUpdate.setUserId(issuancesInDto.getUserId());
        }

        if (issuancesInDto.getBookId() != null) {
            issuanceToUpdate.setBookId(issuancesInDto.getBookId());
        }

        if (issuancesInDto.getIssueDate() != null) {
            issuanceToUpdate.setIssueDate(issuancesInDto.getIssueDate());
        }

        if (issuancesInDto.getReturnDate() != null) {
            issuanceToUpdate.setReturnDate(issuancesInDto.getReturnDate());
        }

        if (issuancesInDto.getStatus() != null && !issuancesInDto.getStatus().isEmpty()) {
            issuanceToUpdate.setStatus(issuancesInDto.getStatus());
        }

        if (issuancesInDto.getIssuanceType() != null && !issuancesInDto.getIssuanceType().isEmpty()) {
            issuanceToUpdate.setIssuanceType(issuancesInDto.getIssuanceType());
        }

        Issuances updatedIssuance = issuancesRepository.save(issuanceToUpdate);


        IssuancesOutDto issuancesOutDto = new IssuancesOutDto();

        issuancesOutDto.setIssueDate(updatedIssuance.getIssueDate());
        issuancesOutDto.setReturnDate(updatedIssuance.getReturnDate());
        issuancesOutDto.setStatus(updatedIssuance.getStatus());
        issuancesOutDto.setIssuanceType(updatedIssuance.getIssuanceType());

        return issuancesOutDto;
    }

    @Override
    public void deleteById(int issuanceId) {
        issuancesRepository.deleteById(issuanceId);
    }
}
