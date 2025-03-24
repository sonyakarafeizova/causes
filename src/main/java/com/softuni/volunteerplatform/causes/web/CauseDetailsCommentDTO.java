package com.softuni.volunteerplatform.causes.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CauseDetailsCommentDTO {
    private Long id;
    private String content;
    private String authorName;
}
