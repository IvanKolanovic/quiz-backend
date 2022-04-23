ALTER TABLE game DROP FOREIGN KEY game_ibfk_3;
DROP TABLE room;
ALTER TABLE game DROP COLUMN room_id;
ALTER TABLE game DROP COLUMN active;
ALTER TABLE game
    ADD `name` varchar(255) NOT NULL UNIQUE;
ALTER TABLE game
    ADD password varchar(255) DEFAULT NULL;
ALTER TABLE game
    ADD players varchar(255) NOT NULL UNIQUE;
ALTER TABLE game
    add started tinyint(1) NOT NULL;

ALTER TABLE user_statistics
    add total_game bigint unsigned NOT NULL DEFAULT 0;
ALTER TABLE user_statistics
    add point_average FLOAT(5,2) unsigned NOT NULL DEFAULT 0;

UPDATE user_statistics
SET total_game = 0;
UPDATE user_statistics
SET point_average = 0.0;

