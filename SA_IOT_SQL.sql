-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Usuario` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USUARIONOME` VARCHAR(60) NOT NULL,
  `USUARIOCPF` VARCHAR(11) NOT NULL,
  `USUARIOEMAIL` VARCHAR(45) NOT NULL,
  `USUARIOSENHA` VARCHAR(45) NOT NULL,
  `USUARIODTNASC` DATE NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Estacionamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Estacionamento` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ESTACIONAMENTONUMERO` VARCHAR(45) NOT NULL,
  `ESTACIONAMENTOSTATUS` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Reservas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Reservas` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `RESERVAHORASTOTAL` TIME NOT NULL,
  `Estacionamento_ID` INT NOT NULL,
  `Usuario_ID` INT NOT NULL,
  `RESERVADATAINICIO` DATETIME NOT NULL,
  `RESERVASDATAFIM` DATETIME NOT NULL,
  `RESERVAHORARIOSAIDA` DATETIME NOT NULL,
  `RESERVAPLACACARRO` VARCHAR(7) NOT NULL,
  `RESERVANOMECARRO` VARCHAR(45) NOT NULL,
  `RESERVAPRECOPORHORA` DECIMAL(10,2) NOT NULL,
  `RESERVAPRECOTOTAL` VARCHAR(45) NOT NULL,
  `RESERVAHORARIOCHEGADA` DATETIME NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Reservas_Estacionamento_idx` (`Estacionamento_ID` ASC),
  INDEX `fk_Reservas_Usuario1_idx` (`Usuario_ID` ASC) ,
  CONSTRAINT `fk_Reservas_Estacionamento`
    FOREIGN KEY (`Estacionamento_ID`)
    REFERENCES `mydb`.`Estacionamento` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reservas_Usuario1`
    FOREIGN KEY (`Usuario_ID`)
    REFERENCES `mydb`.`Usuario` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
