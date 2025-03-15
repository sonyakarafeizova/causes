package com.softuni.volunteerplatform.causes.web;

import com.softuni.volunteerplatform.causes.service.CauseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CauseController {
    private final CauseService causeService;


}
