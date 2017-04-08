/**
 * ========================== WARNING ============================
 *       THIS SCRIPT WILL ERASE ALL DATA FROM rfact_wh     
 *       AND WILL RESTORE DATABASE TO DEFAULT STARTUP STATE       
 * ===============================================================
 **/

CREATE DATABASE temporary_database_recipe;
USE temporary_database_recipe;
DROP DATABASE IF EXISTS recipe;
CREATE DATABASE recipe;
USE recipe;
DROP DATABASE IF EXISTS temporary_database_recipe;

CREATE TABLE `ingredient` (
	`id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`description` TEXT NOT NULL,
	`vendor` VARCHAR(25) NOT NULL,
	`nutrition` TEXT NOT NULL,
	`price_value` DOUBLE NOT NULL DEFAULT 0.0,
	`price_ccy` VARCHAR(3) NOT NULL DEFAULT '',
    `created` timestamp not null default current_timestamp,
    `updated` timestamp not null default current_timestamp on update current_timestamp,
	PRIMARY KEY (`id`),
    KEY(`name`,`vendor`)
);

insert into recipe.ingredient(`name`,`description`,`vendor`,`nutrition`)VALUES("banana","desc","TESCO","")

-- CREATE TABLE `order` (
--    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
--    `name` varchar(50) NOT NULL,
--    `created_by` varchar(25) NOT NULL DEFAULT 'system',
--    `created_date` timestamp not NULL DEFAULT CURRENT_TIMESTAMP,
--    `last_updated_by` varchar(25) NOT NULL DEFAULT 'system',
--    `last_updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--    PRIMARY KEY (`id`),
--    KEY (`name`),
--    KEY(`created_by`),
--    KEY(`last_updated_by`),
--    KEY(`created_date`),
--    KEY(`last_updated_date`)
--  );
