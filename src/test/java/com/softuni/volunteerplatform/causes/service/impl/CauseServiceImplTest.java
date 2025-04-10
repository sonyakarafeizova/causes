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
import static org.mockito.ArgumentMatchers.any;
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
    void setUp() {
        addCauseDTO = new AddCauseDTO(
                "TestTitle",
                "TestDescription",
                Level.JUNIOR,
                "http://example.com/test.png",
                "TestAuthor",
                LocalDateTime.now()
        );
        cause = new Cause()
                .setTitle(addCauseDTO.title())
                .setDescription(addCauseDTO.description())
                .setLevel(addCauseDTO.level())
                .setImageUrl(addCauseDTO.imageUrl())
                .setCreated(addCauseDTO.created())
                .setAuthorName(addCauseDTO.authorName());
        cause.setId(1L);
    }

    @Test
    void testCreateCause() {
        when(causeRepository.save(any(Cause.class))).thenReturn(cause);
        CauseShortInfoDTO result = causeService.createCause(addCauseDTO);
        assertNotNull(result);
        assertEquals(cause.getId(), result.id());
        assertEquals(cause.getTitle(), result.title());
        verify(causeRepository, times(1)).save(any(Cause.class));
    }

    @Test
    void testDeleteCause() {
        doNothing().when(causeRepository).deleteById(anyLong());
        causeService.deleteCause(1L);
        verify(causeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllCauses() {
        when(causeRepository.findAll()).thenReturn(List.of(cause));
        List<CauseShortInfoDTO> result = causeService.getAllCauses();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cause.getId(), result.get(0).id());
    }

    @Test
    void testGetCauseById_Success() {
        when(causeRepository.findById(1L)).thenReturn(Optional.of(cause));
        CauseShortInfoDTO result = causeService.getCauseById(1L);
        assertNotNull(result);
        assertEquals(cause.getId(), result.id());
    }

    @Test
    void testGetCauseById_NotFound() {
        when(causeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CauseNotFoundException.class, () -> causeService.getCauseById(1L));
    }
}
