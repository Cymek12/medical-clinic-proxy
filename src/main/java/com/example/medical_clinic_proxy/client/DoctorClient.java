package com.example.medical_clinic_proxy.client;

import com.example.medical_clinic_proxy.model.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "app", url = "http://localhost:8080/doctors")
public interface DoctorClient {
    @GetMapping("/{email}")
    DoctorDTO getDoctorByEmail(@PathVariable("email") String email);
}
