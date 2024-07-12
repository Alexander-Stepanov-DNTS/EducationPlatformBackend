package ru.stepanov.EducationPlatform.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.stepanov.EducationPlatform.DTO.CourseDto;
import ru.stepanov.EducationPlatform.models.Course;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    CourseDto toDto(Course course);
    Course toEntity(CourseDto courseDto);
}
