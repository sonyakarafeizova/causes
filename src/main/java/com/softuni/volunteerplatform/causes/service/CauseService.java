package com.softuni.volunteerplatform.causes.service;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseDTO;

public interface CauseService {
    CauseDTO createCause(AddCauseDTO addCauseDTO);
    CauseDTO getCauseById(Long id);

}
