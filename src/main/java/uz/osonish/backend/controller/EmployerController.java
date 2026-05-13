package uz.osonish.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.osonish.backend.service.FileStorageService;

import java.util.Map;

@RestController
@RequestMapping("/api/employer")
@RequiredArgsConstructor
@Tag(name = "Employer Settings", description = "Endpoints for employers to manage their profile and settings")
public class EmployerController {

    private final FileStorageService fileStorageService;

    // In a real application, you would also inject UserService/CompanyService to save the logo URL to the database
    // private final CompanyService companyService;

    @PostMapping("/logo")
    @PreAuthorize("hasRole('EMPLOYER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Upload or update company logo (Max 5MB, PNG/JPG)")
    public ResponseEntity<Map<String, String>> uploadLogo(@RequestParam("file") MultipartFile file) {
        
        // 1. Validate the file size and type
        fileStorageService.validateFile(file, "image/png", "image/jpeg", "image/jpg");

        // 2. Store the file securely on the server
        String fileUrl = fileStorageService.storeFile(file);

        // 3. Update the company profile in the database
        // companyService.updateCompanyLogo(getCurrentUser(), fileUrl);

        return ResponseEntity.ok(Map.of(
                "message", "Logo successfully uploaded",
                "logoUrl", fileUrl
        ));
    }
}
