create table users
(
	id serial not null
		constraint users_pk
			primary key,
	name varchar(50),
	password varchar(150),
	email varchar(50),
	role varchar(50),
	active boolean default true
);

create unique index users_email_uindex
	on users (email);