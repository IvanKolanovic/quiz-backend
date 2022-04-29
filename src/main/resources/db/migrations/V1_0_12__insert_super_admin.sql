INSERT INTO user_statistics (total_points,
                             games_won,
                             rank_id)
VALUES (DEFAULT, DEFAULT, DEFAULT);

-- Insert User
INSERT INTO user (email, password, roles, first_name, last_name, user_statistics_id, active, username, learning_index)
VALUES ('superadmin@rit.com', '$2a$10$.xaYSfiMMA1mWtZcNe9HjODlWtaOBoq6azpoEy445YFX8alxHPJJC', 'ROLE_SUPER_ADMIN', 'SuperAdmin',
        'Superic', 4, 1, "superadmin", 1);
