package com.example.medical_clinic_proxy.service;

import com.example.medical_clinic_proxy.client.PatientClient;
import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientClient visitClient;

    public PageContent<VisitDTO> getVisitsByPatient(String patientEmail, Pageable pageable) {
        return visitClient.getVisitsByPatient(patientEmail, pageable);
    }

}
