package com.complaints.service.model.DTOs.request;

import com.complaints.service.common.validator.NoHtml;
import jakarta.validation.constraints.NotBlank;

public record CreateComplaintRequest(
        long productId,
        @NoHtml @NotBlank String description
) {
}
