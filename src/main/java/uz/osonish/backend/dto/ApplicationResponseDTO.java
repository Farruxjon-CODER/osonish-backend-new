package uz.osonish.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.osonish.backend.enums.ApplicationStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
    private Long id;
    private Long vacancyId;
    private String vacancyTitle;
    private Long resumeId;
    private String candidateName;
    private ApplicationStatus status;
    private String coverLetter;
}
