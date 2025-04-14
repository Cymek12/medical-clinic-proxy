package com.example.medical_clinic_proxy.service;

import com.example.medical_clinic_proxy.client.VisitClient;
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
    private final VisitClient visitClient;

    public PageContent<VisitDTO> getVisitsByPatient(String patientEmail, Pageable pageable) {
        return visitClient.getVisitsByPatient(patientEmail, pageable);
    }

    public VisitDTO reserveVisit(ReserveVisitCommand reserveVisitCommand) {
        return visitClient.reserveVisit(reserveVisitCommand);
    }

    public PageContent<VisitDTO> getAvailableVisitsByDoctor(String doctorEmail, Pageable pageable) {
        return visitClient.getAvailableVisitsByDoctor(doctorEmail, pageable);
    }

    public PageContent<VisitDTO> getAvailableVisitsByDayAndSpecialization(String specialization, VisitDayCommand visitDayCommand, Pageable pageable) {
        return visitClient.getAvailableVisitsByDayAndSpecialization(specialization, visitDayCommand, pageable);
    }

}
