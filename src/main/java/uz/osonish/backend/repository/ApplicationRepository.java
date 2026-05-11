package uz.osonish.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.osonish.backend.entity.Application;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByVacancyId(Long vacancyId);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.vacancy.company.id = :employerId")
    long countByEmployerId(@Param("employerId") Long employerId);
}
