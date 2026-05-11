package uz.osonish.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.osonish.backend.entity.Vacancy;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {
    List<Vacancy> findByCompanyId(Long companyId);
    long countByCompanyId(Long companyId);
}
