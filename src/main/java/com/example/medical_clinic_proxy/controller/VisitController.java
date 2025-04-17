package com.example.medical_clinic_proxy.controller;

import com.example.medical_clinic_proxy.model.ErrorMessage;
import com.example.medical_clinic_proxy.model.PageContent;
import com.example.medical_clinic_proxy.model.command.ReserveVisitCommand;
import com.example.medical_clinic_proxy.model.command.VisitDayCommand;
import com.example.medical_clinic_proxy.model.dto.VisitDTO;
import com.example.medical_clinic_proxy.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Visit operations")
@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @Operation(summary = "Get available visits specified by patient, doctor, doctor's specialization or day with visits number, current page and total page number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visits list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageContent.class))})
    })
    @GetMapping("/available")
    public PageContent<VisitDTO> getAvailableVisits(
            Pageable pageable,
            @RequestParam(required = false) String patientEmail,
            @RequestParam(required = false) String doctorEmail,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) VisitDayCommand visitDayCommand) {
        return visitService.getAvailableVisitsProcessor(pageable, patientEmail, doctorEmail, specialization, visitDayCommand);
    }

    @Operation(summary = "Reserve visit by given visit's id and patient's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visit was reserved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Patient or visit not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))})
    })
    @PatchMapping("/reservation")
    public VisitDTO reserveVisit(@RequestBody ReserveVisitCommand reserveVisitCommand) {
        return visitService.reserveVisit(reserveVisitCommand);
    }
}
