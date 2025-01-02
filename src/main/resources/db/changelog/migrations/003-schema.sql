-- liquibase formatted sql

--changeset dmitriy:1
create table files
(
    id   bigint AUTO_INCREMENT primary key,
    name varchar(255) not null,
    path varchar(255) not null,
    size bigint       not null
);

