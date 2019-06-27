insert into usr values (1, true, false, false, '$2a$08$ljpA1CDgaRu0wxh0Ym5hSudbGpvZCs/3bR/Muc2hIKQFJniWJlxE2', 'admin');
insert into usr_role(user_id, roles) values (1, 'ADMIN'), (1, 'USER');

insert into translation values (1, 'ru-en', 'Привет, Мир!', 'Hello, World!', 1);
insert into translation values (2, 'ru-en', 'Привет!', 'Hello!', 1);
insert into translation values (3, 'en-ru', 'hello!', 'привет!', 1);