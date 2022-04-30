alter
    table participants
    add has_won tinyint(1) default null;

alter
    table participants
    add finished_at timestamp null;

ALTER TABLE user_statistics drop column point_average;

ALTER TABLE user_statistics
    add point_average FLOAT(10,2) unsigned NOT NULL DEFAULT 0;

update rank
set `name` = 'Challanger'
where id = 7;


