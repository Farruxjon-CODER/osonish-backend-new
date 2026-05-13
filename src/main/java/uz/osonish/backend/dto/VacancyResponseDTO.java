package uz.osonish.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyResponseDTO {
    private Long id;
    private String title;
    private String description;
    
    // Geographic
    private Long regionId;
    private Long districtId;
    
    // Financial
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    
    // Professional
    private ExperienceLevel experienceLevel;
    private EducationLevel educationLevel;
    private EmploymentType employmentType;
    private WorkMode workMode;
    
    // Social Toggles
    private boolean forStudents;
    private boolean forGraduates;
    private boolean forDisabled;
    private boolean forOrphans;
    
    private Gender gender;
    private SocialCategory socialCategory;
    private Integer minAge;
    private Integer maxAge;
    private String language;
    
    // Audit / Time
    private LocalDateTime createdAt;
    
    // Company Profile Data (From User or Company Entity)
    private Long companyId;
    private String companyName;
    private String companyLogo; // Used by frontend for professional display
}
