-- liquibase formatted sql

--changeset dmitriy:1
create table category
(
    id   bigint AUTO_INCREMENT primary key,
    name varchar(255) not null,
    link_name varchar(255) not null
);

--changeset dmitriy:2
insert into category(name, link_name )
values ('Химия', 'khimiya'),
       ('Физика', 'fizika'),
       ('Биология', 'biologiya'),
       ('Робототехника', 'robototekhnika');

--changeset dmitriy:3
create table tags
(
    id   bigint AUTO_INCREMENT primary key,
    name varchar(255) not null
);

--changeset dmitriy:4
insert into tags(name)
values ('Химия'),
       ('Физика'),
       ('Биология'),
       ('Робототехника'),
       ('Мастер-класс'),
       ('Практика'),
       ('Видео'),
       ('Статья'),
       ('Вeбинар');

--changeset dmitriy:5
CREATE TABLE users
(
    id bigint AUTO_INCREMENT primary key
);











