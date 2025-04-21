package com.complaints.service.model.DTOs;

import com.complaints.service.common.validator.NoHtml;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ComplaintDTO(
        long id,
        long productId,
        long userId,
        long counter,
        @NoHtml @NotBlank String description,
        @NotNull String countryCode,
        @NotNull Instant createdDate
) {
}
