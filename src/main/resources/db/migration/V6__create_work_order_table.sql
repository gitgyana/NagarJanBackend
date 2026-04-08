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
