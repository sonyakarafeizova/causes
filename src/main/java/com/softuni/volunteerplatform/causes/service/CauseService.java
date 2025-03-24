package com.softuni.volunteerplatform.causes.service;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseShortInfoDTO;

import com.softuni.volunteerplatform.causes.model.entity.Cause;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;


public interface CauseService {
    CauseShortInfoDTO createCause(AddCauseDTO addCauseDTO);

    void deleteCause(Long causeId);

    List<CauseShortInfoDTO> getAllCauses();

    CauseShortInfoDTO getCauseById(Long id);
}
