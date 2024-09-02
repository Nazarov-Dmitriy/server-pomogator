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
       ('Статья');

--changeset dmitriy:5
create table news
(
    id               bigint AUTO_INCREMENT primary key,
    title            varchar(255) not null,
    subtitle         varchar(255) not null,
    article          TEXT         not null,
    date_publication timestamp    DEFAULT CURRENT_TIMESTAMP,
    category_id      bigint       not null,
    shows            int          DEFAULT 0,
    likes            int          DEFAULT 0,
    tags             varchar(255) DEFAULT 0,
    FOREIGN KEY (category_id) REFERENCES category (id)
);









