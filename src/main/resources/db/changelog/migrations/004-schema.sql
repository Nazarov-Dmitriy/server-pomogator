-- liquibase formatted sql

--changeset dmitriy:1
insert into news(title , subtitle, article)
values ('статья 1','подзоголовок1', ' статья1'),
       ('статья 2','подзоголовок2', ' статья2');

--changeset dmitriy:2
insert into news_tags(news_id, tag_id)
values (1, 1),
       (1, 3);

--changeset dmitriy:3
insert into news_tags(news_id, tag_id)
values (2, 2), (2, 4);

--changeset dmitriy:4
insert into file(name, model_type , size)
values ('file 1', 'jpg', 12312), ('file 2', 'jpg', 12312) , ('file 3', 'jpg', 12312);

--changeset dmitriy:5
insert into news_file(news_id,  file_id)
values (1, 2), (2, 1), (1, 2);
