package uz.osonish.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.osonish.backend.dto.PublicStatsDTO;
import uz.osonish.backend.service.PublicStatsService;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
@Tag(name = "Public Stats", description = "Publicly accessible endpoints for platform statistics")
public class PublicStatsController {

    private final PublicStatsService publicStatsService;

    @GetMapping("/stats")
    @Operation(summary = "Get platform-wide statistics (Vacancies, Resumes, Companies) - Cached")
    public ResponseEntity<PublicStatsDTO> getStats() {
        return ResponseEntity.ok(publicStatsService.getPublicStats());
    }
}
