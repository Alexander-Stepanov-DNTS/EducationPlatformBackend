package ru.stepanov.EducationPlatform.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.DirectionDto;
import ru.stepanov.EducationPlatform.mappers.DirectionMapper;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;
import ru.stepanov.EducationPlatform.services.DirectionService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectionServiceImpl implements DirectionService {

    @Autowired
    private DirectionRepository directionRepository;

    @Override
    @Transactional(readOnly = true)
    public DirectionDto getDirectionById(Long id) {
        Optional<Direction> direction = directionRepository.findById(id);
        return direction.map(DirectionMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DirectionDto> getAllDirections() {
        return directionRepository.findAll().stream()
                .map(DirectionMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DirectionDto createDirection(DirectionDto directionDto) {
        Direction direction = DirectionMapper.INSTANCE.toEntity(directionDto);
        direction = directionRepository.save(direction);
        return DirectionMapper.INSTANCE.toDto(direction);
    }

    @Override
    @Transactional
    public DirectionDto updateDirection(Long id, DirectionDto directionDto) {
        Optional<Direction> existingDirection = directionRepository.findById(id);
        if (existingDirection.isPresent()) {
            Direction direction = existingDirection.get();
            direction.setName(directionDto.getName());
            direction.setDescription(directionDto.getDescription());
            direction = directionRepository.save(direction);
            return DirectionMapper.INSTANCE.toDto(direction);
        } else {
            return null; // или выбросить исключение
        }
    }

    @Override
    @Transactional
    public void deleteDirection(Long id) {
        directionRepository.deleteById(id);
    }
}
