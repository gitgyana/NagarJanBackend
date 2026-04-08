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


-- INBOUND INGESTION

CREATE TABLE grievances_raw
(
    raw_id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    source                ENUM ('APP','EMAIL','IVR','SOCIAL') NOT NULL,
    external_reference_id VARCHAR(100),
    raw_content           TEXT,
    media_text_content    LONGTEXT,
    timestamp             DATETIME,
    location_id           BIGINT,
    status                ENUM ('INGESTED','PROCESSING','FAILED') DEFAULT 'INGESTED',
    FOREIGN KEY (location_id) REFERENCES locations (location_id),
    INDEX idx_source_time (source, timestamp)
);

CREATE TABLE grievances
(
    grievance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    raw_id       BIGINT,
    title        VARCHAR(255),
    description  TEXT,
    citizen_id   BIGINT NULL,
    location_id  BIGINT,
    created_at   TIMESTAMP                              DEFAULT CURRENT_TIMESTAMP,
    status       ENUM ('OPEN','IN_PROGRESS','RESOLVED') DEFAULT 'OPEN',
    FOREIGN KEY (raw_id) REFERENCES grievances_raw (raw_id),
    FOREIGN KEY (citizen_id) REFERENCES users (user_id),
    FOREIGN KEY (location_id) REFERENCES locations (location_id),
    INDEX idx_status (status),
    INDEX idx_location (location_id)
);


-- DEDUPLICATION

CREATE TABLE grievance_duplicates
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    grievance_id     BIGINT,
    duplicate_of     BIGINT,
    similarity_score DECIMAL(5, 2),
    method           ENUM ('AI','RULE_BASED'),
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id),
    FOREIGN KEY (duplicate_of) REFERENCES grievances (grievance_id),
    INDEX idx_dup (grievance_id, duplicate_of)
);

CREATE TABLE grievance_clusters
(
    cluster_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    primary_grievance_id BIGINT,
    cluster_size         INT       DEFAULT 1,
    created_at           TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (primary_grievance_id) REFERENCES grievances (grievance_id)
);

CREATE TABLE cluster_mapping
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    cluster_id   BIGINT,
    grievance_id BIGINT,
    FOREIGN KEY (cluster_id) REFERENCES grievance_clusters (cluster_id),
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id),
    UNIQUE KEY uniq_cluster (cluster_id, grievance_id)
);


-- AI CLASSIFICATION

CREATE TABLE categories
(
    category_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(150) NOT NULL,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES departments (department_id)
);

CREATE TABLE grievance_classification
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    grievance_id     BIGINT,
    category_id      BIGINT,
    confidence_score DECIMAL(5, 2),
    model_version    VARCHAR(50),
    classified_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status           ENUM ('AUTO','MANUAL_REVIEW'),
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id),
    INDEX idx_confidence (confidence_score)
);

CREATE TABLE classification_logs
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    grievance_id     BIGINT,
    raw_output       JSON,
    model_latency_ms INT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id)
);


-- PRIORITY ENGINE

CREATE TABLE priority_scores
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    grievance_id     BIGINT,
    severity_score   DECIMAL(5, 2),
    recurrence_score DECIMAL(5, 2),
    age_score        DECIMAL(5, 2),
    density_score    DECIMAL(5, 2),
    final_score      DECIMAL(6, 2),
    computed_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id),
    INDEX idx_priority (final_score DESC)
);

CREATE TABLE priority_factors
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    grievance_id BIGINT,
    factor_name  ENUM ('AGE','DENSITY','RECURRENCE','SEVERITY'),
    factor_value DECIMAL(5, 2),
    weight       DECIMAL(5, 2),
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id)
);


-- WORK ORDERS

CREATE TABLE work_orders
(
    work_order_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    grievance_id     BIGINT,
    priority_score   DECIMAL(6, 2),
    assigned_team_id BIGINT,
    status           ENUM ('ASSIGNED','IN_PROGRESS','CLOSED') DEFAULT 'ASSIGNED',
    assigned_at      TIMESTAMP                                DEFAULT CURRENT_TIMESTAMP,
    completed_at     TIMESTAMP NULL,
    FOREIGN KEY (grievance_id) REFERENCES grievances (grievance_id),
    FOREIGN KEY (assigned_team_id) REFERENCES teams (team_id),
    INDEX idx_status (status),
    INDEX idx_team (assigned_team_id)
);

CREATE TABLE work_order_logs
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_order_id BIGINT,
    action        VARCHAR(255),
    remarks       TEXT,
    updated_by    BIGINT,
    timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (work_order_id) REFERENCES work_orders (work_order_id),
    FOREIGN KEY (updated_by) REFERENCES users (user_id)
);


-- ANALYTICS / DENSITY

CREATE TABLE area_statistics
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_id         BIGINT,
    active_grievances   INT,
    resolved_grievances INT,
    avg_resolution_time FLOAT,
    last_updated        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (location_id) REFERENCES locations (location_id)
);

CREATE TABLE heatmap_data
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_id    BIGINT,
    category_id    BIGINT,
    incident_count INT,
    density_score  DECIMAL(6, 2),
    FOREIGN KEY (location_id) REFERENCES locations (location_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

SET FOREIGN_KEY_CHECKS = 1;