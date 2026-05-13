package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.osonish.backend.dto.VacancyRequestDTO;
import uz.osonish.backend.dto.VacancyResponseDTO;
import uz.osonish.backend.dto.VacancySearchFilterDTO; // Import joyiga qaytarildi
import uz.osonish.backend.entity.User;
import uz.osonish.backend.entity.Vacancy;
import uz.osonish.backend.mapper.VacancyMapper; // Mapper qo'shildi
import uz.osonish.backend.repository.UserRepository;
import uz.osonish.backend.repository.VacancyRepository;
import uz.osonish.backend.repository.VacancySpecification;
import uz.osonish.backend.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;
    private final VacancyMapper vacancyMapper; // MapStruct mapper

    @Transactional(readOnly = true)
    public Page<VacancyResponseDTO> search(VacancySearchFilterDTO filter, Pageable pageable) {
        Specification<Vacancy> spec = VacancySpecification.filter(filter);
        return vacancyRepository.findAll(spec, pageable)
                .map(vacancyMapper::toResponseDto); // Manual map o'rniga professional mapper
    }

    @Transactional
    public VacancyResponseDTO create(VacancyRequestDTO request) {
        String phone = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();
        
        User company = userRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi: " + phone));

        // Builder orqali yaratish (Manual mapping Mapper ichiga ko'chirilishi ham mumkin)
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

        return vacancyMapper.toResponseDto(vacancyRepository.save(vacancy));
    }

    @Transactional(readOnly = true)
    public Page<VacancyResponseDTO> homeSearch(String keyword, Long regionId, Pageable pageable) {
        VacancySearchFilterDTO filter = new VacancySearchFilterDTO();
        filter.setKeyword(keyword);
        filter.setRegionId(regionId);
        return search(filter, pageable);
    }
}