package uz.osonish.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.osonish.backend.enums.ResumeCategory;
import uz.osonish.backend.enums.ResumeStatus;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResumeCategory category;

    @Column(name = "full_name")
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @ElementCollection
    @CollectionTable(name = "resume_skills", joinColumns = @JoinColumn(name = "resume_id"))
    @Column(name = "skill")
    private List<String> skills;

    @Column(name = "experience", nullable = false, columnDefinition = "TEXT")
    private String experience;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(name = "expected_salary")
    private BigDecimal expectedSalary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ResumeStatus status = ResumeStatus.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreateStatus() {
        if (status == null) {
            status = ResumeStatus.OPEN;
        }
    }
}
