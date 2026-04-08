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
