package uz.osonish.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Telefon raqami bo'sh bo'lmasligi kerak")
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Telefon raqami +998XXXXXXXXX formatida bo'lishi kerak")
    private String phone;

    @NotBlank(message = "Parol bo'sh bo'lmasligi kerak")
    @Size(min = 6, message = "Parol kamida 6 ta belgidan iborat bo'lishi kerak")
    private String password;

    @NotBlank(message = "To'liq ism bo'sh bo'lmasligi kerak")
    private String fullName;
}
