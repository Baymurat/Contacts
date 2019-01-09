create table persons
(
  id                 serial      not null
    constraint persons_pk
      primary key,
  name               varchar(50) not null,
  surname            varchar(50) not null,
  middlename         varchar(50) not null,
  citizenship        varchar(50) not null,
  familystatus       varchar(50) not null,
  website            varchar(50),
  email              varchar(50),
  currentjob         varchar(50),
  gender             varchar(50),
  datebirth          date,
  country            varchar(50),
  city               varchar(50),
  street_house_apart varchar(50),
  p_index            varchar(50)
);

alter table persons
  owner to postgres;

create table phones
(
  id           serial   not null
    constraint phones_pk
      primary key,
  persons_id   integer  not null
    constraint phones_persons_id_fk
      references persons,
  countrycode  smallint not null,
  operatorcode smallint not null,
  phonebumber  integer  not null,
  type         varchar(50),
  comments     varchar(50)
);

alter table phones
  owner to postgres;

create table attachments
(
  id         serial  not null
    constraint attachments_pk
      primary key,
  persons_id integer not null
    constraint attachments_persons_id_fk
      references persons
      on delete cascade,
  filename   varchar(50),
  comments   varchar(50),
  loaddate   date
);

alter table attachments
  owner to postgres;


