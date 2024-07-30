package ru.stepanov.EducationPlatform.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import ru.stepanov.EducationPlatform.models.Direction;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DirectionRepositoryTest {

    @Autowired
    private DirectionRepository directionRepository;

    @Test
    public void testSaveAndFindById() {
        Direction direction = new Direction();
        direction.setName("IT");
        direction.setDescription("Information Technology");
        Direction savedDirection = directionRepository.save(direction);

        assertNotNull(savedDirection.getId());
        Optional<Direction> foundDirection = directionRepository.findById(savedDirection.getId());
        assertTrue(foundDirection.isPresent());
        assertEquals("IT", foundDirection.get().getName());
        assertEquals("Information Technology", foundDirection.get().getDescription());
    }
}
