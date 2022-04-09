-- USER
CREATE TABLE user
(
    id         BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY AUTO_INCREMENT,

    email      varchar(255) NOT NULL UNIQUE,
    password   varchar(255) NOT NULL,
    roles      varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

-- Insert Users
INSERT INTO user (email, password, roles, first_name, last_name)
VALUES ('admin@rit.com', '$2a$10$.xaYSfiMMA1mWtZcNe9HjODlWtaOBoq6azpoEy445YFX8alxHPJJC', 'ROLE_ADMIN', 'Ivan',
        'Kolanovic');
INSERT INTO user (email, password, roles, first_name, last_name)
VALUES ('mili@rit.com', '$2a$10$.xaYSfiMMA1mWtZcNe9HjODlWtaOBoq6azpoEy445YFX8alxHPJJC', 'ROLE_USER', 'Marko',
        'Milic Bucevic');
INSERT INTO user (email, password, roles, first_name, last_name)
VALUES ('juric@rit.com', '$2a$10$.xaYSfiMMA1mWtZcNe9HjODlWtaOBoq6azpoEy445YFX8alxHPJJC', 'ROLE_USER', 'Marko',
        'Juric');
