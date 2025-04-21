package com.complaints.service.controller;

import com.complaints.service.model.DTOs.ComplaintDTO;
import com.complaints.service.model.DTOs.request.CreateComplaintRequest;
import com.complaints.service.model.DTOs.request.UpdateComplaintRequest;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(path = "api/complaints")
public interface ComplaintsEndpoints {

    @Timed
    @GetMapping
    @Operation(summary = "Get page of complaints.", description = "UserId have to be provided by header.")
    @ResponseStatus(OK)
    Page<ComplaintDTO> getPage(
            Pageable pageable
    );

    @Timed
    @PostMapping
    @Operation(summary = "Add new complaint.", description = "UserId have to be provided by header.")
    @ResponseStatus(CREATED)
    ComplaintDTO addNewComplaint(
            @RequestBody @Valid CreateComplaintRequest request
    );

    @Timed
    @PutMapping("/{id}")
    @Operation(summary = "Update complaint.", description = "UserId have to be provided by header.")
    @ResponseStatus(OK)
    ComplaintDTO update(
            @RequestBody @Valid UpdateComplaintRequest updateComplaintRequest,
            @PathVariable(value = "id") long id
    );
}
