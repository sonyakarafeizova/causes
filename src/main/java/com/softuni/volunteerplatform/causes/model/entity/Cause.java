package com.softuni.volunteerplatform.causes.model.entity;

import com.softuni.volunteerplatform.causes.model.enums.Level;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;

    @Column(name = "author_name")
    private String authorName;

    public Cause setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Column(name = "image_url")
    private String imageUrl;

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

    public Cause setId(Long id) {
        this.id = id;
        return this;
    }

    public  Cause setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public Cause setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }
}
