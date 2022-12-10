create table subject (
    id bigserial primary key,
    name varchar(255)
);

create table degree (
    id bigserial primary key,
    name varchar(255)
);

create table user_ (
    id bigserial primary key,
    email varchar(255),
    username varchar(255),
    password varchar(255),
    role varchar(100) check (role in ('USER', 'ADMIN')),
    enabled boolean not null default false,
    locked boolean not null default false,
    deleted boolean not null default false
);

create table person (
    id bigserial primary key,
    user_id bigint,
    first_name varchar(255),
    last_name varchar(255),
    surname varchar(255),
    email varchar(255),
    birthday date not null,
    gender varchar(10) check (gender in ('FEMALE', 'MALE')),
    experience integer not null default 0,
    phone varchar(50),
    bio varchar(1000),
    degree_id bigint,
    photo bytea,
    interests varchar(255) [],
    publications varchar(255) [],
    constraint fk_person_user foreign key(user_id) references user_(id),
    constraint fk_person_degree foreign key(degree_id) references degree(id)
);

create table verification_token (
    id bigserial primary key,
    token varchar(255),
    created_on timestamp,
    expire_at timestamp,
    user_id bigint,
    verified boolean default false,
    constraint fk_verification_token_user foreign key(user_id) references user_(id)
);

create table person_subject (
    person_id bigint,
    subject_id bigint,
    constraint fk_person_subject_person foreign key(person_id) references person(id),
    constraint fk_person_subject_subject foreign key(subject_id) references subject(id),
    constraint uc_person_subject unique (person_id, subject_id)
);