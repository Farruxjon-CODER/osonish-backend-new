package uz.osonish.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public LocalFileStorageServiceImpl(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            originalFileName = "unknown";
        }
        originalFileName = StringUtils.cleanPath(originalFileName);
        String fileExtension = "";
        
        if (originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        try {
            if (newFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + newFileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/files/" + newFileName; // Assuming a WebMvcConfigurer maps /files/** to the uploads directory
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + newFileName + ". Please try again!", ex);
        }
    }

    @Override
    public void validateFile(MultipartFile file, String... allowedTypes) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds 5MB limit");
        }
        
        String contentType = file.getContentType();
        boolean isAllowed = Arrays.asList(allowedTypes).contains(contentType);
        
        if (!isAllowed) {
            throw new IllegalArgumentException("Invalid file type. Allowed types are: " + Arrays.toString(allowedTypes));
        }
    }
}
