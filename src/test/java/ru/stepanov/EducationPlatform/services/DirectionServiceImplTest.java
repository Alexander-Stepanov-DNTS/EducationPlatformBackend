package ru.stepanov.EducationPlatform.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanov.EducationPlatform.DTO.DirectionDto;
import ru.stepanov.EducationPlatform.models.Direction;
import ru.stepanov.EducationPlatform.repositories.DirectionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DirectionServiceImplTest {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private DirectionRepository directionRepository;

    @BeforeEach
    public void setUp() {
        directionRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testGetDirectionById() {
        Direction direction = new Direction();
        direction.setName("Test Direction");
        direction.setDescription("Test Description");
        direction = directionRepository.save(direction);

        DirectionDto foundDirection = directionService.getDirectionById(direction.getId());
        assertNotNull(foundDirection);
        assertEquals(direction.getId(), foundDirection.getId());
        assertEquals(direction.getName(), foundDirection.getName());
        assertEquals(direction.getDescription(), foundDirection.getDescription());
    }

    @Test
    @Transactional
    public void testGetAllDirections() {
        Direction direction1 = new Direction();
        direction1.setName("Test Direction 1");
        direction1.setDescription("Test Description 1");
        directionRepository.save(direction1);

        Direction direction2 = new Direction();
        direction2.setName("Test Direction 2");
        direction2.setDescription("Test Description 2");
        directionRepository.save(direction2);

        List<DirectionDto> directions = directionService.getAllDirections();
        for(int i = 0; i != directions.size(); i++) {
            System.out.println(directions.get(i));
        }
        assertEquals(2, directions.size());
    }

    @Test
    @Transactional
    public void testCreateDirection() {
        DirectionDto directionDto = new DirectionDto();
        directionDto.setName("New Direction");
        directionDto.setDescription("New Description");

        DirectionDto createdDirection = directionService.createDirection(directionDto);
        assertNotNull(createdDirection.getId());
        assertEquals(directionDto.getName(), createdDirection.getName());
        assertEquals(directionDto.getDescription(), createdDirection.getDescription());
    }

    @Test
    @Transactional
    public void testUpdateDirection() {
        Direction direction = new Direction();
        direction.setName("Old Direction");
        direction.setDescription("Old Description");
        direction = directionRepository.save(direction);

        DirectionDto directionDto = new DirectionDto();
        directionDto.setName("Updated Direction");
        directionDto.setDescription("Updated Description");

        DirectionDto updatedDirection = directionService.updateDirection(direction.getId(), directionDto);
        assertNotNull(updatedDirection);
        assertEquals(directionDto.getName(), updatedDirection.getName());
        assertEquals(directionDto.getDescription(), updatedDirection.getDescription());
    }

    @Test
    @Transactional
    public void testDeleteDirection() {
        Direction direction = new Direction();
        direction.setName("To Be Deleted");
        direction.setDescription("To Be Deleted");
        direction = directionRepository.save(direction);

        directionService.deleteDirection(direction.getId());

        Optional<Direction> deletedDirection = directionRepository.findById(direction.getId());
        assertFalse(deletedDirection.isPresent());
    }
}