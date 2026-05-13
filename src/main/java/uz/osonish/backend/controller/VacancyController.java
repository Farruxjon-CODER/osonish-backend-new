package uz.osonish.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.osonish.backend.dto.VacancyRequestDTO;
import uz.osonish.backend.dto.VacancyResponseDTO;
import uz.osonish.backend.dto.VacancySearchFilterDTO;
import uz.osonish.backend.enums.*;
import uz.osonish.backend.service.VacancyService;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
@Tag(name = "Vacancy", description = "Vakansiyalarni boshqarish")
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYER', 'ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Yangi vakansiya yaratish")
    public ResponseEntity<VacancyResponseDTO> createVacancy(@Valid @RequestBody VacancyRequestDTO request) {
        return ResponseEntity.ok(vacancyService.create(request));
    }

    @GetMapping("/home-search")
    @Operation(summary = "Lightweight search for the Home Page (Keyword + Region)")
    public ResponseEntity<Page<VacancyResponseDTO>> homeSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long regionId,
            @PageableDefault(size = 6) Pageable pageable
    ) {
        return ResponseEntity.ok(vacancyService.homeSearch(keyword, regionId, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Full filtration system with all 10+ parameters using ParameterObject")
    public ResponseEntity<Page<VacancyResponseDTO>> search(
            @Valid VacancySearchFilterDTO filter,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        return ResponseEntity.ok(vacancyService.search(filter, pageable));
    }
}
