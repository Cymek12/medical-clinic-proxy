package com.example.medical_clinic_proxy.client;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.command.VisitDayCommand;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "app", url = "${spring.cloud.openfeign.client.config.postClient.url}")
public interface MedicalClinicClient {

    @PatchMapping("/reservation")
    VisitDTO reserveVisit(@RequestBody ReserveVisitCommand reserveVisitCommand);

    @GetMapping("/available")
    PageContent<VisitDTO> getAvailableVisits(
            Pageable pageable,
            @RequestParam(required = false) String patientEmail,
            @RequestParam(required = false) String doctorEmail,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) VisitDayCommand visitDayCommand);
}