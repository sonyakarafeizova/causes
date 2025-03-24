package com.softuni.volunteerplatform.causes.service.impl;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseShortInfoDTO;
import com.softuni.volunteerplatform.causes.model.entity.Cause;
import com.softuni.volunteerplatform.causes.repository.CauseRepository;
import com.softuni.volunteerplatform.causes.service.CauseService;
import com.softuni.volunteerplatform.causes.service.exception.CauseNotFoundException;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CauseServiceImpl implements CauseService {

    private final CauseRepository causeRepository;
    private final Logger LOGGER= LoggerFactory.getLogger(CauseService.class);




    @Override
    public CauseShortInfoDTO createCause(AddCauseDTO addCauseDTO) {
        Cause cause=causeRepository.save(map(addCauseDTO));
        return map(cause);
    }

    @Override
    public void deleteCause(Long causeId) {
        causeRepository.deleteById(causeId);
    }


    @Override
    public List<CauseShortInfoDTO> getAllCauses() {
        return causeRepository
                .findAll()
                .stream()
                .map(CauseServiceImpl::map)
                .toList();
    }

    @Override
    public CauseShortInfoDTO getCauseById(Long id) {
        return causeRepository
                .findById(id)
                .map(CauseServiceImpl::map)
                .orElseThrow(CauseNotFoundException::new);
    }


    private static CauseShortInfoDTO map(Cause cause) {
        return new CauseShortInfoDTO(
               cause.getId(),
                cause.getTitle(),
                cause.getDescription(),
                cause.getLevel(),
                cause.getImageUrl()
        );
    }

    private static Cause map(AddCauseDTO addCauseDTO){
        return new Cause()
                .setTitle(addCauseDTO.title())
                .setDescription(addCauseDTO.description())
                .setLevel(addCauseDTO.level())
                .setImageUrl(addCauseDTO.imageUrl());

    }

}
