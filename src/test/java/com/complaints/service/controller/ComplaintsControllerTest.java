package com.complaints.service.controller;

import com.complaints.service.BaseTest;
import static com.complaints.service.controller.requests.ComplaintControllerRequestSeeder.getCreateComplaintRequest;
import static com.complaints.service.controller.requests.ComplaintControllerRequestSeeder.getUpdateComplaintRequest;
import com.complaints.service.model.entity.Complaint;
import com.complaints.service.repository.ComplaintRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

class ComplaintsControllerTest extends BaseTest {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Test
    @Transactional
    public void shouldGetPage() throws Exception {
        var complaint = saveComplaint(123L);
        saveComplaint(145L);
        mockMvc.perform(get("/api/complaints")
                        .header("userID", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].description").value(complaint.getDescription()))
                .andExpect(jsonPath("$.content[0].counter").value(complaint.getCounter()))
                .andExpect(jsonPath("$.content[0].countryCode").value(complaint.getCountryCode()))
                .andExpect(jsonPath("$.content[0].userId").value(123))
                .andExpect(jsonPath("$.content[0].productId").value(complaint.getProductId()))
                .andExpect(jsonPath("$.content[0].description").value(complaint.getDescription()))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(20))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @Transactional
    public void shouldAddNewComplaint() throws Exception {
        prepareMockServerWithSuccessResponse("PL");
        var request = getCreateComplaintRequest();

        mockMvc.perform(post("/api/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userID", "123")
                        .header("X-Forwarded-For", "1.1.1.1")
                        .content(getAsJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value(request.description()))
                .andExpect(jsonPath("$.productId").value(request.productId()))
                .andExpect(jsonPath("$.counter").value(1))
                .andExpect(jsonPath("$.countryCode").value("PL"))
                .andExpect(jsonPath("$.userId").value(123));
    }

    @Test
    @Transactional
    public void shouldReturnExceptionWhenUserIdIsNotProvided() throws Exception {
        prepareMockServerWithSuccessResponse("PL");
        var request = getCreateComplaintRequest();

        mockMvc.perform(post("/api/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Forwarded-For", "1.1.1.1")
                        .content(getAsJson(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void shouldReturnExceptionWhenUserIpIsNotProvided() throws Exception {
        prepareMockServerWithSuccessResponse("PL");
        var request = getCreateComplaintRequest();

        mockMvc.perform(post("/api/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userID", "123")
                        .content(getAsJson(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void shouldIncrementCounterWhenAddingTheSameComplaint() throws Exception {
        prepareMockServerWithSuccessResponse("PL");
        var complaint = saveComplaint(123L);
        var request = getCreateComplaintRequest(complaint.getProductId(), complaint.getDescription());

        mockMvc.perform(post("/api/complaints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userID", complaint.getUserId())
                        .content(getAsJson(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.counter").value(2));
    }

    @Test
    @Transactional
    public void shouldUpdateComplaint() throws Exception {
        prepareMockServerWithSuccessResponse("PL");
        var complaint = saveComplaint(123L);
        var request = getUpdateComplaintRequest("Updated description");

        mockMvc.perform(put("/api/complaints/" + complaint.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userID", complaint.getUserId())
                        .content(getAsJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    private Complaint saveComplaint(long userId) {
        var complaint = new Complaint();
        complaint.setUserId(userId);
        complaint.setProductId(123L);
        complaint.setDescription("Test description");
        complaint.setCounter(1L);
        complaint.setCountryCode("PL");
        return complaintRepository.save(complaint);
    }
}
