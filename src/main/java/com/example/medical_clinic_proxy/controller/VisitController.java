package com.example.medical_clinic_proxy.controller;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.command.VisitDayCommand;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.example.medical_clinic_proxy.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @GetMapping
    public PageContent<VisitDTO> getVisitsByPatient(@RequestParam(required = false) String patientEmail, Pageable pageable) {
        return visitService.getVisitsByPatient(patientEmail, pageable);
    }

    @PatchMapping("/reservation")
    public VisitDTO reserveVisit(@RequestBody ReserveVisitCommand reserveVisitCommand) {
        return visitService.reserveVisit(reserveVisitCommand);
    }

    @GetMapping("/available")
    public PageContent<VisitDTO> getAvailableVisitsByDoctor(@RequestParam(required = false) String doctorEmail, Pageable pageable) {
        return visitService.getAvailableVisitsByDoctor(doctorEmail, pageable);
    }

    @GetMapping("/available/specialization")
    public PageContent<VisitDTO> getAvailableVisitsByDayAndSpecialization(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) VisitDayCommand visitDayCommand,
            Pageable pageable) {
        return visitService.getAvailableVisitsByDayAndSpecialization(specialization, visitDayCommand, pageable);
    }
}
