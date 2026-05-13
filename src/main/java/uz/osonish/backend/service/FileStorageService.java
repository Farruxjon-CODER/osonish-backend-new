package uz.osonish.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file);
    void validateFile(MultipartFile file, String... allowedTypes);
}
