package com.complaints.service.model.entity;

import com.complaints.service.common.validator.NoHtml;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "complaints")
public class Complaint extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private Long productId;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @NoHtml
    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private Long counter = 1L;

    @NotNull
    @Column(nullable = false, length = 10)
    private String countryCode;
}
