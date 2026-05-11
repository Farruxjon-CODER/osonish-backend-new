package uz.osonish.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeFullRequestDTO {

    @NotBlank
    @Schema(example = "Farruxjon")
    private String firstName;

    @NotBlank
    @Schema(example = "Yusupjanov")
    private String lastName;

    @Schema(example = "Xamidovich")
    private String middleName;

    @NotBlank
    @Schema(example = "+998777405717")
    private String phone;

    @Email
    @Schema(example = "[EMAIL_ADDRESS]")
    private String email;

    @Schema(example = "1995-05-15")
    private LocalDate birthDate;

    @Schema(example = "Male")
    private String gender;

    @Schema(example = "TASHKENT_CITY")
    private Region region;

    @Schema(example = "Yunusabad")
    private String district;

    @NotBlank
    @Schema(example = "Senior Java Developer")
    private String jobTitle;

    @Schema(example = "Building high-performance APIs and microservices.")
    private String jobDescription;

    @Schema(example = "Senior")
    private String professionalLevel;

    @Schema(example = "3000")
    private BigDecimal expectedSalary;

    @Schema(example = "FULL_TIME")
    private EmploymentType employmentType;

    @Schema(example = "HYBRID")
    private WorkMode workMode;

    private List<ExperienceRequestDTO> experiences;
    private List<EducationRequestDTO> educations;
}
