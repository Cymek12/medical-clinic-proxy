package com.example.medical_clinic_proxy.client;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "app", url = "http://localhost:8080/visits")
public interface VisitClient {

    @GetMapping("/{patientEmail}")
    PageContent<VisitDTO> getVisitsByPatient(@PathVariable("patientEmail") String patientEmail, Pageable pageable);

    @PatchMapping("/{patientEmail}/{visitId}")
    VisitDTO reserveVisit(@PathVariable("patientEmail") String email, @PathVariable("visitId") String id);

    @GetMapping("/available/{doctorEmail}")
    PageContent<VisitDTO> getAvailableVisitsByDoctor(@PathVariable("doctorEmail") String doctorEmail, Pageable pageable);
}
