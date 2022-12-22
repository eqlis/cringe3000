alter table person
drop constraint fk_person_user,
drop column user_id,
add constraint fk_person_user foreign key (id) references user_(id);