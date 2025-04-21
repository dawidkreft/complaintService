package com.complaints.service.mapper;

import com.complaints.service.model.DTOs.ComplaintDTO;
import com.complaints.service.model.entity.Complaint;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ComplaintDtoMapper {

    ComplaintDTO toDto(Complaint complaint);

    default Page<ComplaintDTO> mapPageToDto(Page<Complaint> complaints) {
        return complaints.map(this::toDto);
    }
}
