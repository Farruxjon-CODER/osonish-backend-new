package uz.osonish.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.osonish.backend.dto.*;
import uz.osonish.backend.service.EmployerService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employer")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('EMPLOYER')")
@Tag(name = "Employer Dashboard", description = "Endpoints for employers to manage vacancies and applicants")
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping("/vacancies")
    @Operation(summary = "Create a new vacancy")
    public ResponseEntity<VacancyResponseDTO> createVacancy(@Valid @RequestBody VacancyRequestDTO request) {
        return ResponseEntity.ok(employerService.createVacancy(request));
    }

    @GetMapping("/vacancies")
    @Operation(summary = "Get vacancies created by me")
    public ResponseEntity<List<VacancyResponseDTO>> getMyVacancies() {
        return ResponseEntity.ok(employerService.getMyVacancies());
    }

    @GetMapping("/vacancies/{id}/applicants")
    @Operation(summary = "Get all applicants for a specific vacancy")
    public ResponseEntity<List<ApplicationResponseDTO>> getVacancyApplicants(@PathVariable Long id) {
        return ResponseEntity.ok(employerService.getVacancyApplicants(id));
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "Get employer dashboard statistics")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(employerService.getDashboardStats());
    }
}
