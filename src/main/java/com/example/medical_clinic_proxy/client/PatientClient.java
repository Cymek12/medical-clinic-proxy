package com.example.medical_clinic_proxy.client;

import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "app", url = "http://app:8080")
public interface PatientClient {

    @GetMapping("/visits/{patientEmail}")
    PageContent<VisitDTO> getVisitsByPatient(@PathVariable("patientEmail") String patientEmail, Pageable pageable);
}
//@FeignClient(value = "visit", url = "http://localhost:8080")
//public interface VisitClient {
//
//    @RequestMapping(method = RequestMethod.GET, value = "/visits/{patientEmail}")
//    PageContent<VisitDTO> getVisitsByPatient(String patientEmail, Pageable pageable);
//}

