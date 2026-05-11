package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.osonish.backend.dto.PublicStatsDTO;
import uz.osonish.backend.enums.Role;
import uz.osonish.backend.repository.ResumeRepository;
import uz.osonish.backend.repository.UserRepository;
import uz.osonish.backend.repository.VacancyRepository;

@Service
@RequiredArgsConstructor
public class PublicService {

    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public PublicStatsDTO getPublicStats() {
        return PublicStatsDTO.builder()
                .vacancyCount(vacancyRepository.count())
                .resumeCount(resumeRepository.count())
                .companyCount(userRepository.countByRole(Role.ROLE_EMPLOYER))
                .build();
    }
}
