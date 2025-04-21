package com.complaints.service.controller;

import com.complaints.service.model.DTOs.ComplaintDTO;
import com.complaints.service.model.DTOs.request.CreateComplaintRequest;
import com.complaints.service.model.DTOs.request.UpdateComplaintRequest;
import com.complaints.service.security.UserDetailsProvider;
import com.complaints.service.service.ComplaintsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ComplaintsController implements ComplaintsEndpoints {

    private final ComplaintsService complaintsService;
    private final UserDetailsProvider userDetailsProvider;

    @Override
    public Page<ComplaintDTO> getPage(Pageable pageable) {
        return complaintsService.getPage(pageable, userDetailsProvider.getUserId());
    }

    @Override
    public ComplaintDTO addNewComplaint(CreateComplaintRequest request) {
        return complaintsService.addNewComplaint(request, userDetailsProvider.getUserId());
    }

    @Override
    public ComplaintDTO update(UpdateComplaintRequest updateComplaintRequest, long id) {
        return complaintsService.update(id, updateComplaintRequest, userDetailsProvider.getUserId());
    }
}
