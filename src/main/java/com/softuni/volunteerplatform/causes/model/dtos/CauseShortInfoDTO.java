package com.softuni.volunteerplatform.causes.model.dtos;

import com.softuni.volunteerplatform.causes.model.enums.Level;


public record CauseShortInfoDTO(
        Long id,
        String title,
        String description,
        Level level,
        String imageUrl
) {
}

