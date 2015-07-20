CREATE DATABASE  IF NOT EXISTS `inspigen` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `inspigen`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: inspigen
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ig_addresses`
--

DROP TABLE IF EXISTS `ig_addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_addresses` (
  `id` bigint(20) NOT NULL,
  `address` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `city` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `zip_code` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `state` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `country` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `registered_address` tinyint(1) NOT NULL,
  `mail_address` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_addresses`
--

LOCK TABLES `ig_addresses` WRITE;
/*!40000 ALTER TABLE `ig_addresses` DISABLE KEYS */;
INSERT INTO `ig_addresses` VALUES (12,'Podkarpacka 1','Rzeszów','38-548','Podkarpackie','Polska',1,0),(13,'Podwiślańska 1','Poznań','35-564','Podkarpackie','Polska',0,1),(21,'Wilanowska 15','Warszawa','35-564','Mazowieckie','Polska',0,1),(22,'Krakowska 4','Kraków','35-564','Dolno-Śląskie','Polska',1,1),(24,'Wilanowska 18','Kraków','38-548','Podkarpackie','Polska',1,0),(32,'Mickiewicza 10','Rzeszów','35-564','Podkarpackie','Polska',0,1),(33,'Markowska 7','Rzeszów','35-564','Podkarpackie','Polska',1,0),(34,'Kopernika 10','Pozna?','07-444','Pomorskie','Polska',1,0);
/*!40000 ALTER TABLE `ig_addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_attachments`
--

DROP TABLE IF EXISTS `ig_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_attachments` (
  `id` bigint(20) NOT NULL,
  `file_name` varchar(70) COLLATE utf8_polish_ci NOT NULL,
  `file_type` varchar(80) COLLATE utf8_polish_ci NOT NULL,
  `file` longblob NOT NULL,
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_attachments_user_id_idx` (`user_id`),
  CONSTRAINT `fk_attachments_user_id` FOREIGN KEY (`user_id`) REFERENCES `ig_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_attachments`
--

LOCK TABLES `ig_attachments` WRITE;
/*!40000 ALTER TABLE `ig_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `ig_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_events`
--

DROP TABLE IF EXISTS `ig_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_events` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `type` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `description` varchar(500) COLLATE utf8_polish_ci DEFAULT NULL,
  `location_id` bigint(20) DEFAULT '0',
  `user_id` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_events_location_id_idx` (`location_id`),
  KEY `fk_events_user_id_idx` (`user_id`),
  CONSTRAINT `fk_events_location_id` FOREIGN KEY (`location_id`) REFERENCES `ig_locations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_events_user_id` FOREIGN KEY (`user_id`) REFERENCES `ig_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_events`
--

LOCK TABLES `ig_events` WRITE;
/*!40000 ALTER TABLE `ig_events` DISABLE KEYS */;
/*!40000 ALTER TABLE `ig_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_events_attachments`
--

DROP TABLE IF EXISTS `ig_events_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_events_attachments` (
  `id` bigint(20) NOT NULL,
  `event_id` bigint(20) NOT NULL,
  `attachment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_ea_event_id_idx` (`event_id`),
  KEY `fk_ea_attachment_id_idx` (`attachment_id`),
  CONSTRAINT `fk_ea_attachment_id` FOREIGN KEY (`attachment_id`) REFERENCES `ig_attachments` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ea_event_id` FOREIGN KEY (`event_id`) REFERENCES `ig_events` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_events_attachments`
--

LOCK TABLES `ig_events_attachments` WRITE;
/*!40000 ALTER TABLE `ig_events_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `ig_events_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_events_users`
--

DROP TABLE IF EXISTS `ig_events_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_events_users` (
  `id` bigint(20) NOT NULL,
  `event_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_eu_event_id_idx` (`event_id`),
  KEY `fk_eu_user_id_idx` (`user_id`),
  CONSTRAINT `fk_eu_event_id` FOREIGN KEY (`event_id`) REFERENCES `ig_events` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_eu_user_id` FOREIGN KEY (`user_id`) REFERENCES `ig_users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_events_users`
--

LOCK TABLES `ig_events_users` WRITE;
/*!40000 ALTER TABLE `ig_events_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `ig_events_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_locations`
--

DROP TABLE IF EXISTS `ig_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_locations` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `type` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `address` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `city` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `zip_code` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `state` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `country` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `phone` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_locations`
--

LOCK TABLES `ig_locations` WRITE;
/*!40000 ALTER TABLE `ig_locations` DISABLE KEYS */;
INSERT INTO `ig_locations` VALUES (5,'Uniwersytet Rzeszowski','Szkoła wyższa','Rejtana 60','Rzeszów','35-564','Podkarpackie','Polska',NULL,NULL),(9,'Politechnika Rzeszowska','Szkoła wyższa','Rejtana 77','Rzeszów','35-564','Podkarpackie','Polska',NULL,NULL);
/*!40000 ALTER TABLE `ig_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_personal_data`
--

DROP TABLE IF EXISTS `ig_personal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_personal_data` (
  `id` bigint(20) NOT NULL,
  `first_name` varchar(12) COLLATE utf8_polish_ci NOT NULL,
  `last_name` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `pesel` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `phone` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_personal_data`
--

LOCK TABLES `ig_personal_data` WRITE;
/*!40000 ALTER TABLE `ig_personal_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `ig_personal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_personal_data_addresses`
--

DROP TABLE IF EXISTS `ig_personal_data_addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_personal_data_addresses` (
  `id` bigint(20) NOT NULL,
  `personal_data_id` bigint(20) NOT NULL,
  `address_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_pda_personal_data_id_idx` (`personal_data_id`),
  KEY `fk_pda_address_id_idx` (`address_id`),
  CONSTRAINT `fk_pda_address_id` FOREIGN KEY (`address_id`) REFERENCES `ig_addresses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_pda_personal_data_id` FOREIGN KEY (`personal_data_id`) REFERENCES `ig_personal_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_personal_data_addresses`
--

LOCK TABLES `ig_personal_data_addresses` WRITE;
/*!40000 ALTER TABLE `ig_personal_data_addresses` DISABLE KEYS */;
/*!40000 ALTER TABLE `ig_personal_data_addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_system_settings`
--

DROP TABLE IF EXISTS `ig_system_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_system_settings` (
  `id` bigint(20) NOT NULL,
  `max_login_attempts` int(11) NOT NULL DEFAULT '3',
  `account_lock_time` int(11) NOT NULL DEFAULT '15',
  `url_expiration_time` int(11) NOT NULL DEFAULT '2880',
  `email_address` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email_host` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email_port` int(4) DEFAULT NULL,
  `email_username` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `email_password` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_system_settings`
--

LOCK TABLES `ig_system_settings` WRITE;
/*!40000 ALTER TABLE `ig_system_settings` DISABLE KEYS */;
INSERT INTO `ig_system_settings` VALUES (1,3,15,2880,'system.inspigen@gmail.com','smtp.gmail.com',587,'system.inspigen','qwertyui7');
/*!40000 ALTER TABLE `ig_system_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_user_settings`
--

DROP TABLE IF EXISTS `ig_user_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_user_settings` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_user_settings`
--

LOCK TABLES `ig_user_settings` WRITE;
/*!40000 ALTER TABLE `ig_user_settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ig_user_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ig_users`
--

DROP TABLE IF EXISTS `ig_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ig_users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(12) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(32) COLLATE utf8_polish_ci NOT NULL,
  `email` varchar(40) COLLATE utf8_polish_ci NOT NULL,
  `role` varchar(10) COLLATE utf8_polish_ci NOT NULL DEFAULT 'ROLE_USER',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `account_non_locked` tinyint(1) NOT NULL DEFAULT '1',
  `account_non_expired` tinyint(1) DEFAULT '1',
  `credentials_non_expired` tinyint(1) DEFAULT '1',
  `password_token` varchar(16) COLLATE utf8_polish_ci NOT NULL,
  `password_token_expiration` timestamp NOT NULL DEFAULT '2014-01-01 00:01:01',
  `activation_token` varchar(16) COLLATE utf8_polish_ci DEFAULT NULL,
  `activation_token_expiration` timestamp NOT NULL DEFAULT '2014-01-01 00:01:01',
  `failed_logins` int(1) NOT NULL DEFAULT '0',
  `last_login_attempt` timestamp NULL DEFAULT '2014-01-01 00:01:01',
  `last_active` timestamp NULL DEFAULT NULL,
  `personal_data_id` bigint(20) DEFAULT NULL,
  `user_settings_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_users_personal_data_id_idx` (`personal_data_id`),
  KEY `fk_users_user_settings_id_idx` (`user_settings_id`),
  CONSTRAINT `fk_users_personal_data_id` FOREIGN KEY (`personal_data_id`) REFERENCES `ig_personal_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_user_settings_id` FOREIGN KEY (`user_settings_id`) REFERENCES `ig_user_settings` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ig_users`
--

LOCK TABLES `ig_users` WRITE;
/*!40000 ALTER TABLE `ig_users` DISABLE KEYS */;
INSERT INTO `ig_users` VALUES (1,'admin','c989e0f3b2dd29649dc98d6359c9bd4e','sebastian.sobiech@gmail.com','ROLE_ADMIN',1,1,1,1,'ajguiedyb3cx90dk','2015-02-04 07:37:12','tc1zpsdifsw65cxh','2014-10-07 06:15:05',0,'2015-01-23 15:33:09','2015-01-23 15:33:09',NULL,NULL);
/*!40000 ALTER TABLE `ig_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-21  0:23:57
