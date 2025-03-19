package com.softuni.volunteerplatform.causes.model.entity;

import com.softuni.volunteerplatform.causes.model.enums.Level;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "causes")
@Getter

public class Cause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Level level;

    public Cause setTitle(String title) {
        this.title = title;
        return this;
    }

    public Cause setDescription(String description) {
        this.description = description;
        return this;
    }

    public Cause setLevel(Level level) {
        this.level = level;
        return this;
    }


}
