package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import uz.osonish.backend.dto.VacancyRequestDTO;
import uz.osonish.backend.dto.VacancyResponseDTO;
import uz.osonish.backend.entity.User;
import uz.osonish.backend.entity.Vacancy;
import uz.osonish.backend.enums.*;
import uz.osonish.backend.repository.UserRepository;
import uz.osonish.backend.repository.VacancyRepository;
import uz.osonish.backend.repository.VacancySpecification;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;

    public Page<VacancyResponseDTO> search(
            String keyword,
            Long regionId,
            Long districtId,
            BigDecimal minSalary,
            BigDecimal maxSalary,
            ExperienceLevel experienceLevel,
            EducationLevel educationLevel,
            List<EmploymentType> employmentTypes,
            List<WorkMode> workModes,
            Boolean forStudents,
            Boolean forGraduates,
            Boolean forDisabled,
            Boolean forOrphans,
            Gender gender,
            SocialCategory socialCategory,
            Integer minAge,
            Integer maxAge,
            String language,
            Pageable pageable
    ) {
        Specification<Vacancy> spec = VacancySpecification.filter(
                keyword, regionId, districtId, minSalary, maxSalary,
                experienceLevel, educationLevel, employmentTypes, workModes,
                forStudents, forGraduates, forDisabled, forOrphans, gender, socialCategory,
                minAge, maxAge, language
        );

        return vacancyRepository.findAll(spec, pageable).map(this::mapToResponse);
    }

    public VacancyResponseDTO create(VacancyRequestDTO request) {
        String phone = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User company = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
                .forOrphans(request.isForOrphans())
                .gender(request.getGender())
                .socialCategory(request.getSocialCategory())
                .minAge(request.getMinAge())
                .maxAge(request.getMaxAge())
                .language(request.getLanguage())
                .company(company)
                .build();

        return mapToResponse(vacancyRepository.save(vacancy));
    }

    public Page<VacancyResponseDTO> homeSearch(String keyword, Long regionId, Pageable pageable) {
        return search(keyword, regionId, null, null, null, null, null, null, null, 
                null, null, null, null, null, null, null, null, null, pageable);
    }

    private VacancyResponseDTO mapToResponse(Vacancy vacancy) {
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
                .forOrphans(vacancy.isForOrphans())
                .gender(vacancy.getGender())
                .socialCategory(vacancy.getSocialCategory())
                .minAge(vacancy.getMinAge())
                .maxAge(vacancy.getMaxAge())
                .language(vacancy.getLanguage())
                .companyId(vacancy.getCompany() != null ? vacancy.getCompany().getId() : null)
                .build();
    }
}
