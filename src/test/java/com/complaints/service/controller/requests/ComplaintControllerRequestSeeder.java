package com.complaints.service.controller.requests;

import com.complaints.service.model.DTOs.request.CreateComplaintRequest;
import com.complaints.service.model.DTOs.request.UpdateComplaintRequest;

import java.util.concurrent.ThreadLocalRandom;

public class ComplaintControllerRequestSeeder {

    public static CreateComplaintRequest getCreateComplaintRequest(
            long productId,
            String description
    ) {
        return new CreateComplaintRequest(
                productId,
                description
        );
    }

    public static CreateComplaintRequest getCreateComplaintRequest() {
        return new CreateComplaintRequest(
                Math.abs(ThreadLocalRandom.current().nextLong()),
                "Test description"
        );
    }

    public static UpdateComplaintRequest getUpdateComplaintRequest(
            String description
    ) {
        return new UpdateComplaintRequest(
                description
        );
    }

}
