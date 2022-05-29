-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: railwayreservationsystem
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_accounts`
--

DROP TABLE IF EXISTS `admin_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_accounts` (
  `Sno` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`Sno`,`username`),
  UNIQUE KEY `Sno_UNIQUE` (`Sno`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_accounts`
--

LOCK TABLES `admin_accounts` WRITE;
/*!40000 ALTER TABLE `admin_accounts` DISABLE KEYS */;
INSERT INTO `admin_accounts` VALUES (1,'Pahel','Jagtap','Sammy','adminuser');
/*!40000 ALTER TABLE `admin_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booked_tickets`
--

DROP TABLE IF EXISTS `booked_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booked_tickets` (
  `SNo` int NOT NULL AUTO_INCREMENT,
  `booking_id` varchar(45) NOT NULL,
  `PNR_booked` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `no_of_tickets` int NOT NULL,
  `booking_time` datetime NOT NULL,
  PRIMARY KEY (`SNo`,`booking_id`,`PNR_booked`,`username`),
  UNIQUE KEY `booking_id_UNIQUE` (`booking_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booked_tickets`
--

LOCK TABLES `booked_tickets` WRITE;
/*!40000 ALTER TABLE `booked_tickets` DISABLE KEYS */;
INSERT INTO `booked_tickets` VALUES (1,'g2p4B','8238955362','Pahel','Jagtap','test',1,'2021-07-17 08:31:16'),(5,'0muLE','02617','Pahel','Jagtap','Sammy',1,'2021-07-25 10:28:18');
/*!40000 ALTER TABLE `booked_tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train_details`
--

DROP TABLE IF EXISTS `train_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train_details` (
  `Sno` int NOT NULL AUTO_INCREMENT,
  `pnr_id` varchar(45) NOT NULL,
  `source` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `train_name` varchar(100) NOT NULL,
  `departure_time` time DEFAULT NULL,
  `seats_available` int NOT NULL,
  PRIMARY KEY (`Sno`,`pnr_id`),
  UNIQUE KEY `Sno._UNIQUE` (`Sno`),
  UNIQUE KEY `pnr_id_UNIQUE` (`pnr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train_details`
--

LOCK TABLES `train_details` WRITE;
/*!40000 ALTER TABLE `train_details` DISABLE KEYS */;
INSERT INTO `train_details` VALUES (1,'8238955362','Thane','Goa','01111/MAO Festival Special','23:45:00',100),(7,'02617','MADGAON','KALYAN','MANGALA LAKSHWADEEP','01:35:00',100),(8,'09228','INDORE','MUMBAI CENTRAL','MMCT DURANTO SPECIAL','21:00:00',100),(9,'04200','HAZRAT NIZAMUDDIN','AGRA CANTT','SUHASAN SF SPECIAL','01:00:00',100),(10,'01021','PUNE','YESHWANTPUR','DR TEN SPECIAL','01:00:00',100),(11,'02433','MADRAS','BHOPAL','MAS NZM RAJDHANI','06:05:00',100),(12,'02987','SIALDAH','JAIPUR','SDAH AJMER SPECIAL','22:55:00',100),(13,'02297','VASAI ROAD','PUNE JUNCTION','PUNE DURANTO','03:55:00',100),(14,'01078','JAMMU TAVI','GWALIOR','JHELUM EXPRESS','23:40:00',100),(15,'09424','GANDHINAGAR','TIRUNELVELI','TEN FESTIVAL SPECIAL','04:40:00',100),(16,'03255','PATNA','CHANDIGARH','PATNA CHANDIGARH SPECIAL','20:35:00',100),(17,'02216','AJMER','JAIPUR','DEE GARIB RATH','04:00:00',100),(18,'06081','BENGALURU','MYSORE','CHENNAI MYSORE SPECIAL','10:50:00',100),(19,'06532','MANGALORE','BENGALURU','KANNUR BANGALORE SPECIAL','20:17:00',100),(20,'04564','CHANDIGARH','KOTA','GOA SAMARPAK KRANTI SPECIAL','02:15:00',100),(21,'04687','GORAKHPUR','LUCKNOW','ASR GARIB RATH','00:45:00',100),(22,'06168','HAZRAT NIZAMUDDIN','TRIVANDRUM','NIZAMUDDIN TRIVANDRUM SF SPL','05:10:00',100),(23,'04677','AHMEDABAD','NEW DELHI','HAPA SVDK SPL','14:30:00',100),(24,'09774','KOTA','UJJAIN','JAIPUR INDORE SPECIAL','00:40:00',100),(25,'02190','NAGPUR','MUMBAI CENTRAL','CSTM DURANTO SPECIAL','20:40:00',100);
/*!40000 ALTER TABLE `train_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_accounts`
--

DROP TABLE IF EXISTS `user_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_accounts` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_accounts`
--

LOCK TABLES `user_accounts` WRITE;
/*!40000 ALTER TABLE `user_accounts` DISABLE KEYS */;
INSERT INTO `user_accounts` VALUES (1,'Pahel','Jagtap','Sammy','hellosql'),(2,'Megha','Sharma','Fashionqueen','queenbee'),(3,'Parth','Patki','Torroido','parthmojpp'),(5,'Nandini','HV','NHV','NHV123'),(6,'Rahul','Kumar','DotBlue','dotblue123'),(7,'Hrishikesh','Karnik','KarnikDew','safepassword'),(8,'Rochan','Kumar','Dyresty','password');
/*!40000 ALTER TABLE `user_accounts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-25 23:09:28
