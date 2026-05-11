package uz.osonish.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyRequestDTO {
    @NotBlank
    @Schema(example = "Senior Java Developer")
    private String title;

    @Schema(example = "We are looking for an experienced Java developer...")
    private String description;

    @Schema(example = "1")
    private Long regionId;

    @Schema(example = "10")
    private Long districtId;

    @Schema(example = "2000")
    private BigDecimal minSalary;

    @Schema(example = "4000")
    private BigDecimal maxSalary;

    @Schema(example = "FROM_1_TO_3_YEARS")
    private ExperienceLevel experienceLevel;

    @Schema(example = "BACHELOR")
    private EducationLevel educationLevel;

    @Schema(example = "FULL_TIME")
    private EmploymentType employmentType;

    @Schema(example = "HYBRID")
    private WorkMode workMode;

    @Schema(example = "false")
    private boolean forStudents;

    @Schema(example = "true")
    private boolean forGraduates;

    @Schema(example = "true")
    private boolean forDisabled;

    @Schema(example = "false")
    private boolean forOrphans;

    @Schema(example = "Uzbek/English")
    private String language;

    @Schema(example = "ANY")
    private Gender gender;

    @Schema(example = "LOW_INCOME_FAMILIES")
    private SocialCategory socialCategory;

    @Schema(example = "22")
    private Integer minAge;

    @Schema(example = "45")
    private Integer maxAge;
}
