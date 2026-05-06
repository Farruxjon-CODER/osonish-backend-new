package uz.osonish.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.osonish.backend.entity.Resume;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUserId(Long userId);
}
