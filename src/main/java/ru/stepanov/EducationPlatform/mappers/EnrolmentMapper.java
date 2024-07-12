package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.EnrolmentDto;
import ru.stepanov.EducationPlatform.models.Enrolment;

@Mapper
public interface EnrolmentMapper {
    EnrolmentMapper INSTANCE = Mappers.getMapper(EnrolmentMapper.class);

    EnrolmentDto toDto(Enrolment enrolment);
    Enrolment toEntity(EnrolmentDto enrolmentDto);
}
