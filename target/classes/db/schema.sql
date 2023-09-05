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
