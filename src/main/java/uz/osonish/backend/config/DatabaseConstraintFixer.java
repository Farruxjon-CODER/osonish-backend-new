package uz.osonish.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseConstraintFixer {

    private final JdbcTemplate jdbcTemplate;

    /**
     * This listener runs automatically when the application is ready.
     * It ensures that legacy database constraints (like 'users_role_check')
     * are dropped so that the new Role enum values (ROLE_USER, ROLE_ADMIN)
     * can be inserted without conflicts.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void fixConstraints() {
        log.info("Checking for legacy database constraints...");
        try {
            // Drop the constraint if it exists to allow the new ROLE_ prefix values
            jdbcTemplate.execute("ALTER TABLE users DROP CONSTRAINT IF EXISTS users_role_check");
            log.info("Constraint 'users_role_check' dropped successfully (if it existed).");

            // Drop legacy 'skills' column from resumes table if it exists
            jdbcTemplate.execute("ALTER TABLE resumes DROP COLUMN IF EXISTS skills");
            log.info("Legacy column 'skills' dropped from 'resumes' table.");

            // Drop legacy status constraint if it exists
            jdbcTemplate.execute("ALTER TABLE resumes DROP CONSTRAINT IF EXISTS chk_resumes_status");
            jdbcTemplate.execute("ALTER TABLE resumes DROP CONSTRAINT IF EXISTS resumes_status_check");
            jdbcTemplate.execute("ALTER TABLE resumes DROP CONSTRAINT IF EXISTS resumes_role_check");
            log.info("Check constraints dropped successfully.");

            // Make columns nullable for testing flexibility
            jdbcTemplate.execute("ALTER TABLE resumes ALTER COLUMN experience DROP NOT NULL");
            jdbcTemplate.execute("ALTER TABLE resumes ALTER COLUMN category DROP NOT NULL");
            jdbcTemplate.execute("ALTER TABLE resumes ALTER COLUMN expected_salary DROP NOT NULL");
            log.info("Columns 'experience', 'category' and 'expected_salary' set to NULLABLE.");
            
            // Optional: Synchronize existing data if any 'USER' or 'ADMIN' values remain
            jdbcTemplate.execute("UPDATE users SET role = 'ROLE_USER' WHERE role = 'USER'");
            jdbcTemplate.execute("UPDATE users SET role = 'ROLE_ADMIN' WHERE role = 'ADMIN'");
            log.info("Existing roles synchronized to include 'ROLE_' prefix.");
            
        } catch (Exception e) {
            log.warn("Could not modify database constraints/data: {}", e.getMessage());
        }
    }
}
