package uz.osonish.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Osonish.uz Recruitment Platform API",
                version = "1.0.0",
                description = "Production API Documentation for the Osonish.uz Job Portal. Covers Vacancies, Resumes, and Job Applications.",
                contact = @Contact(
                        name = "Osonish Team",
                        email = "support@osonish.uz",
                        url = "https://osonish.uz"
                )
        ),
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Enter your JWT token to access secured endpoints (Employer or Candidate)."
)
public class SwaggerConfig {
    // Configuration is handled via annotations
}
