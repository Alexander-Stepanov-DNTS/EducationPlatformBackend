package ru.stepanov.EducationPlatform.services;

import ru.stepanov.EducationPlatform.DTO.DirectionDto;

import java.util.List;

public interface DirectionService {
    DirectionDto getDirectionById(Long id);
    List<DirectionDto> getAllDirections();
    DirectionDto createDirection(DirectionDto directionDto);
    DirectionDto updateDirection(Long id, DirectionDto directionDto);
    void deleteDirection(Long id);
}
