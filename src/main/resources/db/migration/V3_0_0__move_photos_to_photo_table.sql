create table photo (
    id bigserial primary key,
    photo bytea,
    index integer,
    person_id bigint,
    constraint uc_index_person_id unique(index, person_id),
    constraint fk_photo_person foreign key(person_id) references person(id)
);

insert into photo(photo, index, person_id)
select photo, 1, id from person where photo is not null;

alter table person
drop column photo,
add column selected_photo integer;