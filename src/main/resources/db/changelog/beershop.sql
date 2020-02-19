# DROP DATABASE beershop;
# CREATE DATABASE beershop DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
# USE beershop;
# DROP TABLE IF EXISTS beer;
CREATE TABLE beer
(
    id          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(512),
    in_stock    BOOL,
    name        VARCHAR(512),
    description VARCHAR(512),
    alcohol     DOUBLE,
    density     DOUBLE,
    country     VARCHAR(512),
    price       DOUBLE
);
# ) DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
# ALTER TABLE beer
#     CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;
INSERT INTO beer (type, in_stock, name, description, alcohol, density, country, price)
VALUES ('светлое', true, 'Лидское', 'Лучшее пиво по бабушкиным рецептам', 5.0, 11.5, 'Республика Беларусь', 5.0),
       ('темное', true, 'Аливария', 'Пиво номер 1 в Беларуси', 4.6, 10.2, 'Республика Беларусь', 3.0),
       ('светлое осветлённое', true, 'Pilsner Urquell', 'непастеризованное', 4.2, 12.0, 'Чехия', 8.0);

# DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(512),
    password  VARCHAR(64),
    email     VARCHAR(64),
    phone     VARCHAR(64),
    user_role INT
);

INSERT INTO user (name, password, email, phone, user_role)
VALUES ('Иван Иванов', '123456', 'ivan.ivanov@mail.ru', '+375331234567', 1),
       ('Петр Петров', '654321', 'petr.petrov@yandex.ru', '+375337654321', 1);

# DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id   INT NOT NULL,
    processed BOOL,
    total     DOUBLE,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

INSERT INTO orders (user_id, processed, total)
VALUES (1, true, 25.0),
       (2, false, 27.0);

# DROP TABLE IF EXISTS customer_order;
CREATE TABLE customer_order
(
    id       INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    beer_id  INT NOT NULL,
    count    INT,
    FOREIGN KEY (beer_id) REFERENCES beer (id),
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

INSERT INTO customer_order (order_id, beer_id, count)
VALUES (1, 1, 2),
       (1, 2, 5);