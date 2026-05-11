package uz.osonish.backend.dto;

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
public class VacancyResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Long regionId;
    private Long districtId;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private ExperienceLevel experienceLevel;
    private EducationLevel educationLevel;
    private EmploymentType employmentType;
    private WorkMode workMode;
    private boolean forStudents;
    private boolean forGraduates;
    private boolean forDisabled;
    private boolean forOrphans;
    private String language;
    private Gender gender;
    private SocialCategory socialCategory;
    private Integer minAge;
    private Integer maxAge;
    private Long companyId;
}
