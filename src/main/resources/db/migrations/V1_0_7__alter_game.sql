ALTER TABLE game DROP COLUMN players;
ALTER TABLE game
    ADD players varchar(255) NOT NULL;