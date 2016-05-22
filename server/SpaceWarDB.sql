create table SpaceWar
(
user_id varchar2(10) primary key
, user_pw varchar2(10) not null
, hi_score number(6, 2) default 0
, user_char varchar2(10) default 'No_Play'
)

alter table spacewar
modify (hi_score number(6, 2) default 0)

delete spacewar 


select * from spacewar

insert into spacewar (user_id, user_pw)
values ('1', '1');
insert into spacewar (user_id, user_pw)
values ('2', '2');
insert into spacewar (user_id, user_pw)
values ('3', '3');
insert into spacewar (user_id, user_pw)
values ('4', '1');
insert into spacewar (user_id, user_pw)
values ('5', '1');
insert into spacewar (user_id, user_pw)
values ('6', '1');
insert into spacewar (user_id, user_pw)
values ('7', '1');
insert into spacewar (user_id, user_pw)
values ('8', '1');

delete spacewar
where user_id = '5'