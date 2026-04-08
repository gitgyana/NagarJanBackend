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
