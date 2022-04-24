ALTER TABLE `user`
DROP
COLUMN learning_index;
ALTER TABLE `user`
    add learning_index integer unsigned NOT NULL DEFAULT 1;

ALTER TABLE game
    add locked tinyint(1) unsigned NOT NULL;