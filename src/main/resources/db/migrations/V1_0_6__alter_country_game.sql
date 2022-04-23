ALTER TABLE game
    add active tinyint(1) NOT NULL;
ALTER TABLE country
    add description varchar(255) NOT NULL;
ALTER TABLE `user`
    add learning_index integer unsigned NOT NULL DEFAULT 0;
ALTER TABLE `user`
    add `username` varchar(255) UNIQUE;

UPDATE user
set learning_index = 0;
update user
set username = 'admin'
where id = 1;
update user
set username = 'sluggm'
where id = 2;
update user
set username = 'torcida'
where id = 3;


