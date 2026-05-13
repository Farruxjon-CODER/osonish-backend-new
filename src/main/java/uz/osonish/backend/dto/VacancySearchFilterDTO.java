package uz.osonish.backend.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import uz.osonish.backend.enums.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@ParameterObject
public class VacancySearchFilterDTO {
    @Parameter(description = "Qidiruv so'zi (title, description)")
    private String keyword;
    
    private Long regionId;
    private Long districtId;
    
    @Parameter(description = "Kutilayotgan minimal maosh")
    private BigDecimal minSalary;
    
    @Parameter(description = "Kutilayotgan maksimal maosh")
    private BigDecimal maxSalary;
    
    private ExperienceLevel experienceLevel;
    private EducationLevel educationLevel;
    private List<EmploymentType> employmentType;
    private List<WorkMode> workMode;
    
    private Boolean forStudents;
    private Boolean forGraduates;
    private Boolean forDisabled;
    private Boolean forOrphans;
    
    private Gender gender;
    private SocialCategory socialCategory;
    private Integer minAge;
    private Integer maxAge;
    private String language;
}
