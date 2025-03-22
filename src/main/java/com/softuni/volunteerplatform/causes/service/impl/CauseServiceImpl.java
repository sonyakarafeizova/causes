package com.softuni.volunteerplatform.causes.service.impl;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseDTO;
import com.softuni.volunteerplatform.causes.model.entity.Cause;
import com.softuni.volunteerplatform.causes.repository.CauseRepository;
import com.softuni.volunteerplatform.causes.service.CauseService;
import com.softuni.volunteerplatform.causes.service.exception.CauseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CauseServiceImpl implements CauseService {

    private final CauseRepository causeRepository;
    private final Logger LOGGER= LoggerFactory.getLogger(CauseService.class);




    @Override
    public CauseDTO createCause(AddCauseDTO addCauseDTO) {
        Cause cause=causeRepository.save(map(addCauseDTO));
        return map(cause);
    }

    @Override
    public CauseDTO getCauseById(Long id) {
        return causeRepository
                .findById(id)
                .map(CauseServiceImpl::map)
                .orElseThrow(CauseNotFoundException::new);
    }

    @Override
    public PagedModel<CauseDTO> getAllCauses(Pageable pageable) {
        return new PagedModel<>(causeRepository
                .findAll(pageable)
                .map(CauseServiceImpl::map));
    }


    private static CauseDTO map(Cause cause) {
        return new CauseDTO(
               cause.getId(),
                cause.getTitle(),
                cause.getDescription(),
                cause.getLevel()
        );
    }

    private static Cause map(AddCauseDTO addCauseDTO){
        return new Cause()
                .setTitle(addCauseDTO.title())
                .setDescription(addCauseDTO.description())
                .setLevel(addCauseDTO.level());

    }

}
