package uz.osonish.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.ResumeStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeResponse {
    private Long id;
    private String title;
    private String category;
    private String experience;
    private String education;
    private String workExperience;
    private BigDecimal expectedSalary;
    private ResumeStatus status;
    private LocalDateTime createdAt;
}
