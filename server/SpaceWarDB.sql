create table SpaceWar
(
user_id varchar2(10) primary key
, user_pw varchar2(10) not null
, hi_score varchar2(7)
, user_char varchar2(10)
)

alter table spacewar
modify (hi_score number(6, 2) default 0)

delete spacewar 


select * from spacewar

