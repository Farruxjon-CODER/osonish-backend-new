package uz.osonish.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.osonish.backend.enums.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vacancies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacancy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Location
    private Long regionId;
    private Long districtId;

    // Salary
    @Column(name = "min_salary")
    private BigDecimal minSalary;

    @Column(name = "max_salary")
    private BigDecimal maxSalary;

    // Requirements
    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level")
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private EducationLevel educationLevel;

    // Types
    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type")
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode")
    private WorkMode workMode;

    // Audience
    @Column(name = "for_students")
    private boolean forStudents;

    @Column(name = "for_graduates")
    private boolean forGraduates;

    @Column(name = "for_disabled")
    private boolean forDisabled;

    @Column(name = "for_orphans")
    private boolean forOrphans;

    private String language;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_category")
    private SocialCategory socialCategory;

    // Age
    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private User company; // Assuming Company is represented by a User with ROLE_COMPANY
}
