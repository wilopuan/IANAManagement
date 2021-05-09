--Creación de la tabla destino: Original MySql
CREATE TABLE `mydb`.`rangelocation` (
  `ipfrom` BIGINT NOT NULL,
  `ipto` BIGINT NOT NULL,
  `countrycode` VARCHAR(16) NULL,
  `country` VARCHAR(32) NULL,
  `region` VARCHAR(64) NULL,
  `city` VARCHAR(64) NULL,
  `latitude` VARCHAR(16) NULL,
  `longitude` VARCHAR(16) NULL,
  `company` VARCHAR(128) NULL,
  PRIMARY KEY (`ipfrom` , `ipto`));