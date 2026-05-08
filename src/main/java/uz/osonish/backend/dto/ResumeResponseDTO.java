package uz.osonish.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.ResumeCategory;
import uz.osonish.backend.enums.ResumeStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDTO {
    private Long id;
    private String fullName;
    private String title;
    private String summary;
    private List<String> skills;
    private String experience;
    private String education;
    private ResumeCategory category;
    private BigDecimal expectedSalary;
    private ResumeStatus status;
    private Long userId;
}
