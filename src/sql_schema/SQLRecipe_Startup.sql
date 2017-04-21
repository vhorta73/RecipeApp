/**
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

-- Core recipe table driving all other recipe tables.
CREATE TABLE `recipe` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(255) NOT NULL,
   `version` INT(3) NOT NULL DEFAULT 0,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`name`,`version`)
);

-- The main ingredient the recipe is based on.
CREATE TABLE `recipe_main_ingredient` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `main_ingredient` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`main_ingredient`)
 );

-- The type the recipe is based on.
CREATE TABLE `recipe_type` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `type` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`type`)
);

-- The recipe style.
CREATE TABLE `recipe_style` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `style` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`style`)
);

-- The recipe course.
CREATE TABLE `recipe_course` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `course` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`course`)
);

-- The recipe description.
CREATE TABLE `recipe_description` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `description` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`)
);

-- The recipe source.
CREATE TABLE `recipe_source` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `source` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`source`)
);

-- The recipe author.
CREATE TABLE `recipe_author` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `author` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`author`)
);

-- The recipe rating.
CREATE TABLE `recipe_rating` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `rating` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`)
);

-- The recipe difficulty.
CREATE TABLE `recipe_difficulty` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `difficulty` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`)
);

-- The recipe duration.
CREATE TABLE `recipe_duration` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `type` VARCHAR(25) NOT NULL,
   `duration` TIME NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`type`)
);

-- The recipe tags.
CREATE TABLE `recipe_tag` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `tag` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`tag`)
);

-- The recipe stages.
CREATE TABLE `recipe_stage` (
   `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
   `recipe_id` INT(11) UNSIGNED NOT NULL,
   `step_id` INT(3) NOT NULL,
   `step_name` VARCHAR(75) NOT NULL,
   `description` VARCHAR(255) NOT NULL,
   `created_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `last_updated_by` VARCHAR(25) NOT NULL DEFAULT 'system',
   `last_updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY (`recipe_id`,`step_id`)
);
