create table jwt (
    token varchar(300) primary key,
    user_id bigint,
    expire_at timestamp,
    active boolean default true,
    constraint fk_jwt_user foreign key(user_id) references user_(id)
);