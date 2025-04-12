package com.example.medical_clinic_proxy.service;

import com.example.medical_clinic_proxy.client.DoctorClient;
import com.example.medical_clinic_proxy.client.VisitClient;
import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.dto.DoctorDTO;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitClient visitClient;
    private final DoctorClient doctorClient;

    public PageContent<VisitDTO> getVisitsByPatient(String patientEmail, Pageable pageable) {
        return visitClient.getVisitsByPatient(patientEmail, pageable);
    }

    public VisitDTO reserveVisit(String patientEmail, String visitId) {
        return visitClient.reserveVisit(patientEmail, visitId);
    }

    public PageContent<VisitDTO> getAvailableVisitsByDoctor(String doctorEmail, Pageable pageable) {
        return visitClient.getAvailableVisitsByDoctor(doctorEmail, pageable);
    }

}
