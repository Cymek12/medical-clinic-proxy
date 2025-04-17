package com.example.medical_clinic_proxy.controller;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.dto.DoctorDTO;
import com.example.medical_clinic_proxy.model.dto.PatientDTO;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.example.medical_clinic_proxy.service.VisitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.medical_clinic_proxy.TestDataBuilder.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class VisitControllerTest {
    @MockitoBean
    private VisitService visitService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    void getVisitsByPatient_returnPageContentDTO() throws Exception {
//        String patientEmail = "patient@gmail.com";
//        Pageable pageable = PageRequest.of(0, 5);
//        PatientDTO patientDTO = buildPatientDTO(1L, "patient@gmail.com");
//        List<VisitDTO> visits = List.of(
//                buildVisitDTO(1L, buildDoctorDTO(1L, "doctor@gmail.com"), patientDTO),
//                buildVisitDTO(2L, buildDoctorDTO(2L, "doctor2@gmail.com"), patientDTO)
//        );
//        PageContent<VisitDTO> pageContentDTO = new PageContent<>(2L, 0, 1, visits);
//        when(visitService.getVisitsByPatient(patientEmail, pageable)).thenReturn(pageContentDTO);
//        mockMvc.perform(get("/visits")
//                        .param("patientEmail", "patient@gmail.com")
//                        .param("size", "5")
//                        .param("page", "0")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalElements").value(2L))
//                .andExpect(jsonPath("$.currentPage").value(0))
//                .andExpect(jsonPath("$.totalPageNumber").value(1))
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.content[0].id").value(1))
//                .andExpect(jsonPath("$.content[0].doctorDTO.id").value(1L))
//
}
