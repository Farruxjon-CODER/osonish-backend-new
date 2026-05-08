package uz.osonish.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.osonish.backend.dto.ResumeRequestDTO;
import uz.osonish.backend.dto.ResumeResponseDTO;
import uz.osonish.backend.service.ResumeService;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
@Tag(name = "Resumes", description = "Endpoints for managing resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new resume")
    public ResponseEntity<ResumeResponseDTO> create(@Valid @RequestBody ResumeRequestDTO request) {
        return ResponseEntity.ok(resumeService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all resumes")
    public ResponseEntity<List<ResumeResponseDTO>> getAll() {
        return ResponseEntity.ok(resumeService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get resume by id")
    public ResponseEntity<ResumeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update an existing resume")
    public ResponseEntity<ResumeResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody ResumeRequestDTO request) {
        return ResponseEntity.ok(resumeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Delete a resume")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resumeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
