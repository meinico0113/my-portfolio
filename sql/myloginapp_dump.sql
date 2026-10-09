USE test;
-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'bubu','koyuki0104@icloud.com','koyu0104',1,NULL),(3,'管理者テスト','koyuki130104@icloud.com',NULL,0,'admin'),(4,'一般テスト','koyuki130104@icloud.com',NULL,0,'user');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (2,'アカウント'),(13,'テスト2'),(15,'ログイン'),(16,'テスト８');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT '未対応',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (3,'テスト2','テスト','未対応','2026-07-25 05:32:15'),(5,'アカウント','アカウント削除のやり方がわからない。','未対応','2026-07-25 05:33:31'),(12,'ログイン','ログインができません。','未対応','2026-08-23 07:20:16'),(13,'テスト８','困っている。\r\n助けて。','対応中','2026-08-26 11:26:43');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes_log`
--

DROP TABLE IF EXISTS `likes_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `target_user_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes_log`
--

LOCK TABLES `likes_log` WRITE;
/*!40000 ALTER TABLE `likes_log` DISABLE KEYS */;
INSERT INTO `likes_log` VALUES (1,13,'2026-08-24 13:11:18'),(2,11,'2026-08-24 13:11:18'),(3,10,'2026-08-24 13:11:18'),(4,2,'2026-08-24 13:11:18'),(5,13,'2026-08-24 13:11:18'),(6,11,'2026-08-24 13:11:18'),(7,10,'2026-08-24 13:11:18'),(8,2,'2026-08-24 13:11:18'),(9,13,'2026-08-24 13:11:18'),(10,11,'2026-08-24 13:11:18'),(11,2,'2026-08-24 13:11:18'),(12,11,'2026-08-24 13:11:18'),(13,2,'2026-08-24 13:11:18'),(14,11,'2026-08-24 13:11:18'),(15,2,'2026-08-24 13:11:18'),(16,11,'2026-08-24 13:11:18'),(17,2,'2026-08-24 13:11:18'),(18,11,'2026-08-24 13:11:18'),(19,2,'2026-08-24 13:11:18'),(20,11,'2026-08-24 13:11:18'),(21,2,'2026-08-24 13:11:18'),(22,11,'2026-08-24 13:11:18'),(23,2,'2026-08-24 13:11:18'),(24,11,'2026-08-24 13:11:18'),(25,2,'2026-08-24 13:11:18'),(26,11,'2026-08-24 13:11:18'),(27,2,'2026-08-24 13:11:18'),(28,11,'2026-08-24 13:11:18'),(29,11,'2026-08-24 13:11:18'),(30,11,'2026-08-24 13:11:18'),(31,11,'2026-08-24 13:11:18'),(32,11,'2026-08-24 13:12:49'),(33,11,'2026-08-24 13:12:49'),(34,11,'2026-08-24 13:12:58'),(35,10,'2026-08-25 13:58:38'),(36,10,'2026-08-25 13:58:38'),(37,13,'2026-08-26 07:40:41'),(38,13,'2026-08-26 07:40:42'),(39,10,'2026-09-17 04:25:08'),(40,10,'2026-09-17 04:25:08');
/*!40000 ALTER TABLE `likes_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `like_count` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,2,'ブランの初投稿',8),(3,2,'ブランの2つ目の投稿',3);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` varchar(10) DEFAULT 'user',
  `status` int DEFAULT '0',
  `nickname` varchar(255) DEFAULT NULL,
  `kana` varchar(255) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `profile` text,
  `profile_image` varchar(255) DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  `is_deleted` int DEFAULT '0',
  `likes` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'ブラン','11111111','bubu@example.com','user',1,'ブラン','ぶらん','male',3,'保護猫ブラン。鳴き声がブサイク。甘えたがり☺️','illust1129.png',NULL,0,11),(5,'管理者','12345678','koyu@example.com','admin',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0),(10,'クロ','12345678','kuroro@example.com','user',0,NULL,'くろ','male',3,'保護猫くろちゃん。お喋り！','images-4.png',NULL,0,6),(11,'イヴ','12345678','ivu@example.com','user',0,NULL,'いゔ','female',7,'保護猫イヴちゃん。実家の猫。ぽっちゃりツンデレ。','images-2.png',NULL,0,18),(13,'コウ','1111','kou@example.com','user',0,NULL,'こう','female',7,'保護猫コウちゃん。実家の猫。顔がいい。','images-3.png',NULL,0,5);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-25 15:32:21
