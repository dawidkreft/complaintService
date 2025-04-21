package com.complaints.service.service;

import com.complaints.service.exception.ComplaintNotFoundException;
import com.complaints.service.mapper.ComplaintDtoMapper;
import com.complaints.service.model.DTOs.ComplaintDTO;
import com.complaints.service.model.DTOs.request.CreateComplaintRequest;
import com.complaints.service.model.DTOs.request.UpdateComplaintRequest;
import com.complaints.service.model.entity.Complaint;
import com.complaints.service.repository.ComplaintRepository;
import com.complaints.service.security.ForbiddenAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ComplaintsService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintDtoMapper complaintDtoMapper;
    private final UserCountryCodeResolver userCountryCodeResolver;

    public ComplaintDTO addNewComplaint(CreateComplaintRequest request, Long userId) {
        return complaintRepository.findByProductIdAndUserId(request.productId(), userId)
                .map(this::increaseCounterExistingComplaint)
                .orElseGet(() -> createAndSaveNewComplaint(request, userId));
    }

    private ComplaintDTO increaseCounterExistingComplaint(Complaint complaint) {
        complaint.setCounter(complaint.getCounter() + 1);
        complaintRepository.save(complaint);
        log.debug("Complaint counter increased: {}", complaint);
        return complaintDtoMapper.toDto(complaint);
    }

    private ComplaintDTO createAndSaveNewComplaint(CreateComplaintRequest request, Long userId) {
        var complaintEntity = new Complaint();
        complaintEntity.setProductId(request.productId());
        complaintEntity.setUserId(userId);
        complaintEntity.setCountryCode(userCountryCodeResolver.resolveUserCountryCode());
        complaintEntity.setDescription(request.description());
        complaintRepository.save(complaintEntity);
        log.debug("Complaint created: {}", complaintEntity);
        return complaintDtoMapper.toDto(complaintEntity);
    }

    public ComplaintDTO update(long id, UpdateComplaintRequest updateComplaintRequest, Long userId) {
        var complaint = complaintRepository.findById(id).orElseThrow(ComplaintNotFoundException::new);
        validate(userId, complaint);
        complaint.setDescription(updateComplaintRequest.description());
        complaintRepository.save(complaint);
        log.debug("Complaint with id {} updated", complaint.getId());
        return complaintDtoMapper.toDto(complaint);
    }

    private static void validate(Long userId, Complaint complaint) {
        if (!complaint.getUserId().equals(userId)) {
            throw new ForbiddenAccessException("Forbidden");
        }
    }

    @Transactional(readOnly = true)
    public Page<ComplaintDTO> getPage(Pageable pageable, Long userId) {
        Page<Complaint> page = complaintRepository.findByUserId(userId, pageable);
        return complaintDtoMapper.mapPageToDto(page);
    }
}
