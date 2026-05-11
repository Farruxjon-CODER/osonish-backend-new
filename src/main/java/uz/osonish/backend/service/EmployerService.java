package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.osonish.backend.dto.*;
import uz.osonish.backend.entity.*;
import uz.osonish.backend.repository.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployerService {

    private final VacancyRepository vacancyRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Transactional
    public VacancyResponseDTO createVacancy(VacancyRequestDTO request) {
        User company = getCurrentUser();
        
        Vacancy vacancy = Vacancy.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .regionId(request.getRegionId())
                .districtId(request.getDistrictId())
                .minSalary(request.getMinSalary())
                .maxSalary(request.getMaxSalary())
                .experienceLevel(request.getExperienceLevel())
                .educationLevel(request.getEducationLevel())
                .employmentType(request.getEmploymentType())
                .workMode(request.getWorkMode())
                .forStudents(request.isForStudents())
                .forGraduates(request.isForGraduates())
                .forDisabled(request.isForDisabled())
                .gender(request.getGender())
                .socialCategory(request.getSocialCategory())
                .minAge(request.getMinAge())
                .maxAge(request.getMaxAge())
                .company(company)
                .build();

        return mapToVacancyResponse(vacancyRepository.save(vacancy));
    }

    public List<VacancyResponseDTO> getMyVacancies() {
        User company = getCurrentUser();
        return vacancyRepository.findByCompanyId(company.getId()).stream()
                .map(this::mapToVacancyResponse)
                .collect(Collectors.toList());
    }

    public List<ApplicationResponseDTO> getVacancyApplicants(Long vacancyId) {
        User company = getCurrentUser();
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new RuntimeException("Vacancy not found"));

        if (!vacancy.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("Unauthorized to view applicants for this vacancy");
        }

        return applicationRepository.findByVacancyId(vacancyId).stream()
                .map(this::mapToApplicationResponse)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getDashboardStats() {
        User company = getCurrentUser();
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalActiveVacancies", vacancyRepository.countByCompanyId(company.getId()));
        stats.put("totalApplicants", applicationRepository.countByEmployerId(company.getId()));
        return stats;
    }

    private User getCurrentUser() {
        String phone = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private VacancyResponseDTO mapToVacancyResponse(Vacancy vacancy) {
        return VacancyResponseDTO.builder()
                .id(vacancy.getId())
                .title(vacancy.getTitle())
                .description(vacancy.getDescription())
                .regionId(vacancy.getRegionId())
                .districtId(vacancy.getDistrictId())
                .minSalary(vacancy.getMinSalary())
                .maxSalary(vacancy.getMaxSalary())
                .experienceLevel(vacancy.getExperienceLevel())
                .educationLevel(vacancy.getEducationLevel())
                .employmentType(vacancy.getEmploymentType())
                .workMode(vacancy.getWorkMode())
                .forStudents(vacancy.isForStudents())
                .forGraduates(vacancy.isForGraduates())
                .forDisabled(vacancy.isForDisabled())
                .gender(vacancy.getGender())
                .socialCategory(vacancy.getSocialCategory())
                .minAge(vacancy.getMinAge())
                .maxAge(vacancy.getMaxAge())
                .companyId(vacancy.getCompany().getId())
                .build();
    }

    private ApplicationResponseDTO mapToApplicationResponse(Application application) {
        return ApplicationResponseDTO.builder()
                .id(application.getId())
                .vacancyId(application.getVacancy().getId())
                .vacancyTitle(application.getVacancy().getTitle())
                .resumeId(application.getResume().getId())
                .candidateName(application.getResume().getFirstName() + " " + application.getResume().getLastName())
                .status(application.getStatus())
                .coverLetter(application.getCoverLetter())
                .build();
    }
}
