CREATE TABLE question
(
    id      BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
    abbr    varchar(255) NOT NULL,
    game_id BIGINT UNSIGNED NOT NULL,

    FOREIGN KEY (game_id) REFERENCES game (id)

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE question_choices
(
    id          BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
    question_id BIGINT UNSIGNED NOT NULL,
    choice      BIGINT UNSIGNED NOT NULL,
    is_right    tinyint(1) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question (id),
    FOREIGN KEY (choice) REFERENCES country (id)

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE user_answer
(
    id          BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
    user_id     BIGINT UNSIGNED NOT NULL,
    question_id BIGINT UNSIGNED NOT NULL,
    choice_id   BIGINT UNSIGNED NOT NULL,
    is_right    tinyint(1) NOT NULL,
    answer_time timestamp default CURRENT_TIMESTAMP,
    game_id     BIGINT UNSIGNED NOT NULL,

    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (question_id) REFERENCES question (id),
    FOREIGN KEY (choice_id) REFERENCES question_choices (id),
    FOREIGN KEY (user_id) REFERENCES user (id)

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

ALTER TABLE game MODIFY player_one bigint unsigned DEFAULT NULL;
ALTER TABLE game MODIFY player_two bigint unsigned DEFAULT NULL;
