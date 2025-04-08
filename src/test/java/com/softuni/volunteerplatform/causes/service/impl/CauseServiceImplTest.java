package com.softuni.volunteerplatform.causes.service.impl;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseShortInfoDTO;
import com.softuni.volunteerplatform.causes.model.entity.Cause;
import com.softuni.volunteerplatform.causes.model.enums.Level;
import com.softuni.volunteerplatform.causes.repository.CauseRepository;
import com.softuni.volunteerplatform.causes.service.exception.CauseNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CauseServiceImplTest {

    @Mock
    private CauseRepository causeRepository;

    @InjectMocks
    private CauseServiceImpl causeService;

    private AddCauseDTO addCauseDTO;
    private Cause cause;

    @BeforeEach
    public void setUp() {
        // Подготовка на тестовите данни
        addCauseDTO = new AddCauseDTO(
                "Test Title",
                "Test Description",
                Level.JUNIOR,
                "http://example.com/image.png",
                "Test Author",
                LocalDateTime.now()
        );

        // Настройваме entity-то, както го създава мапингът в service‑а
        cause = new Cause()
                .setTitle(addCauseDTO.title())
                .setDescription(addCauseDTO.description())
                .setLevel(addCauseDTO.level())
                .setImageUrl(addCauseDTO.imageUrl())
                .setCreated(addCauseDTO.created())
                .setAuthorName(addCauseDTO.authorName());
        // Задаваме ID, както би го имал след като е записано в базата данни
        cause.setId(1L);
    }

    @Test
    public void testCreateCause() {
        // Подканяме repository‑то да върне вече "създадената" причина
        when(causeRepository.save(any(Cause.class))).thenReturn(cause);

        CauseShortInfoDTO result = causeService.createCause(addCauseDTO);

        // Проверяваме, че връщания DTO съдържа съответната информация
        assertNotNull(result);
        assertEquals(cause.getId(), result.id());
        assertEquals(cause.getTitle(), result.title());
        // Проверка на допълнителни атрибути по аналогия

        // Верифицираме, че методът save е извикан веднъж
        verify(causeRepository, times(1)).save(any(Cause.class));
    }

    @Test
    public void testDeleteCause() {
        // Изтриваме без да връщаме стойност
        doNothing().when(causeRepository).deleteById(anyLong());

        causeService.deleteCause(1L);

        // Потвърждаваме, че изтриването е извикано
        verify(causeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllCauses() {
        // Настройка – repository връща списък с един запис
        when(causeRepository.findAll()).thenReturn(List.of(cause));

        List<CauseShortInfoDTO> causes = causeService.getAllCauses();

        assertNotNull(causes);
        assertEquals(1, causes.size());
        assertEquals(cause.getId(), causes.get(0).id());
    }

    @Test
    public void testGetCauseById_Success() {
        // Настройка – repository връща Optional с нашия запис
        when(causeRepository.findById(1L)).thenReturn(Optional.of(cause));

        CauseShortInfoDTO result = causeService.getCauseById(1L);

        assertNotNull(result);
        assertEquals(cause.getId(), result.id());
    }

    @Test
    public void testGetCauseById_NotFound() {
        // Настройка – repository връща празен Optional
        when(causeRepository.findById(1L)).thenReturn(Optional.empty());

        // Очакваме изключение CauseNotFoundException при опит за намиране
        assertThrows(CauseNotFoundException.class, () -> causeService.getCauseById(1L));
    }
}
