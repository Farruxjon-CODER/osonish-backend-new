CREATE TABLE resumes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    work_experience TEXT,
    education TEXT,
    expected_salary DECIMAL(19, 2),
    status VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_resumes_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
