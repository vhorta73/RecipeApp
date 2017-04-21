/**
 * ========================== WARNING ============================
 *         THIS SCRIPT WILL ERASE ALL DATA FROM recipe DB     
 *       AND WILL RESTORE DATABASE TO DEFAULT STARTUP STATE       
 * ===============================================================
 **/

CREATE DATABASE temporary_database_recipe;
USE temporary_database_recipe;
DROP DATABASE IF EXISTS recipe_vendor;
CREATE DATABASE recipe_vendor;
USE recipe_vendor;
DROP DATABASE IF EXISTS temporary_database_recipe;

CREATE TABLE `tesco_product` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `product_id`      VARCHAR(25)  NOT NULL DEFAULT '',
	`title`           VARCHAR(255) NOT NULL DEFAULT '',
	`halal`           CHAR(1)      NOT NULL DEFAULT 'N',
	`vegetarian`      CHAR(1)      NOT NULL DEFAULT 'N',
	`offer`           CHAR(1)      NOT NULL DEFAULT 'N',
	`available`       CHAR(1)      NOT NULL DEFAULT 'Y',
	`amount`          DOUBLE(10,3) NOT NULL DEFAULT 0.0,
	`amount_unit`     VARCHAR(25)  NOT NULL DEFAULT '',
	`price`           DOUBLE(10,2) NOT NULL DEFAULT 0.0,
	`ccy`             CHAR(3)      NOT NULL DEFAULT '',
	`base_price`      DOUBLE(10,2) NOT NULL DEFAULT 0.0,
	`base_ccy`        CHAR(3)      NOT NULL DEFAULT '',
	`base_value`      DOUBLE(10,3) NOT NULL DEFAULT 0.0,
	`base_unit`       VARCHAR(25)  NOT NULL DEFAULT '',
	`details` TEXT NOT NULL,
    `product_url`     VARCHAR(255)  NOT NULL DEFAULT '',
    `image_small_url` VARCHAR(255)  NOT NULL DEFAULT '',
    `image_large_url` VARCHAR(255)  NOT NULL DEFAULT '',
    `imported_date`   DATE,
    `processed`       CHAR(1)       NOT NULL DEFAULT 'N',
    `created_date`    timestamp not null default current_timestamp,
    `last_updated`    timestamp not null default current_timestamp on update current_timestamp,
    PRIMARY KEY(`id`),
    UNIQUE KEY(`product_id`,`imported_date`),
    KEY(`title`)
);


CREATE TABLE `core_name` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `name`            VARCHAR(75)  NOT NULL DEFAULT '',
    `created_date`    timestamp not null default current_timestamp,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    timestamp not null default current_timestamp on update current_timestamp,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`name`)
);

INSERT INTO `core_name` (`name`) VALUES("Banana");
INSERT INTO `core_name` (`name`) VALUES("Orange");
INSERT INTO `core_name` (`name`) VALUES("Sugar");
INSERT INTO `core_name` (`name`) VALUES("Flour");
INSERT INTO `core_name` (`name`) VALUES("Milk");

CREATE TABLE `core_attribute` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `name`            VARCHAR(25)  NOT NULL DEFAULT '',
    `created_date`    timestamp not null default current_timestamp,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    timestamp not null default current_timestamp on update current_timestamp,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`name`)
);

INSERT INTO `core_attribute` (`name`) VALUES("Large");
INSERT INTO `core_attribute` (`name`) VALUES("Small");
INSERT INTO `core_attribute` (`name`) VALUES("Green");
INSERT INTO `core_attribute` (`name`) VALUES("Yellow");

CREATE TABLE `ingredient` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `name_id`         BIGINT NOT NULL DEFAULT 0, 
    `created_date`    timestamp not null default current_timestamp,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    timestamp not null default current_timestamp on update current_timestamp,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    KEY(`name_id`)
);

INSERT INTO `ingredient` (`name_id`) VALUES(1);
INSERT INTO `ingredient` (`name_id`) VALUES(2);
INSERT INTO `ingredient` (`name_id`) VALUES(3);
INSERT INTO `ingredient` (`name_id`) VALUES(4);
INSERT INTO `ingredient` (`name_id`) VALUES(1);

CREATE TABLE `ingredient_attribute` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `ingredient_id`   BIGINT NOT NULL,
    `attribute_id`    BIGINT NOT NULL,
    `created_date`    timestamp not null default current_timestamp,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    timestamp not null default current_timestamp on update current_timestamp,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`ingredient_id`,`attribute_id`)
);

INSERT INTO `ingredient_attribute` (`ingredient_id`,`attribute_id`) VALUES(1,1);
INSERT INTO `ingredient_attribute` (`ingredient_id`,`attribute_id`) VALUES(2,2);
INSERT INTO `ingredient_attribute` (`ingredient_id`,`attribute_id`) VALUES(3,3);
INSERT INTO `ingredient_attribute` (`ingredient_id`,`attribute_id`) VALUES(4,4);
INSERT INTO `ingredient_attribute` (`ingredient_id`,`attribute_id`) VALUES(1,2);
INSERT INTO `ingredient_attribute` (`ingredient_id`,`attribute_id`) VALUES(1,3);

SELECT i.id, cn.name, ca.name 
FROM ingredient i 
JOIN core_name cn, ingredient_attribute ia, core_attribute ca 
WHERE i.name_id = cn.id AND ia.ingredient_id = i.id AND ia.attribute_id = ca.id;


