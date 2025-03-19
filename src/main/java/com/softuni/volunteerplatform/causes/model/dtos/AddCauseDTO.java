package com.softuni.volunteerplatform.causes.model.dtos;
import com.softuni.volunteerplatform.causes.model.enums.Level;


public record AddCauseDTO (
    String title,
    String description,
    Level level
){

}
