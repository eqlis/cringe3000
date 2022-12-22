alter table person
drop column interests,
drop column publications,
add column interests varchar(1000),
add column publications varchar(1000);