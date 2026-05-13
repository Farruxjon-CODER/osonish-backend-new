package uz.osonish.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.osonish.backend.entity.Application;
import uz.osonish.backend.entity.Resume;
import uz.osonish.backend.entity.User;
import uz.osonish.backend.entity.Vacancy;
import uz.osonish.backend.enums.ApplicationStatus;
import org.springframework.security.access.AccessDeniedException;
import uz.osonish.backend.exception.ResourceNotFoundException;
import uz.osonish.backend.repository.ApplicationRepository;
import uz.osonish.backend.repository.ResumeRepository;
import uz.osonish.backend.repository.UserRepository;
import uz.osonish.backend.repository.VacancyRepository;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final ApplicationRepository applicationRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    @Transactional
    @PreAuthorize("hasRole('CANDIDATE')")
    public Application applyForJob(Long vacancyId, Long resumeId, String coverLetter) {
        User currentUser = getCurrentUser();
        
        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy topilmadi: " + vacancyId));
                
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume topilmadi: " + resumeId));

        // Check if the resume actually belongs to the current user
        if (!resume.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Siz faqat o'zingizning rezyumeingiz bilan topshira olasiz");
        }

        Application application = Application.builder()
                .vacancy(vacancy)
                .resume(resume)
                .coverLetter(coverLetter)
                .status(ApplicationStatus.PENDING)
                .build();

        return applicationRepository.save(application);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('EMPLOYER', 'ADMIN')")
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {
        User currentUser = getCurrentUser();

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Ariza topilmadi: " + applicationId));

        // Check if the vacancy belongs to the current employer
        Vacancy vacancy = application.getVacancy();
        if (!vacancy.getCompany().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Siz faqat o'zingizning vakansiyalaringizga kelgan arizalarni boshqara olasiz");
        }

        application.setStatus(newStatus);
        return applicationRepository.save(application);
    }

    private User getCurrentUser() {
        String phone = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Foydalanuvchi topilmadi"));
    }
}
