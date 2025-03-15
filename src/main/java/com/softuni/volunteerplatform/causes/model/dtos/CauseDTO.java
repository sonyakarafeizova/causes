package com.softuni.volunteerplatform.causes.model.dtos;

import com.softuni.volunteerplatform.causes.model.enums.Level;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



public record CauseDTO(
        Long id,
        String name,
        String description,
        Level level
) {
}

