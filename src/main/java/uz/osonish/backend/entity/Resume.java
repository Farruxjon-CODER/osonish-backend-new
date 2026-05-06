package uz.osonish.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.osonish.backend.enums.ResumeStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(name = "work_experience", columnDefinition = "TEXT")
    private String workExperience;

    @Column(columnDefinition = "TEXT")
    private String education;

    @Column(name = "expected_salary")
    private BigDecimal expectedSalary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResumeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = ResumeStatus.OPEN;
        }
    }
}
