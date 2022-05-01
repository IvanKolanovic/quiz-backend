CREATE TABLE participants
(
    id         BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
    user_id    BIGINT UNSIGNED NOT NULL,
    user_score int unsigned NOT NULL DEFAULT 0,
    game_id    BIGINT UNSIGNED NOT NULL,

    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (user_id) REFERENCES user (id)

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

ALTER TABLE game DROP FOREIGN KEY game_ibfk_2;
ALTER TABLE game DROP FOREIGN KEY game_ibfk_1;
ALTER TABLE game DROP COLUMN player_one;
ALTER TABLE game DROP COLUMN player_two;
ALTER TABLE game DROP COLUMN player_one_score;
ALTER TABLE game DROP COLUMN player_two_score;

UPDATE user
SET active = true;
