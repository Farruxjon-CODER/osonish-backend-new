package uz.osonish.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.ResumeStatus;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponseDTO {
    private Long id;
    private String title;
    private String category;
    private String workExperience;
    private String education;
    private BigDecimal expectedSalary;
    private ResumeStatus status;
    private Long userId;
}
