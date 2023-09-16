CREATE TABLE recipe
(
    id                integer NOT NULL,
    title             text NULL,
    short_description text NULL,
    ingriedients      text NULL,
    recipe            text NULL,
    CONSTRAINT recipe_pkey PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id     integer NOT NULL,,
    text varchar(255),
    recipe_id  bigint,
    dateTime TIMESTAMP,
    CONSTRAINT recipe_pkey PRIMARY KEY (id)
);

alter table if exists comment
    add constraint fk_comment_recipe
    foreign key (recipe_id)
    references recipe;

create table usr (
                     id integer NOT NULL,
                     active boolean not null,
                     password varchar(255) not null,
                     username varchar(255) not null,
                     primary key (id)
);


create table user_role (
                           user_id integer NOT NULL,
                           roles varchar(255)
);


alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr;


alter table comment add column created_by_id integer default 1;

alter table if exists comment
    add constraint comment_user_fk
    foreign key (created_by_id) references usr;
