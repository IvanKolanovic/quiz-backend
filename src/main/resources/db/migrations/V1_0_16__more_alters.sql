ALTER TABLE message CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE message MODIFY content TEXT CHARSET utf8mb4 NOT NULL;

alter table participants
    add has_left tinyint(1) not null default false;

alter table game
    add started_at timestamp null;