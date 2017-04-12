-/**
 * ========================== WARNING ============================
 *   THIS SCRIPT WILL ERASE ALL DATA FROM recipe_admin DATABASE     
 *       AND WILL RESTORE DATABASE TO DEFAULT STARTUP STATE       
 * ===============================================================
 **/

CREATE DATABASE temporary_database_recipe;
USE temporary_database_recipe;
DROP DATABASE IF EXISTS recipe_admin;
CREATE DATABASE recipe_admin;
USE recipe_admin;
DROP DATABASE IF EXISTS temporary_database_recipe;

/* The known ingredient names */
CREATE TABLE `user` (
    `id`              BIGINT AUTO_INCREMENT NOT NULL,
    `email`           VARCHAR(75)  NOT NULL DEFAULT '',
    `title`           CHAR(5) NOT NULL DEFAULT '',
    `first_name`      VARCHAR(50) NOT NULL DEFAULT '',
    `middle_names`    VARCHAR(50) NOT NULL DEFAULT '',
    `last_name`       VARCHAR(50) NOT NULL DEFAULT '',
    `account_type`    VARCHAR(50) NOT NULL DEFAULT '',
    `last_activity`   TIMESTAMP NOT NULL,
    `active`          CHAR(1) NOT NULL DEFAULT 'Y',
    `created_date`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    `last_updated`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `updated_by`      VARCHAR(25) NOT NULL DEFAULT 'system',
    PRIMARY KEY(`id`),
    UNIQUE KEY(`email`)
);

