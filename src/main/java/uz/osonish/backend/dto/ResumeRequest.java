package uz.osonish.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeRequest {
    private String title;
    private String category;
    private String experience;
    private String education;
    private String workExperience;
    private BigDecimal expectedSalary;
    private List<String> skills;
}
