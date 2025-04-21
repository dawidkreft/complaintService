package com.complaints.service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Setter(AccessLevel.PROTECTED)
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

    @NotNull
    @Setter(AccessLevel.PROTECTED)
    @Column(nullable = false)
    private Instant updatedDate;
}
