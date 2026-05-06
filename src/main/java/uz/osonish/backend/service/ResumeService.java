package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.osonish.backend.dto.ResumeRequestDTO;
import uz.osonish.backend.dto.ResumeResponseDTO;
import uz.osonish.backend.entity.Resume;
import uz.osonish.backend.entity.User;
import uz.osonish.backend.enums.ResumeStatus;
import uz.osonish.backend.enums.Role;
import uz.osonish.backend.repository.ResumeRepository;
import uz.osonish.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeResponseDTO create(ResumeRequestDTO request) {
        User user = getCurrentUser();

        Resume resume = Resume.builder()
                .title(request.getTitle())
                .category(request.getCategory())
                .workExperience(request.getWorkExperience())
                .education(request.getEducation())
                .expectedSalary(request.getExpectedSalary())
                .status(request.getStatus() != null ? request.getStatus() : ResumeStatus.OPEN)
                .user(user)
                .build();

        return mapToResponse(resumeRepository.save(resume));
    }

    public ResumeResponseDTO getById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));
        return mapToResponse(resume);
    }

    public List<ResumeResponseDTO> getAll() {
        return resumeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ResumeResponseDTO update(Long id, ResumeRequestDTO request) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));

        User currentUser = getCurrentUser();
        if (!resume.getUser().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized to update this resume");
        }

        resume.setTitle(request.getTitle());
        resume.setCategory(request.getCategory());
        resume.setWorkExperience(request.getWorkExperience());
        resume.setEducation(request.getEducation());
        resume.setExpectedSalary(request.getExpectedSalary());
        if (request.getStatus() != null) {
            resume.setStatus(request.getStatus());
        }

        return mapToResponse(resumeRepository.save(resume));
    }

    public void delete(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));

        User currentUser = getCurrentUser();
        if (!resume.getUser().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new RuntimeException("Unauthorized to delete this resume");
        }

        resumeRepository.delete(resume);
    }

    private User getCurrentUser() {
        String phone = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private ResumeResponseDTO mapToResponse(Resume resume) {
        return ResumeResponseDTO.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .category(resume.getCategory())
                .workExperience(resume.getWorkExperience())
                .education(resume.getEducation())
                .expectedSalary(resume.getExpectedSalary())
                .status(resume.getStatus())
                .userId(resume.getUser().getId())
                .build();
    }
}
