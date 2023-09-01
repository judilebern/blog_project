CREATE TABLE recipe
(
    id                integer NOT NULL,
    title             text NULL,
    short_description text NULL,
    ingriedients      text NULL,
    recipe            text NULL,
    CONSTRAINT recipe_pkey PRIMARY KEY (id)
);

CREATE TABLE product_image
(
    id        bigserial NOT NULL,
    imagedata oid NULL,
    "name"    varchar(255) NULL,
    "type"    varchar(255) NULL,
    CONSTRAINT product_image_pkey PRIMARY KEY (id)
);


alter table if exists product_image
    add constraint fk_product_image
    foreign key (recipe_id)
    references recipe;