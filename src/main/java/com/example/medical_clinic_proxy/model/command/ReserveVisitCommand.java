package com.example.medical_clinic_proxy.model.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReserveVisitCommand {
    private String patientEmail;
    private String visitId;
}
