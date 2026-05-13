-- V1__init_schema.sql

-- 1. Users Table (Authentication & Base Profile)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    phone VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- 2. Companies Table (Employer Profiles)
CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    company_name VARCHAR(255) NOT NULL,
    description TEXT,
    logo_url VARCHAR(255),
    website VARCHAR(255),
    address VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- 3. Resumes Table (Candidate Profiles)
CREATE TABLE resumes (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255),
    birth_date DATE,
    gender VARCHAR(20),
    region VARCHAR(50),
    district VARCHAR(50),
    job_title VARCHAR(255) NOT NULL,
    job_description TEXT,
    professional_level VARCHAR(50),
    expected_salary DECIMAL(19, 2),
    employment_type VARCHAR(50),
    work_mode VARCHAR(50),
    status VARCHAR(50) DEFAULT 'OPEN',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- 4. Vacancies Table (Job Postings)
CREATE TABLE vacancies (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL REFERENCES users(id), -- Or references companies(id) based on final structure
    title VARCHAR(255) NOT NULL,
    description TEXT,
    region_id BIGINT,
    district_id BIGINT,
    min_salary DECIMAL(19, 2),
    max_salary DECIMAL(19, 2),
    experience_level VARCHAR(50),
    education_level VARCHAR(50),
    employment_type VARCHAR(50),
    work_mode VARCHAR(50),
    for_students BOOLEAN DEFAULT FALSE,
    for_graduates BOOLEAN DEFAULT FALSE,
    for_disabled BOOLEAN DEFAULT FALSE,
    for_orphans BOOLEAN DEFAULT FALSE,
    language VARCHAR(255),
    gender VARCHAR(20),
    social_category VARCHAR(50),
    min_age INTEGER,
    max_age INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- 5. Applications Table (Tracking)
CREATE TABLE applications (
    id BIGSERIAL PRIMARY KEY,
    vacancy_id BIGINT NOT NULL REFERENCES vacancies(id),
    resume_id BIGINT NOT NULL REFERENCES resumes(id),
    status VARCHAR(50) DEFAULT 'PENDING',
    cover_letter TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- 6. Nested Entities for Resumes
CREATE TABLE experiences (
    id BIGSERIAL PRIMARY KEY,
    resume_id BIGINT NOT NULL REFERENCES resumes(id),
    company_name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE educations (
    id BIGSERIAL PRIMARY KEY,
    resume_id BIGINT NOT NULL REFERENCES resumes(id),
    institution VARCHAR(255) NOT NULL,
    degree VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

-- Essential Indexes for High-Performance Search
CREATE INDEX idx_vacancies_is_deleted ON vacancies(is_deleted);
CREATE INDEX idx_vacancies_search ON vacancies(region_id, district_id, employment_type, work_mode);
CREATE INDEX idx_resumes_is_deleted ON resumes(is_deleted);
CREATE INDEX idx_applications_vacancy ON applications(vacancy_id);
CREATE INDEX idx_applications_resume ON applications(resume_id);
