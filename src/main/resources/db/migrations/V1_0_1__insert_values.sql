-- Insert Ranks
INSERT INTO rank (`name`, `level`)
VALUES ('Beginner', 1);
INSERT INTO rank (`name`, `level`)
VALUES ('Amateur', 2);
INSERT INTO rank (`name`, `level`)
VALUES ('Semi-Pro', 3);
INSERT INTO rank (`name`, `level`)
VALUES ('Professional', 4);
INSERT INTO rank (`name`, `level`)
VALUES ('World Class', 5);
INSERT INTO rank (`name`, `level`)
VALUES ('Legendary', 6);
INSERT INTO rank (`name`, `level`)
VALUES ('Legendary', 7);

-- Insert statistics for created users
INSERT INTO user_statistics (total_points,
                             games_won,
                             rank_id)
VALUES (DEFAULT, DEFAULT, DEFAULT);
INSERT INTO user_statistics (total_points,
                             games_won,
                             rank_id)
VALUES (DEFAULT, DEFAULT, DEFAULT);
INSERT INTO user_statistics (total_points,
                             games_won,
                             rank_id)
VALUES (DEFAULT, DEFAULT, DEFAULT);

-- Insert Users
INSERT INTO user (email, password, roles, first_name, last_name, user_statistics_id)
VALUES ('admin@rit.com', '$2a$10$.xaYSfiMMA1mWtZcNe9HjODlWtaOBoq6azpoEy445YFX8alxHPJJC', 'ROLE_ADMIN', 'Ivan',
        'Kolanovic', 1);
INSERT INTO user (email, password, roles, first_name, last_name, user_statistics_id)
VALUES ('mili@rit.com', '$2a$10$.xaYSfiMMA1mWtZcNe9HjODlWtaOBoq6azpoEy445YFX8alxHPJJC', 'ROLE_USER', 'Marko',
        'Milic Bucevic', 2);
INSERT INTO user (email, password, roles, first_name, last_name, user_statistics_id)
VALUES ('juric@rit.com', '$2a$10$.xaYSfiMMA1mWtZcNe9HjODlWtaOBoq6azpoEy445YFX8alxHPJJC', 'ROLE_USER', 'Marko',
        'Juric', 3);

