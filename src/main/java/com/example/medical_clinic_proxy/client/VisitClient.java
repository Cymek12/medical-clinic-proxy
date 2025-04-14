package com.example.medical_clinic_proxy.client;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.command.VisitDayCommand;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "app", url = "http://app:8080/visits")
public interface VisitClient {

    @GetMapping
    PageContent<VisitDTO> getVisitsByPatient(@RequestParam(required = false) String patientEmail, Pageable pageable);

    @PatchMapping
    VisitDTO reserveVisit(@RequestBody ReserveVisitCommand reserveVisitCommand);

    @GetMapping("/available/{doctorEmail}")
    PageContent<VisitDTO> getAvailableVisitsByDoctor(@PathVariable("doctorEmail") String doctorEmail, Pageable pageable);

    @GetMapping
    PageContent<VisitDTO> getAvailableVisitsByDayAndSpecialization(
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) VisitDayCommand visitDayCommand,
            Pageable pageable
    );
}
