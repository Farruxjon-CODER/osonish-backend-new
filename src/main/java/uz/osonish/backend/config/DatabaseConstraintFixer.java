package uz.osonish.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Component;

// @Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseConstraintFixer {

    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void fixConstraints() {
        log.info("Checking for legacy database constraints...");
        try {
            jdbcTemplate.execute("ALTER TABLE users DROP CONSTRAINT IF EXISTS users_role_check");
            log.info("Constraint 'users_role_check' dropped successfully.");

            jdbcTemplate.execute("ALTER TABLE resumes DROP COLUMN IF EXISTS skills");
            log.info("Legacy column 'skills' dropped from 'resumes' table.");

            jdbcTemplate.execute("ALTER TABLE resumes DROP CONSTRAINT IF EXISTS chk_resumes_status");
            jdbcTemplate.execute("ALTER TABLE resumes DROP CONSTRAINT IF EXISTS resumes_status_check");
            jdbcTemplate.execute("ALTER TABLE resumes DROP CONSTRAINT IF EXISTS resumes_role_check");
            log.info("Check constraints dropped successfully.");
            jdbcTemplate.execute("ALTER TABLE resumes ALTER COLUMN experience DROP NOT NULL");
            jdbcTemplate.execute("ALTER TABLE resumes ALTER COLUMN category DROP NOT NULL");
            jdbcTemplate.execute("ALTER TABLE resumes ALTER COLUMN expected_salary DROP NOT NULL");
            log.info("Columns 'experience', 'category' and 'expected_salary' set to NULLABLE.");
            jdbcTemplate.execute("UPDATE users SET role = 'ROLE_USER' WHERE role = 'USER'");
            jdbcTemplate.execute("UPDATE users SET role = 'ROLE_ADMIN' WHERE role = 'ADMIN'");
            log.info("Existing roles synchronized to include 'ROLE_' prefix.");
            
        } catch (Exception e) {
            log.warn("Could not modify database constraints/data: {}", e.getMessage());
        }
    }
}
