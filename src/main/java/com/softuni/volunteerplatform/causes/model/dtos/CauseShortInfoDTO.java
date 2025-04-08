package com.softuni.volunteerplatform.causes.model.dtos;

import com.softuni.volunteerplatform.causes.model.enums.Level;

import java.time.LocalDateTime;


public record CauseShortInfoDTO(
        Long id,
        String title,
        String description,
        Level level,
        String imageUrl,
        LocalDateTime created
) {
}

