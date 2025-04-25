package com.example.medical_clinic_proxy;

import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.dto.DoctorDTO;
import com.example.medical_clinic_proxy.model.dto.PatientDTO;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class TestDataBuilder {

    public static VisitDTO buildVisitDTO(Long id, DoctorDTO doctorDTO, PatientDTO patientDTO) {
        return VisitDTO.builder()
                .id(id)
                .startDateTime(LocalDateTime.of(LocalDate.of(2030, 1, 1), LocalTime.of(16, 0, 0)))
                .endDateTime(LocalDateTime.of(LocalDate.of(2030, 1, 1), LocalTime.of(18, 0, 0)))
                .doctorDTO(doctorDTO)
                .patientDTO(patientDTO)
                .build();
    }

    public static DoctorDTO buildDoctorDTO(Long id, String email) {
        return DoctorDTO.builder()
                .id(id)
                .firstName("jan")
                .lastName("kowalski")
                .email(email)
                .specialization("kardiolog")
                .institutionIds(new ArrayList<>())
                .build();
    }

    public static PatientDTO buildPatientDTO(Long id, String email) {
        return PatientDTO.builder()
                .id(id)
                .email(email)
                .idCardNo("123")
                .firstName("jan")
                .lastName("kowalski")
                .phoneNumber("123456789")
                .birthday(LocalDate.of(2000, 2, 17))
                .build();
    }

    public static ReserveVisitCommand buildreserveVisitCommand() {
        return ReserveVisitCommand.builder()
                .patientEmail("patient@gmail.com")
                .visitId("1")
                .build();
    }
}
