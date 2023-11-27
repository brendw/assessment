-- schema.sql

CREATE DATABASE products;
USE products; 

CREATE TABLE product
(
id INT UNSIGNED NOT NULL PRIMARY KEY,
name VARCHAR(45) NOT NULL, 
category VARCHAR(45) NOT NULL,
date_added DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
added_by INT UNSIGNED NOT NULL,

--FOREIGN KEY (added_by) REFERENCES user_table (id),
);

CREATE TABLE product_price
(
product_id INT UNSIGNED NOT NULL PRIMARY KEY, 
price decimal(6,2) NOT NULL,
discount_percent INT DEFAULT 0,
time_last_updated DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
last_updated_by INT UNSIGNED NOT NULL, 

FOREIGN KEY (product_id) REFERENCES products.product (id),
--FOREIGN KEY (last_updated_by) REFERENCES user_table (id)
);

CREATE TABLE product_price_change_log
(
change_id INT UNSIGNED NOT NULL auto-increment PRIMARY KEY,
product_id INT UNSIGNED NOT NULL,
previous_price DECIMAL(6,2),
current_price DECIMAL(6,2), 
time_updated DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_by INT UNSIGNED NOT NULL,

FOREIGN KEY(product_id) REFERENCES products.product (id),
--FOREIGN KEY (updated_by) REFERENCES user_table (id)
);

/*
-- triggers

CREATE TRIGGER after_product_price_insert
AFTER INSERT
ON product_price FOR EACH ROW
BEGIN

INSERT INTO product_price_change_log(product_id, previous_price, current_price, updated_by)
VALUES (NEW.product_id, NULL, NEW.price, USER());

END

CREATE TRIGGER after_product_price_update
AFTER UPDATE
ON product_price FOR EACH ROW
BEGIN

INSERT INTO product_price_change_log(product_id, previous_price, current_price, updated_by)
VALUES (NEW.product_id, OLD.price, NEW.price, USER());

END

CREATE TRIGGER after_product_price_delete
AFTER DELETE
ON product_price FOR EACH ROW
BEGIN

INSERT INTO product_price_change_log(product_id, previous_price, current_price, updated_by)
VALUES (OLD.product_id, OLD.price, NULL, USER());

END
*/

-- query

SELECT product.name, product.category, product_price.price, product_price.time_last_updated, product_price.last_updated_by
FROM product 
INNERJOIN product_price ON product.id=product_price.product_id;