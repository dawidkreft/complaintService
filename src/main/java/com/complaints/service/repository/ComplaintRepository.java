package com.complaints.service.repository;

import com.complaints.service.model.DTOs.ComplaintDTO;
import com.complaints.service.model.entity.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Optional<Complaint> findByProductIdAndUserId(Long productId, Long userId);

    Page<Complaint> findByUserId(Long userId, Pageable pageable);
}
