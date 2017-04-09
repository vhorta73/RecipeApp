/**
 * ========================== WARNING ============================
 *         THIS SCRIPT WILL ERASE ALL DATA FROM recipe DB     
 *       AND WILL RESTORE DATABASE TO DEFAULT STARTUP STATE       
 * ===============================================================
 **/

CREATE DATABASE temporary_database_recipe;
USE temporary_database_recipe;
DROP DATABASE IF EXISTS recipe;
CREATE DATABASE recipe;
USE recipe;
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

CREATE TABLE `tesco_product` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `product_id`      VARCHAR(25)  NOT NULL DEFAULT '',
	`title`           VARCHAR(255) NOT NULL DEFAULT '',
	`halal`           CHAR(1)      NOT NULL DEFAULT 'N',
	`vegetarian`      CHAR(1)      NOT NULL DEFAULT 'N',
	`amount`          DOUBLE(10,3) NOT NULL DEFAULT 0.0,
	`amount_unit`     VARCHAR(25)  NOT NULL DEFAULT '',
	`price`           DOUBLE(10,2) NOT NULL DEFAULT 0.0,
	`ccy`             CHAR(3)      NOT NULL DEFAULT '',
	`base_price`      DOUBLE(10,2) NOT NULL DEFAULT 0.0,
	`base_ccy`        CHAR(3)      NOT NULL DEFAULT '',
	`base_value`      DOUBLE(10,3) NOT NULL DEFAULT 0.0,
	`base_unit`       VARCHAR(25)  NOT NULL DEFAULT '',
	`details`         TEXT NOT NULL,
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

