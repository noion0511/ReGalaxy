-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: i9d102.p.ssafy.io    Database: s09p12d102
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `phone`
--

DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone` (
  `phone_id` int NOT NULL AUTO_INCREMENT,
  `model_id` int DEFAULT NULL,
  `donation_id` int DEFAULT NULL,
  `serial_number` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `isy2k` tinyint DEFAULT NULL,
  `is_climate_humidity` tinyint DEFAULT NULL,
  `is_homecam` tinyint DEFAULT NULL,
  `is_rental` tinyint DEFAULT '0',
  `rental_id` bigint DEFAULT NULL,
  PRIMARY KEY (`phone_id`),
  KEY `fk_Phone_Model1_idx` (`model_id`),
  KEY `fk_phone_donation1_idx` (`donation_id`),
  CONSTRAINT `fk_phone_donation1` FOREIGN KEY (`donation_id`) REFERENCES `donation` (`donation_id`),
  CONSTRAINT `fk_Phone_Model1` FOREIGN KEY (`model_id`) REFERENCES `model` (`model_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-19 22:58:39
