package com.example.medical_clinic_proxy.controller;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.example.medical_clinic_proxy.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @GetMapping("/{patientEmail}")
    public PageContent<VisitDTO> getVisitsByPatient(@PathVariable("patientEmail") String patientEmail, Pageable pageable) {
        return visitService.getVisitsByPatient(patientEmail, pageable);
    }

    @PatchMapping("/{patientEmail}/{visitId}")
    public VisitDTO reserveVisit(@PathVariable("patientEmail") String email, @PathVariable("visitId") String id) {
        return visitService.reserveVisit(email, id);
    }

    @GetMapping("/available/{doctorEmail}")
    public PageContent<VisitDTO> getAvailableVisitsByDoctor(@PathVariable("doctorEmail") String doctorEmail, Pageable pageable) {
        return visitService.getAvailableVisitsByDoctor(doctorEmail, pageable);
    }


//
//    @GetMapping("/{doctorEmail}")
//    public PageContent<VisitDTO> getAvailableDoctorVisits(Pageable pageable) {
//        return visitClientService.getAvailableVisits(pageable);
//    }
}
