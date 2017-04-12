-/**
 * ========================== WARNING ============================
 *   THIS SCRIPT WILL ERASE ALL DATA FROM recipe_core DATABASE     
 *       AND WILL RESTORE DATABASE TO DEFAULT STARTUP STATE       
 * ===============================================================
 **/

CREATE DATABASE temporary_database_recipe;
USE temporary_database_recipe;
DROP DATABASE IF EXISTS recipe_core;
CREATE DATABASE recipe_core;
USE recipe_core;
DROP DATABASE IF EXISTS temporary_database_recipe;

/* The known ingredient names */
CREATE TABLE `ingredient` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `name`            VARCHAR(75)  NOT NULL DEFAULT '',
    `created_date`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`name`)
);

/* The known ingredient attribute names */
CREATE TABLE `attribute` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `name`            VARCHAR(25)  NOT NULL DEFAULT '',
    `created_date`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`name`)
);

/* The known ingredient source names */
CREATE TABLE `source` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `name`            VARCHAR(25)  NOT NULL DEFAULT '',
    `created_date`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`name`)
);

/* The link of ingredient attributes */
CREATE TABLE `ingredient_attribute` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `ingredient_id`   BIGINT NOT NULL,
    `attribute_id`    BIGINT NOT NULL,
    `created_date`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`ingredient_id`,`attribute_id`)
);

/* The link of ingredient sources */
CREATE TABLE `ingredient_source` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `ingredient_id`   BIGINT NOT NULL,
    `source_id`       BIGINT NOT NULL,
    `created_date`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`ingredient_id`, `source_id`)
);
