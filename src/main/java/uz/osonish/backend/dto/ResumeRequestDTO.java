package uz.osonish.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.ResumeStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeRequestDTO {

    @NotBlank(message = "To'liq ism bo'sh bo'lmasligi kerak")
    @Schema(example = "Farruxjon Yusupjanov")
    private String fullName;

    @NotBlank(message = "Sarlavha bo'sh bo'lmasligi kerak")
    @Schema(example = "Senior Java Developer")
    private String title;

    @Schema(example = "Experienced backend developer specializing in Spring Boot and Microservices.")
    private String summary;

    @NotEmpty(message = "Kamida bitta ko'nikma bo'lishi kerak")
    @Schema(example = "[\"Java\", \"Spring Boot\", \"PostgreSQL\", \"Docker\"]")
    private List<String> skills;

    @NotBlank(message = "Tajriba bo'sh bo'lmasligi kerak")
    @Schema(example = "5 years of experience at Google and Microsoft.")
    private String experience;

    @Schema(example = "Bachelor's degree in Computer Science from TATU.")
    private String education;

    @NotBlank(message = "Kategoriya bo'sh bo'lmasligi kerak")
    @Schema(example = "IT")
    private String category;

    @NotNull(message = "Kutilayotgan maosh ko'rsatilishi kerak")
    @Schema(example = "3000")
    private BigDecimal expectedSalary;

    @Schema(example = "TASHKENT_CITY")
    private String region;

    private ResumeStatus status;
}
