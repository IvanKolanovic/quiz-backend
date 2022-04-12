CREATE TABLE rank
(
    id    BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,

    name  varchar(255) NOT NULL,
    level int(10) NOT NULL

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE user_statistics
(
    id           BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,

    total_points bigint unsigned NOT NULL DEFAULT 0,
    games_won    bigint unsigned NOT NULL DEFAULT 0,
    rank_id      bigint unsigned NOT NULL DEFAULT 1,

    FOREIGN KEY (rank_id) REFERENCES rank (id)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE `user`
(
    id                 BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,

    email              varchar(255) NOT NULL UNIQUE,
    password           varchar(255) NOT NULL,
    roles              varchar(255) NOT NULL,
    first_name         varchar(255) NOT NULL,
    last_name          varchar(255) NOT NULL,
    user_statistics_id bigint unsigned NOT NULL,

    FOREIGN KEY (user_statistics_id) REFERENCES `user_statistics` (id)

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE room
(
    id       BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,

    `name`   varchar(255) NOT NULL UNIQUE,
    password varchar(255) DEFAULT NULL,
    players  tinyint(1) unsigned NOT NULL,
    `start`  tinyint(1) NOT NULL

) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE game
(
    id               BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,

    player_one       bigint unsigned NOT NULL,
    player_two       bigint unsigned NOT NULL,
    player_one_score int unsigned NOT NULL,
    player_two_score int unsigned NOT NULL,
    room_id          bigint unsigned NOT NULL,
    active           tinyint(1) NOT NULL,

    FOREIGN KEY (player_one) REFERENCES `user` (id),
    FOREIGN KEY (player_two) REFERENCES `user` (id),
    FOREIGN KEY (room_id) REFERENCES room (id)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;

CREATE TABLE country
(
    id        BIGINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `name`    varchar(255) NOT NULL,
    continent varchar(255) NOT NULL,
    capital   varchar(255) NOT NULL,
    name_abbr char(255)    NOT NULL
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;
