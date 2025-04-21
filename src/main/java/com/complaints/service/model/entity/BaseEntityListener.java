package com.complaints.service.model.entity;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

public class BaseEntityListener {
    @PrePersist
    public void setCreatedDate(BaseEntity entity) {
        Instant now = Instant.now();
        entity.setCreatedDate(now);
        entity.setUpdatedDate(now);
    }

    @PreUpdate
    public void setUpdatedDate(BaseEntity entity) {
        entity.setUpdatedDate(Instant.now());
    }
}
