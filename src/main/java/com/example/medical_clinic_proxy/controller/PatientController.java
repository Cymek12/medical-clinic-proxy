package com.example.medical_clinic_proxy.controller;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.example.medical_clinic_proxy.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService visitClientService;

    @GetMapping("/{patientEmail}")
    public PageContent<VisitDTO> getVisitsByPatient(@PathVariable("patientEmail") String patientEmail, Pageable pageable) {
        return visitClientService.getVisitsByPatient(patientEmail, pageable);
    }

//    @PatchMapping("/{patientEmail}/{visitId}")
//    public VisitDTO reserveVisit(@PathVariable("patientEmail") String email, @PathVariable("visitId") String id) {
//        return visitClientService.reserveVisit(email, id);
//    }
//
//    @GetMapping("/{doctorEmail}")
//    public PageContent<VisitDTO> getAvailableDoctorVisits(Pageable pageable) {
//        return visitClientService.getAvailableVisits(pageable);
//    }
}
