INSERT INTO recipe(title, short_description, ingriedients, recipe)
VALUES ('This is a title',
        'This is some short description',
        '1.Pirmas;
        2. antras;
        3. Trecias;',
        'A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT
A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT ');

INSERT INTO recipe(title, short_description, ingriedients, recipe)
VALUES ('Thiaerghaerhaerhaerhearhitle',
        'Tserghserhserhaehrn',
        'srgre;
        segrrrg;
        grsehserh;', 'A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT
A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT A LOT OF TEXT ');


insert into usr (username, password, active)
values ('admin', '123', true);

insert into user_role (user_id, roles)
values (1, 'ADMIN');