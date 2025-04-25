package com.example.medical_clinic_proxy.integration;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.dto.DoctorDTO;
import com.example.medical_clinic_proxy.model.dto.PatientDTO;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import java.util.List;

import static com.example.medical_clinic_proxy.TestDataBuilder.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWireMock(@ConfigureWireMock(name = "MedicalClinicClient", port = 8889))
public class MedicalClinicIntegrationTest {
    @InjectWireMock("MedicalClinicClient")
    private WireMockServer wireMockServer;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getVisits_returnPageableContentVisitDTO() throws Exception {
        PatientDTO patientDTO = buildPatientDTO(1L, "patient@gmail.com");
        List<VisitDTO> visits = List.of(
                buildVisitDTO(1L, buildDoctorDTO(1L, "doctor@gmail.com"), patientDTO),
                buildVisitDTO(2L, buildDoctorDTO(2L, "doctor2@gmail.com"), patientDTO)
        );
        PageContent<VisitDTO> pageContentDTO = new PageContent<>(2L, 0, 1, visits);

        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/visits/available"))
                .withQueryParam("size", equalTo(String.valueOf(5)))
                .withQueryParam("page", equalTo(String.valueOf(0)))
                .willReturn(okJson(objectMapper.writeValueAsString(pageContentDTO))));

        mockMvc.perform(get("/visits/available")
                        .param("size", "5")
                        .param("page", "0"))
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

    @Test
    public void getVisits_SourceNotAvailable_throwsFallbackException() throws Exception {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/visits/available"))
                .withQueryParam("size", equalTo(String.valueOf(5)))
                .withQueryParam("page", equalTo(String.valueOf(0)))
                .willReturn(aResponse().withStatus(503)));

        mockMvc.perform(get("/visits/available")
                        .param("size", "5")
                        .param("page", "0"))
                .andDo(print())
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Fallback get visit exception"))
                .andExpect(jsonPath("$.httpStatus").value("SERVICE_UNAVAILABLE"));
    }

    @Test
    public void reserveVisit_returnVisitDTO() throws Exception {
        ReserveVisitCommand reserveVisitCommand = ReserveVisitCommand.builder()
                .patientEmail("patient@gmail.com")
                .visitId("1")
                .build();
        DoctorDTO doctorDTO = buildDoctorDTO(1L, "doctor@gmail.com");
        PatientDTO patientDTO = buildPatientDTO(1L, "patient@gmail.com");
        VisitDTO visitDTO = buildVisitDTO(1L, doctorDTO, patientDTO);
        wireMockServer.stubFor(WireMock.patch(WireMock.urlPathTemplate("/visits/reservation"))
                .willReturn(okJson(objectMapper.writeValueAsString(visitDTO))));

        mockMvc.perform(patch("/visits/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveVisitCommand)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void reserveVisit_sourceNotAvailable_throwsFallbackException() throws Exception {
        ReserveVisitCommand reserveVisitCommand = ReserveVisitCommand.builder()
                .patientEmail("patient@gmail.com")
                .visitId("1")
                .build();

        wireMockServer.stubFor(WireMock.patch(WireMock.urlPathTemplate("/visits/reservation"))
                .willReturn(aResponse().withStatus(503)));

        mockMvc.perform(patch("/visits/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reserveVisitCommand)))
                .andDo(print())
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Fallback reserve visit exception"))
                .andExpect(jsonPath("$.httpStatus").value("SERVICE_UNAVAILABLE"));
    }
}
