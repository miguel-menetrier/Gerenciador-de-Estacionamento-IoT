-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           12.2.2-MariaDB - MariaDB Server
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.14.0.7165
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para mydb
CREATE DATABASE IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `mydb`;

-- Copiando estrutura para tabela mydb.estacionamento
CREATE TABLE IF NOT EXISTS `estacionamento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `estacionamentonumero` varchar(255) DEFAULT NULL,
  `estacionamentoprecoporhora` float DEFAULT NULL,
  `estacionamentostatus` tinyint(4) DEFAULT NULL CHECK (`estacionamentostatus` between 0 and 2),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela mydb.estacionamento: ~4 rows (aproximadamente)
INSERT INTO `estacionamento` (`id`, `estacionamentonumero`, `estacionamentoprecoporhora`, `estacionamentostatus`) VALUES
	(1, 'Vaga A1', 5.5, 1),
	(2, 'Vaga A2', 6, 1),
	(3, 'Vaga B1', 7.25, 0),
	(4, 'Vaga B2', 8, 1);

-- Copiando estrutura para tabela mydb.reservas
CREATE TABLE IF NOT EXISTS `reservas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reservahorariosaida` time(6) DEFAULT NULL,
  `reservahorariochegada` time(6) DEFAULT NULL,
  `reservanomecarro` varchar(255) DEFAULT NULL,
  `reservaplacacarro` varchar(255) DEFAULT NULL,
  `reservaprecoporhora` float DEFAULT NULL,
  `reservaprecototal` float DEFAULT NULL,
  `reservadatainicio` date DEFAULT NULL,
  `reservasdatafim` date DEFAULT NULL,
  `reservahorastotal` time(6) DEFAULT NULL,
  `estacionamento_id` int(11) DEFAULT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3tlg9s0e7na1q54uegmomlxfm` (`estacionamento_id`),
  KEY `FKr5jn9ggcyrhgtrfnknty7dfs6` (`usuario_id`),
  CONSTRAINT `FK3tlg9s0e7na1q54uegmomlxfm` FOREIGN KEY (`estacionamento_id`) REFERENCES `estacionamento` (`id`),
  CONSTRAINT `FKr5jn9ggcyrhgtrfnknty7dfs6` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela mydb.reservas: ~2 rows (aproximadamente)
INSERT INTO `reservas` (`id`, `reservahorariosaida`, `reservahorariochegada`, `reservanomecarro`, `reservaplacacarro`, `reservaprecoporhora`, `reservaprecototal`, `reservadatainicio`, `reservasdatafim`, `reservahorastotal`, `estacionamento_id`, `usuario_id`) VALUES
	(1, '14:00:00.000000', '10:00:00.000000', 'Fiat Uno', 'ABC1234', 5.5, 22, '2026-04-27', '2026-04-27', NULL, 1, 2),
	(2, '12:30:00.000000', '08:30:00.000000', 'Gol 1.6', 'XYZ9876', 6, 24, '2026-04-28', '2026-04-28', NULL, 2, 3);

-- Copiando estrutura para tabela mydb.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuariodtnasc` date NOT NULL,
  `usuarioemail` varchar(255) NOT NULL,
  `usuarionome` varchar(255) NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_USER') NOT NULL,
  `usuariosenha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKxitsru4dlv40kl02j5dkusyg` (`usuarioemail`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela mydb.usuario: ~3 rows (aproximadamente)
INSERT INTO `usuario` (`id`, `usuariodtnasc`, `usuarioemail`, `usuarionome`, `role`, `usuariosenha`) VALUES
	(1, '2000-01-01', 'admin@email.com', 'Administrador', 'ROLE_ADMIN', '$2a$12$3m0rC6FOt5HNLp4qYqbQXeOpFeidRgIk2wKhgUA4dzSL24GRXMAg6'),
	(2, '2001-05-10', 'user1@email.com', 'João Silva', 'ROLE_USER', '$2a$12$S2F/nYGgP0srqACmRStzsO.jU3gYVZDBGyBKXgcTBMp4FvYMVFdHa'),
	(3, '1998-09-15', 'user2@email.com', 'Maria Souza', 'ROLE_USER', '$2a$12$mnCk.hxW75GC0V2EsfBc6uvN7WcUk3fNP9fqqh/4.K3dKzOuEZj1K');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
