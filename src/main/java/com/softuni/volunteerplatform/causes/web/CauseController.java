package com.softuni.volunteerplatform.causes.web;

import com.softuni.volunteerplatform.causes.model.dtos.AddCauseDTO;
import com.softuni.volunteerplatform.causes.model.dtos.CauseDTO;
import com.softuni.volunteerplatform.causes.repository.CauseRepository;
import com.softuni.volunteerplatform.causes.service.CauseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/causes")
@RequiredArgsConstructor
public class CauseController {
    private static final Logger LOGGER= LoggerFactory.getLogger(CauseController.class);
    private final CauseService causeService;
    private final CauseRepository causeRepository;
//    @GetMapping
//    public ResponseEntity<List<CauseDTO>> getAllCauses() {
//        return ResponseEntity.ok(causeService.getAllCauses());
//    }
    @PostMapping
    public ResponseEntity<CauseDTO> createCause(@RequestBody AddCauseDTO addCauseDTO){
        LOGGER.info("Going to create a cause {}",addCauseDTO);
        causeService.createCause(addCauseDTO);
        return ResponseEntity.ok().build();
    }

}
