DROP DATABASE contacts_db;
CREATE DATABASE contacts_db;

USE contacts_db;

CREATE TABLE persons (
	id INT(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name NVARCHAR(50) NOT NULL,
    surname NVARCHAR(50) NOT NULL,
    middlename NVARCHAR(50) NOT NULL,
    citizenship NVARCHAR(50),
    familystatus ENUM('single', 'married'),
    website NVARCHAR(50),
    email NVARCHAR(50),
    currentjob NVARCHAR(50),
    gender ENUM ('male', 'female'),
    datebirth DATE,
    country NVARCHAR(50),
    city NVARCHAR(50),
    street_house_apart NVARCHAR(50),
    p_index INT(10)
);

CREATE TABLE phones (
	id INT(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    persons_id INT(10) UNSIGNED,
	countrycode SMALLINT(10),
    operatorcode SMALLINT(10),
    phonebumber INT(10),
    type ENUM ('home', 'mobile'),
    comments NVARCHAR(50),
    CONSTRAINT `phones_fk_persons_id` FOREIGN KEY (persons_id) REFERENCES persons(id) ON DELETE CASCADE
);

CREATE TABLE attachments (
	id INT(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    persons_id INT(10) UNSIGNED,
	filename NVARCHAR(50),
    comments NVARCHAR(50),
    loaddate DATE,
    CONSTRAINT `attachments_fk_persons_id` FOREIGN KEY (persons_id) REFERENCES persons(id) ON DELETE CASCADE
);

INSERT INTO persons VALUES (null, 'Igor', 'Nikolaev', 'Andreevich', 'Uganda', 'married', 'web-site.com', 'some@mail.ru', 'gruzchik', 'male', '2002-02-10', 'Uganda', 'UgCapital', 'Street1/House1/Apart1', 11);
INSERT INTO persons VALUES (null, 'Sergey', 'Lazarev', 'Dmitriyevich', 'Россия', 'single', 'Lazarev-site.com', 'sergey.lazarev@mail.ru', 'kassor', 'male', '13.12.2000', 'Россия', 'Воронеж', 'Street2/House2/Apart2', 22);
INSERT INTO persons VALUES (null, 'Andrey', 'Romanenko', 'Vitalyevich', 'Украина', 'married', 'Romanenko-site.com', 'andrey.romanenko@mail.ru', 'kladovshik', 'male', '22.06.200', 'Украина', 'Львов', 'Street3/House3/Apart3', 33);
INSERT INTO persons VALUES (null, 'Irina', 'Grilchik', 'Potapovna', 'Латвия', 'married', 'Grilchik-site.com', 'grilchik.irina@mail.ru', 'manager', 'male', '1992-2-12', 'Латвия', 'Какой-то город', 'Street4/House4/Apart4', 44);
INSERT INTO persons VALUES (null, 'Anna', 'Vasilyeva', 'Ladovna', 'England', 'single', 'Ladovna-site.com', 'ladovna.annd@mail.ru', 'manager', 'female', '1990-7-19', 'England', 'London', 'Street5/House5/Apart5', 55);

INSERT INTO phones VALUES (null, 1, 375, 29, 2579640, 'mobile', 'IGOR mobile phone number');
INSERT INTO phones VALUES (null, 1, 375, 17, 3579640, 'home', 'IGOR home phone number');
INSERT INTO phones VALUES (null, 2, 375, 29, 2529620, 'mobile', 'Sergey mobile phone number');
INSERT INTO phones VALUES (null, 2, 375, 17, 2572620, 'home', 'Sergey home phone number');
INSERT INTO phones VALUES (null, 3, 375, 29, 3539630, 'mobile', 'Andrey mobile phone number');
INSERT INTO phones VALUES (null, 3, 375, 17, 3539630, 'home', 'Andrey home phone number');
INSERT INTO phones VALUES (null, 4, 375, 29, 4549640, 'mobile', 'Irina mobile phone number');
INSERT INTO phones VALUES (null, 4, 375, 17, 4549640, 'home', 'Irina home phone number');

INSERT INTO attachments VALUES (null, 1, 'homework.java', 'Home Work on Java courses', '2018-09-25');
INSERT INTO attachments VALUES (null, 1, 'homework.sql', 'Home Work on Java courses sql file', '2018-09-25');
INSERT INTO attachments VALUES (null, 2, 'movie.mp4', 'Moive43', '2018-09-25');
INSERT INTO attachments VALUES (null, 2, 'photo.jpg', 'Some photos by trip', '2018-09-25');
INSERT INTO attachments VALUES (null, 3, 'music.mp3', 'Music I like', '2018-09-25');
INSERT INTO attachments VALUES (null, 3, 'readme.txt', 'readme file for project', '2018-09-25');
INSERT INTO attachments VALUES (null, 3, 'calc.java', 'Calculator implemented on Java', '2018-09-25');
INSERT INTO attachments VALUES (null, 4, 'HeadFirst.pdf', 'Book, yet don\'t know about what', '2018-09-25');
INSERT INTO attachments VALUES (null, 4, 'notVirus.exe', 'Exactly not a virus. Trust me, I\'m an engineer', '2018-09-25');
INSERT INTO attachments VALUES (null, 4, 'notVirus2.exe', 'Trust me again. Let it run by admin permissions', '2018-09-25');

SELECT persons.id, name, persons_id, operatorcode, phonebumber, type, comments  FROM persons JOIN phones ON (persons.id = phones.persons_id) /*JOIN attachments ON (persons.id = attachments.persons_id) */;
SELECT * FROM persons /*JOIN phones ON (persons.id = phones.persons_id) */ JOIN attachments ON (persons.id = attachments.persons_id) GROUP BY name;

SELECT * FROM phones JOIN (SELECT id, name FROM persons) te ON phones.persons_id = te.id;
SELECT * FROM persons;
SELECT * FROM phones;
SELECT * FROM attachments;

SELECT COUNT(id) AS counts FROM persons;

SELECT * FROM persons WHERE datebirth = '2013-12-20';
SELECT * FROM persons WHERE name = 'Мурат'AND surname = 'null';
/* Corrects id sequences after deleting >*/
/*
SET @persons_id_conut = 0;
SET @phones_id_count = 0;
SET @attachments_id_count = 0;

UPDATE persons SET persons.id = @persons_id_conut:= @persons_id_conut + 1;
UPDATE phones SET phones.id = @phones_id_count:= @phones_id_count + 1;
UPDATE attachments SET attachments.id = @attachments_id_count:= @attachments_id_count + 1;

ALTER TABLE persons AUTO_INCREMENT = 1;
ALTER TABLE phones AUTO_INCREMENT = 1;
ALTER TABLE attachments AUTO_INCREMENT = 1;
*/
/* ^_________*/

/* Select all tables which has CASCADE property ON  */
/* select * from INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS where DELETE_RULE ='CASCADE';*/
/* 1IMPORTANT1 */

/*  Unsafe mode. Need stuff. Depends on situation*/
/* SET SQL_SAFE_UPDATES = 0;*/