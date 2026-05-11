package uz.osonish.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.osonish.backend.dto.PublicStatsDTO;
import uz.osonish.backend.service.PublicService;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "Public API", description = "Publicly accessible endpoints for platform statistics")
public class PublicController {

    private final PublicService publicService;

    @GetMapping("/stats")
    @Operation(summary = "Get platform-wide statistics (Vacancies, Resumes, Companies)")
    public ResponseEntity<PublicStatsDTO> getPublicStats() {
        return ResponseEntity.ok(publicService.getPublicStats());
    }
}
