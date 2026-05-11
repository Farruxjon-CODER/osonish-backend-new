package uz.osonish.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceRequestDTO {
    @Schema(example = "Google")
    private String companyName;

    @Schema(example = "Senior Software Engineer")
    private String position;

    @Schema(example = "2020-01-01")
    private LocalDate startDate;

    @Schema(example = "2023-12-31")
    private LocalDate endDate;

    @Schema(example = "Developed scalable microservices using Spring Boot.")
    private String description;
}
