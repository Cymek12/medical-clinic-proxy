package com.example.medical_clinic_proxy.client;

import com.example.medical_clinic_proxy.exception.FallbackException;
import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.dto.DoctorDTO;
import com.example.medical_clinic_proxy.model.dto.PatientDTO;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import java.util.List;

import static com.example.medical_clinic_proxy.TestDataBuilder.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@EnableWireMock(@ConfigureWireMock(name = "MedicalClinicClient", port = 8889))
public class MedicalClinicClientTest {
    @InjectWireMock("MedicalClinicClient")
    private WireMockServer wireMockServer;
    @Autowired
    private MedicalClinicClient medicalClinicClient;
    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    void setup() {
//        wireMockServer.start();
//    }
//
//    @AfterEach
//    void setdown() {
//        wireMockServer.stop();
//    }

    @Test
    void reserveVisit_returnVisitDTO() throws JsonProcessingException {
        ReserveVisitCommand reserveVisitCommand = ReserveVisitCommand.builder()
                .patientEmail("patient@gmail.com")
                .visitId("1")
                .build();
        DoctorDTO doctorDTO = buildDoctorDTO(1L, "doctor@gmail.com");
        PatientDTO patientDTO = buildPatientDTO(1L, "patient@gmail.com");
        VisitDTO visitDTO = buildVisitDTO(1L, doctorDTO, patientDTO);
        wireMockServer.stubFor(patch(urlPathTemplate("/visits/reservation"))
                .willReturn(okJson(objectMapper.writeValueAsString(visitDTO))));

        VisitDTO resultVisitDTO = medicalClinicClient.reserveVisit(reserveVisitCommand);

        verify(1, patchRequestedFor(urlPathTemplate("/visits/reservation")));
    }

    @Test
    void reserveVisit_SourceNotAvailable_fallback() {
        ReserveVisitCommand reserveVisitCommand = ReserveVisitCommand.builder()
                .patientEmail("patient@gmail.com")
                .visitId("1")
                .build();
        wireMockServer.stubFor(patch(urlPathTemplate("/visits/reservation"))
                .willReturn(aResponse().withStatus(503)));

//        verify(3, patchRequestedFor(urlPathTemplate("/visits/reservation")));
        assertThrows(FallbackException.class, () -> medicalClinicClient.reserveVisit(reserveVisitCommand));
    }

    @Test
    void getAvailableVisits_returnPageContentVisitDTO() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 5);
        PatientDTO patientDTO = buildPatientDTO(1L, "patient@gmail.com");
        List<VisitDTO> visits = List.of(
                buildVisitDTO(1L, buildDoctorDTO(1L, "doctor@gmail.com"), patientDTO),
                buildVisitDTO(2L, buildDoctorDTO(2L, "doctor2@gmail.com"), patientDTO)
        );
        PageContent<VisitDTO> pageContentDTO = new PageContent<>(2L, 0, 1, visits);
        wireMockServer.stubFor(get(urlEqualTo("/visits/available?size=5&page=0"))
                .willReturn(okJson(objectMapper.writeValueAsString(pageContentDTO))));

        PageContent<VisitDTO> resultPageContent = medicalClinicClient.getAvailableVisits(pageable, null, null, null, null);

        verify(1, getRequestedFor(urlPathTemplate("/visits/available")));
    }

    @Test
    void getAvailableVisits_return503() {
        Pageable pageable = PageRequest.of(0, 5);
        wireMockServer.stubFor(get(urlEqualTo("/visits/available?size=5&page=0"))
                .willReturn(aResponse().withStatus(503)));

        assertThrows(FallbackException.class, () -> medicalClinicClient.getAvailableVisits(pageable, null, null, null, null));
    }
}
