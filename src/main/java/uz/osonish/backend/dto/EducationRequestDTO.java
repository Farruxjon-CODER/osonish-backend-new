package uz.osonish.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequestDTO {
    @Schema(example = "Tashkent University of Information Technologies")
    private String institution;

    @Schema(example = "Bachelor of Computer Science")
    private String degree;

    @Schema(example = "2016-09-01")
    private LocalDate startDate;

    @Schema(example = "2020-06-30")
    private LocalDate endDate;
}
