CREATE TABLE message
(
    id      BIGINT UNSIGNED auto_increment NOT NULL PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    content TEXT                                NOT NULL,
    sent_at timestamp default current_timestamp not null,

    FOREIGN KEY (user_id) REFERENCES user (id)
) ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    COLLATE = utf8_unicode_ci;