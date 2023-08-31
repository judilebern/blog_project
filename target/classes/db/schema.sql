CREATE TABLE recipe
(
    id                integer NOT NULL,
    title             text NULL,
    short_description text NULL,
    ingriedients      text NULL,
    recipe           text NULL,
    CONSTRAINT recipe_pkey PRIMARY KEY (id)
);