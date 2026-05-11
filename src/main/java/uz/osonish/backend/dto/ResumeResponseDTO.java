package uz.osonish.backend.dto;

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
public class ResumeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String gender;
    private Region region;
    private String district;
    private String jobTitle;
    private String jobDescription;
    private String professionalLevel;
    private BigDecimal expectedSalary;
    private EmploymentType employmentType;
    private WorkMode workMode;
    private ResumeStatus status;
    private Long userId;
    private List<ExperienceResponseDTO> experiences;
    private List<EducationResponseDTO> educations;
}
