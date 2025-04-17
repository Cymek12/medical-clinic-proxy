package com.example.medical_clinic_proxy.service;

import com.example.medical_clinic_proxy.client.MedicalClinicClient;
import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.command.VisitDayCommand;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final MedicalClinicClient medicalClinicClient;

    public PageContent<VisitDTO> getAvailableVisitsProcessor(Pageable pageable, String patientEmail, String doctorEmail, String specialization, VisitDayCommand visitDayCommand) {
        return medicalClinicClient.getAvailableVisits(pageable, patientEmail, doctorEmail, specialization, visitDayCommand);
    }

    public VisitDTO reserveVisit(ReserveVisitCommand reserveVisitCommand) {
        return medicalClinicClient.reserveVisit(reserveVisitCommand);
    }
}