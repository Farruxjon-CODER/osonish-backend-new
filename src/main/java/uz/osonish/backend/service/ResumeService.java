package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.osonish.backend.dto.*;
import uz.osonish.backend.entity.*;
import uz.osonish.backend.enums.*;
import uz.osonish.backend.repository.ResumeRepository;
import uz.osonish.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResumeResponseDTO create(ResumeFullRequestDTO request) {
        User user = getCurrentUser();

        Resume resume = Resume.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .region(request.getRegion())
                .district(request.getDistrict())
                .jobTitle(request.getJobTitle())
                .jobDescription(request.getJobDescription())
                .professionalLevel(request.getProfessionalLevel())
                .expectedSalary(request.getExpectedSalary())
                .employmentType(request.getEmploymentType())
                .workMode(request.getWorkMode())
                .user(user)
                .build();

        if (request.getExperiences() != null) {
            request.getExperiences().forEach(expDto -> {
                Experience experience = Experience.builder()
                        .companyName(expDto.getCompanyName())
                        .position(expDto.getPosition())
                        .startDate(expDto.getStartDate())
                        .endDate(expDto.getEndDate())
                        .description(expDto.getDescription())
                        .build();
                resume.addExperience(experience);
            });
        }

        if (request.getEducations() != null) {
            request.getEducations().forEach(eduDto -> {
                Education education = Education.builder()
                        .institution(eduDto.getInstitution())
                        .degree(eduDto.getDegree())
                        .startDate(eduDto.getStartDate())
                        .endDate(eduDto.getEndDate())
                        .build();
                resume.addEducation(education);
            });
        }

        return mapToResponse(resumeRepository.save(resume));
    }

    public ResumeResponseDTO getById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));
        return mapToResponse(resume);
    }

    public List<ResumeResponseDTO> getAll(Region region) {
        List<Resume> resumes;
        if (region != null) {
            resumes = resumeRepository.findByRegion(region);
        } else {
            resumes = resumeRepository.findAll();
        }
        return resumes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResumeResponseDTO update(Long id, ResumeFullRequestDTO request) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));

        resume.setFirstName(request.getFirstName());
        resume.setLastName(request.getLastName());
        resume.setMiddleName(request.getMiddleName());
        resume.setPhone(request.getPhone());
        resume.setEmail(request.getEmail());
        resume.setBirthDate(request.getBirthDate());
        resume.setGender(request.getGender());
        resume.setRegion(request.getRegion());
        resume.setDistrict(request.getDistrict());
        resume.setJobTitle(request.getJobTitle());
        resume.setJobDescription(request.getJobDescription());
        resume.setProfessionalLevel(request.getProfessionalLevel());
        resume.setExpectedSalary(request.getExpectedSalary());
        resume.setEmploymentType(request.getEmploymentType());
        resume.setWorkMode(request.getWorkMode());

        // Update Experiences (Simple approach: clear and re-add)
        resume.getExperiences().clear();
        if (request.getExperiences() != null) {
            request.getExperiences().forEach(expDto -> {
                Experience experience = Experience.builder()
                        .companyName(expDto.getCompanyName())
                        .position(expDto.getPosition())
                        .startDate(expDto.getStartDate())
                        .endDate(expDto.getEndDate())
                        .description(expDto.getDescription())
                        .build();
                resume.addExperience(experience);
            });
        }

        // Update Educations
        resume.getEducations().clear();
        if (request.getEducations() != null) {
            request.getEducations().forEach(eduDto -> {
                Education education = Education.builder()
                        .institution(eduDto.getInstitution())
                        .degree(eduDto.getDegree())
                        .startDate(eduDto.getStartDate())
                        .endDate(eduDto.getEndDate())
                        .build();
                resume.addEducation(education);
            });
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
                .firstName(resume.getFirstName())
                .lastName(resume.getLastName())
                .middleName(resume.getMiddleName())
                .phone(resume.getPhone())
                .email(resume.getEmail())
                .birthDate(resume.getBirthDate())
                .gender(resume.getGender())
                .region(resume.getRegion())
                .district(resume.getDistrict())
                .jobTitle(resume.getJobTitle())
                .jobDescription(resume.getJobDescription())
                .professionalLevel(resume.getProfessionalLevel())
                .expectedSalary(resume.getExpectedSalary())
                .employmentType(resume.getEmploymentType())
                .workMode(resume.getWorkMode())
                .status(resume.getStatus())
                .userId(resume.getUser().getId())
                .experiences(resume.getExperiences().stream()
                        .map(exp -> ExperienceResponseDTO.builder()
                                .id(exp.getId())
                                .companyName(exp.getCompanyName())
                                .position(exp.getPosition())
                                .startDate(exp.getStartDate())
                                .endDate(exp.getEndDate())
                                .description(exp.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .educations(resume.getEducations().stream()
                        .map(edu -> EducationResponseDTO.builder()
                                .id(edu.getId())
                                .institution(edu.getInstitution())
                                .degree(edu.getDegree())
                                .startDate(edu.getStartDate())
                                .endDate(edu.getEndDate())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
