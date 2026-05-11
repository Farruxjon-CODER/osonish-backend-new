package uz.osonish.backend.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import uz.osonish.backend.entity.Vacancy;
import uz.osonish.backend.enums.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VacancySpecification {

    public static Specification<Vacancy> filter(
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
            String language
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern);
                Predicate descPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern);
                predicates.add(criteriaBuilder.or(titlePredicate, descPredicate));
            }

            if (regionId != null) {
                predicates.add(criteriaBuilder.equal(root.get("regionId"), regionId));
            }
            if (districtId != null) {
                predicates.add(criteriaBuilder.equal(root.get("districtId"), districtId));
            }

            // Salary Overlap Logic
            if (minSalary != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxSalary"), minSalary));
            }
            if (maxSalary != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("minSalary"), maxSalary));
            }

            if (experienceLevel != null) {
                predicates.add(criteriaBuilder.equal(root.get("experienceLevel"), experienceLevel));
            }
            if (educationLevel != null) {
                predicates.add(criteriaBuilder.equal(root.get("educationLevel"), educationLevel));
            }

            // Multi-select logic
            if (employmentTypes != null && !employmentTypes.isEmpty()) {
                predicates.add(root.get("employmentType").in(employmentTypes));
            }
            if (workModes != null && !workModes.isEmpty()) {
                predicates.add(root.get("workMode").in(workModes));
            }

            if (forStudents != null) {
                predicates.add(criteriaBuilder.equal(root.get("forStudents"), forStudents));
            }
            if (forGraduates != null) {
                predicates.add(criteriaBuilder.equal(root.get("forGraduates"), forGraduates));
            }
            if (forDisabled != null) {
                predicates.add(criteriaBuilder.equal(root.get("forDisabled"), forDisabled));
            }
            if (forOrphans != null) {
                predicates.add(criteriaBuilder.equal(root.get("forOrphans"), forOrphans));
            }

            if (gender != null) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }
            if (socialCategory != null) {
                predicates.add(criteriaBuilder.equal(root.get("socialCategory"), socialCategory));
            }

            if (minAge != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("minAge"), minAge));
            }
            if (maxAge != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxAge"), maxAge));
            }
            if (language != null && !language.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("language"), language));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
