package com.example.medical_clinic_proxy.controller;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.dto.DoctorDTO;
import com.example.medical_clinic_proxy.model.dto.PatientDTO;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.example.medical_clinic_proxy.service.PatientService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class PatientControllerTest {
    @MockitoBean
    private PatientService visitClientService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getVisitsByPatient_returnPageContentDTO() throws Exception {
        String patientEmail = "patient@gmail.com";
        Pageable pageable = PageRequest.of(0, 5);
        PatientDTO patientDTO = buildPatientDTO(1L, "patient@gmail.com");
        List<VisitDTO> visits = List.of(
                buildVisitDTO(1L, buildDoctorDTO(1L, "doctor@gmail.com"), patientDTO),
                buildVisitDTO(2L, buildDoctorDTO(2L, "doctor2@gmail.com"), patientDTO)
        );
        PageContent<VisitDTO> pageContentDTO = new PageContent<>(2L, 0, 1, visits);
        when(visitClientService.getVisitsByPatient(patientEmail, pageable)).thenReturn(pageContentDTO);
        mockMvc.perform(get("/patients/{patientEmail}", patientEmail)
                        .param("size", "5")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2L))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalPageNumber").value(1))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].doctorDTO.id").value(1L))
                .andExpect(jsonPath("$.content[0].doctorDTO.firstName").value("jan"))
                .andExpect(jsonPath("$.content[0].doctorDTO.lastName").value("kowalski"))
                .andExpect(jsonPath("$.content[0].doctorDTO.email").value("doctor@gmail.com"))
                .andExpect(jsonPath("$.content[0].doctorDTO.specialization").value("kardiolog"))
                .andExpect(jsonPath("$.content[0].doctorDTO.institutionIds").isArray())
                .andExpect(jsonPath("$.content[0].patientDTO.id").value(1L))
                .andExpect(jsonPath("$.content[0].patientDTO.email").value("patient@gmail.com"))
                .andExpect(jsonPath("$.content[0].patientDTO.idCardNo").value("123"))
                .andExpect(jsonPath("$.content[0].patientDTO.firstName").value("jan"))
                .andExpect(jsonPath("$.content[0].patientDTO.lastName").value("kowalski"))
                .andExpect(jsonPath("$.content[0].patientDTO.phoneNumber").value("123456789"))
                .andExpect(jsonPath("$.content[0].patientDTO.birthday").value("2000-02-17"))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].doctorDTO.id").value(2L))
                .andExpect(jsonPath("$.content[1].doctorDTO.firstName").value("jan"))
                .andExpect(jsonPath("$.content[1].doctorDTO.lastName").value("kowalski"))
                .andExpect(jsonPath("$.content[1].doctorDTO.email").value("doctor2@gmail.com"))
                .andExpect(jsonPath("$.content[1].doctorDTO.specialization").value("kardiolog"))
                .andExpect(jsonPath("$.content[1].doctorDTO.institutionIds").isArray())
                .andExpect(jsonPath("$.content[1].patientDTO.id").value(1L))
                .andExpect(jsonPath("$.content[1].patientDTO.email").value("patient@gmail.com"))
                .andExpect(jsonPath("$.content[1].patientDTO.idCardNo").value("123"))
                .andExpect(jsonPath("$.content[1].patientDTO.firstName").value("jan"))
                .andExpect(jsonPath("$.content[1].patientDTO.lastName").value("kowalski"))
                .andExpect(jsonPath("$.content[1].patientDTO.phoneNumber").value("123456789"))
                .andExpect(jsonPath("$.content[1].patientDTO.birthday").value("2000-02-17"));
    }

    private VisitDTO buildVisitDTO(Long id, DoctorDTO doctorDTO, PatientDTO patientDTO) {
        return VisitDTO.builder()
                .id(id)
                .startDateTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(16, 0, 0)))
                .endDateTime(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(18, 0, 0)))
                .doctorDTO(doctorDTO)
                .patientDTO(patientDTO)
                .build();
    }

    private DoctorDTO buildDoctorDTO(Long id, String email) {
        return DoctorDTO.builder()
                .id(id)
                .firstName("jan")
                .lastName("kowalski")
                .email(email)
                .specialization("kardiolog")
                .institutionIds(new ArrayList<>())
                .build();
    }

    private PatientDTO buildPatientDTO(Long id, String email) {
        return PatientDTO.builder()
                .id(id)
                .email(email)
                .idCardNo("123")
                .firstName("jan")
                .lastName("kowalski")
                .phoneNumber("123456789")
                .birthday(LocalDate.of(2000, 2, 17))
                .build();
    }
}
