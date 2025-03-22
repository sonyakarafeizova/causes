package com.softuni.volunteerplatform.causes.service;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;




public interface CauseService {
    CauseDTO createCause(AddCauseDTO addCauseDTO);

    CauseDTO getCauseById(Long id);
    PagedModel<CauseDTO> getAllCauses(Pageable pageable);

}
