package uz.osonish.backend.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.osonish.backend.entity.Vacancy;
import java.util.ArrayList;
import java.util.List;

import uz.osonish.backend.dto.VacancySearchFilterDTO;

public class VacancySpecification {

    public static Specification<Vacancy> filter(VacancySearchFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
                String pattern = "%" + filter.getKeyword().toLowerCase() + "%";
                Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);
                Predicate descPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern);
                predicates.add(criteriaBuilder.or(titlePredicate, descPredicate));
            }

            if (filter.getRegionId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("regionId"), filter.getRegionId()));
            }
            if (filter.getDistrictId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("districtId"), filter.getDistrictId()));
            }

            // Salary Overlap Logic: Vacancy max >= User min AND Vacancy min <= User max
            if (filter.getMinSalary() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxSalary"), filter.getMinSalary()));
            }
            if (filter.getMaxSalary() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("minSalary"), filter.getMaxSalary()));
            }

            if (filter.getExperienceLevel() != null) {
                predicates.add(criteriaBuilder.equal(root.get("experienceLevel"), filter.getExperienceLevel()));
            }
            if (filter.getEducationLevel() != null) {
                predicates.add(criteriaBuilder.equal(root.get("educationLevel"), filter.getEducationLevel()));
            }

            // Multi-select logic
            if (filter.getEmploymentType() != null && !filter.getEmploymentType().isEmpty()) {
                predicates.add(root.get("employmentType").in(filter.getEmploymentType()));
            }
            if (filter.getWorkMode() != null && !filter.getWorkMode().isEmpty()) {
                predicates.add(root.get("workMode").in(filter.getWorkMode()));
            }

            if (filter.getForStudents() != null) {
                predicates.add(criteriaBuilder.equal(root.get("forStudents"), filter.getForStudents()));
            }
            if (filter.getForGraduates() != null) {
                predicates.add(criteriaBuilder.equal(root.get("forGraduates"), filter.getForGraduates()));
            }
            if (filter.getForDisabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("forDisabled"), filter.getForDisabled()));
            }
            if (filter.getForOrphans() != null) {
                predicates.add(criteriaBuilder.equal(root.get("forOrphans"), filter.getForOrphans()));
            }

            if (filter.getGender() != null) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), filter.getGender()));
            }
            if (filter.getSocialCategory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("socialCategory"), filter.getSocialCategory()));
            }

            if (filter.getMinAge() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("minAge"), filter.getMinAge()));
            }
            if (filter.getMaxAge() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxAge"), filter.getMaxAge()));
            }
            if (filter.getLanguage() != null && !filter.getLanguage().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("language"), filter.getLanguage()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
