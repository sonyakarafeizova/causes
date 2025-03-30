package com.softuni.volunteerplatform.causes.web;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseShortInfoDTO;
import com.softuni.volunteerplatform.causes.model.entity.Cause;
import com.softuni.volunteerplatform.causes.repository.CauseRepository;
import com.softuni.volunteerplatform.causes.service.CauseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/causes")
@RequiredArgsConstructor
public class CauseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CauseController.class);
    private final CauseService causeService;



    @GetMapping
    public ResponseEntity<List<CauseShortInfoDTO>> getAllCauses() {
        return ResponseEntity.ok(
                causeService.getAllCauses()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<CauseShortInfoDTO> getById(@PathVariable("id")Long id){
        return ResponseEntity.ok(causeService.getCauseById(id));
    }

    @PostMapping
    public ResponseEntity<CauseShortInfoDTO> createCause(@RequestBody AddCauseDTO addCauseDTO) {
        LOGGER.info("Going to create a cause {}", addCauseDTO);
        causeService.createCause(addCauseDTO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CauseShortInfoDTO> deleteById(@PathVariable("id") Long id){
    causeService.deleteCause(id);
    return ResponseEntity.ok().build();
    }
}
