package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.DirectionDto;
import ru.stepanov.EducationPlatform.models.Direction;

@Mapper
public interface DirectionMapper {
    DirectionMapper INSTANCE = Mappers.getMapper(DirectionMapper.class);

    DirectionDto toDto(Direction direction);
    Direction toEntity(DirectionDto directionDto);
}
