CREATE TABLE users
(
    user_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(150)                        NOT NULL,
    phone      VARCHAR(20),
    email      VARCHAR(150) UNIQUE,
    role       ENUM ('CITIZEN','ADMIN','OPERATOR') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE departments
(
    department_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(150) NOT NULL,
    description    TEXT,
    contact_number VARCHAR(20)
);

CREATE TABLE teams
(
    team_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_id BIGINT,
    team_name     VARCHAR(150) NOT NULL,
    team_contact  VARCHAR(20),
    FOREIGN KEY (department_id) REFERENCES departments (department_id)
);

CREATE TABLE locations
(
    location_id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    area_name          VARCHAR(150),
    ward_number        VARCHAR(50),
    city               VARCHAR(100),
    state              VARCHAR(100),
    latitude           DECIMAL(10, 7),
    longitude          DECIMAL(10, 7),
    population_density INT,
    INDEX idx_geo (latitude, longitude)
);
