create table subject (
    id bigserial primary key,
    name varchar(256)
);

insert into subject(name)
values ('Functional Analysis'),
('English'),
('Algorithms'),
('Nonlinear Problems of Mathematical Physics');