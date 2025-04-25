package com.example.medical_clinic_proxy.config;

import com.example.medical_clinic_proxy.client.MedicalClinicClient;
import com.example.medical_clinic_proxy.exception.FallbackException;
import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.command.VisitDayCommand;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MedicalClinicFallbackFactory implements FallbackFactory<MedicalClinicClient> {

    @Override
    public MedicalClinicClient create(Throwable cause) {
        return new MedicalClinicClient() {
            @Override
            public VisitDTO reserveVisit(ReserveVisitCommand reserveVisitCommand) {
                log.warn("fallback reserve visit");
                throw new FallbackException("Fallback reserve visit exception");
            }

            @Override
            public PageContent<VisitDTO> getAvailableVisits(Pageable pageable, String patientEmail, String doctorEmail, String specialization, VisitDayCommand visitDayCommand) {
                log.warn("fallback get visits");
                throw new FallbackException("Fallback get visit exception");
            }
        };
    }
}
