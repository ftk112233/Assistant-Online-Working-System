CREATE DATABASE  IF NOT EXISTS `aows` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `aows`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: aows
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `role_permisson`
--

DROP TABLE IF EXISTS `role_permisson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role_permisson` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `role` enum('管理员','助教长','助教','教师','学管','游客') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色与用户相对应',
  `perm` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限url',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role`,`perm`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permisson`
--

LOCK TABLES `role_permisson` WRITE;
/*!40000 ALTER TABLE `role_permisson` DISABLE KEYS */;
INSERT INTO `role_permisson` VALUES (15,'2019-11-22 02:38:56','2019-12-05 10:15:45','管理员','assistant:admin','助教信息管理'),(34,'2019-11-26 05:50:40','2019-11-26 05:50:40','管理员','class:admin:delete','删除班级的权限，助教长以上级别才有'),(37,'2019-11-26 05:50:53','2019-11-26 05:50:53','管理员','class:admin:import','excel导入班级的权限，助教长以上级别才有'),(31,'2019-11-26 05:50:25','2019-11-26 05:50:25','管理员','class:admin:insert','添加班级的权限，助教长以上级别才有'),(28,'2019-11-26 05:50:06','2019-11-26 05:50:06','管理员','class:admin:update','编辑班级的权限，助教长以上级别才有'),(70,'2019-11-28 15:02:16','2019-11-28 15:02:16','管理员','missLessonStudent:admin:delete','删除补课学员信息的权限，助教长以上级别才有'),(67,'2019-11-28 15:01:50','2019-11-28 15:01:50','管理员','missLessonStudent:admin:insert','添加补课学员信息的权限，助教长以上级别才有'),(64,'2019-11-28 15:01:29','2019-11-28 15:01:29','管理员','missLessonStudent:admin:update','编辑补课学员信息的权限，助教长以上级别才有'),(14,'2019-11-21 12:09:53','2019-11-21 14:23:05','管理员','permission:admin','角色权限的权限，管理员才有'),(89,'2019-12-03 15:13:05','2019-12-03 15:13:05','管理员','question:admin:delete','删除登录问题的权限，助教长以上级别才有'),(84,'2019-12-03 15:12:51','2019-12-03 15:12:51','管理员','question:admin:insert','添加登录问题的权限，助教以上级别才有'),(81,'2019-12-03 15:11:46','2019-12-03 15:11:46','管理员','question:admin:update','编辑登录问题的权限，助教长以上级别才有'),(58,'2019-11-26 15:30:00','2019-11-26 15:30:00','管理员','student:admin:delete','删除学员个人信息的权限，助教长以上级别才有'),(61,'2019-11-26 15:30:19','2019-11-26 15:30:19','管理员','student:admin:import','excel导入学员个人信息的权限，助教长以上级别才有'),(55,'2019-11-26 15:29:47','2019-11-26 15:29:47','管理员','student:admin:insert','添加学员个人信息的权限，助教长以上级别才有'),(52,'2019-11-26 15:29:32','2019-11-26 15:29:32','管理员','student:admin:update','编辑学员个人信息的权限，助教长以上级别才有'),(46,'2019-11-26 09:31:48','2019-11-26 09:31:48','管理员','studentAndClass:admin:delete','删除学员上课信息的权限，助教长以上级别才有'),(43,'2019-11-26 09:31:32','2019-11-26 09:31:32','管理员','studentAndClass:admin:insert','添加学员上课信息的权限，助教长以上级别才有'),(40,'2019-11-26 09:31:13','2019-11-26 09:31:13','管理员','studentAndClass:admin:update','编辑学员上课信息的权限，助教长以上级别才有'),(77,'2019-11-29 03:05:36','2019-11-29 03:05:36','管理员','system:admin','推送公告等系统设置，只能管理员'),(25,'2019-11-24 09:06:45','2019-11-24 09:06:45','管理员','teacher:admin','教师管理'),(21,'2019-11-24 08:48:42','2019-11-24 08:48:42','管理员','toolbox:assistant','百宝箱助教区的权限，助教以上级别才有'),(18,'2019-11-24 08:48:03','2019-11-24 08:48:03','管理员','toolbox:assistantAdministrator','百宝箱学管区的权限，助教长以上级别才有'),(103,'2019-12-06 11:13:43','2019-12-06 11:13:43','管理员','usefulInformation:admin:delete','删除常用信息的权限，助教长以上级别才有'),(100,'2019-12-06 11:13:28','2019-12-06 11:13:28','管理员','usefulInformation:admin:insert','添加常用信息的权限，助教长以上级别才有'),(97,'2019-12-06 11:13:13','2019-12-06 11:13:13','管理员','usefulInformation:admin:update','编辑常用信息的权限，助教长以上级别才有'),(2,'2019-11-21 05:16:29','2019-11-21 09:41:16','管理员','user:admin','用户管理权限'),(78,'2019-12-02 01:36:44','2019-12-02 01:36:44','管理员','user:admin:import','用户信息导入，管理员和学管和助教长有'),(3,'2019-11-21 12:00:09','2019-11-21 12:00:09','管理员','user:basic','基本用户信息的操作，除游客外都有权限'),(109,'2019-12-08 07:33:41','2019-12-08 07:33:41','管理员','user:message:all','发全体消息，管理员和学管有'),(106,'2019-12-08 07:33:27','2019-12-08 07:33:27','管理员','user:message:many','批量发消息，管理员和学管和助教长有'),(8,'2019-11-21 12:09:19','2019-11-21 12:09:19','管理员','user:showIcon','访问获取头像接口'),(17,'2019-11-22 02:38:56','2019-11-22 02:38:56','助教长','assistant:admin','助教信息管理'),(36,'2019-11-26 05:50:40','2019-11-26 05:50:40','助教长','class:admin:delete','删除班级的权限，助教长以上级别才有'),(39,'2019-11-26 05:50:53','2019-11-26 05:50:53','助教长','class:admin:import','excel导入班级的权限，助教长以上级别才有'),(33,'2019-11-26 05:50:25','2019-11-26 05:50:25','助教长','class:admin:insert','添加班级的权限，助教长以上级别才有'),(30,'2019-11-26 05:50:06','2019-11-26 05:50:06','助教长','class:admin:update','编辑班级的权限，助教长以上级别才有'),(72,'2019-11-28 15:02:16','2019-11-28 15:02:16','助教长','missLessonStudent:admin:delete','删除补课学员信息的权限，助教长以上级别才有'),(69,'2019-11-28 15:01:50','2019-11-28 15:01:50','助教长','missLessonStudent:admin:insert','添加补课学员信息的权限，助教长以上级别才有'),(66,'2019-11-28 15:01:29','2019-11-28 15:01:29','助教长','missLessonStudent:admin:update','编辑补课学员信息的权限，助教长以上级别才有'),(91,'2019-12-03 15:13:05','2019-12-03 15:13:05','助教长','question:admin:delete','删除登录问题的权限，助教长以上级别才有'),(86,'2019-12-03 15:12:51','2019-12-03 15:12:51','助教长','question:admin:insert','添加登录问题的权限，助教以上级别才有'),(83,'2019-12-03 15:11:46','2019-12-03 15:11:46','助教长','question:admin:update','编辑登录问题的权限，助教长以上级别才有'),(60,'2019-11-26 15:30:00','2019-11-26 15:30:00','助教长','student:admin:delete','删除学员个人信息的权限，助教长以上级别才有'),(63,'2019-11-26 15:30:19','2019-11-26 15:30:19','助教长','student:admin:import','excel导入学员个人信息的权限，助教长以上级别才有'),(57,'2019-11-26 15:29:47','2019-11-26 15:29:47','助教长','student:admin:insert','添加学员个人信息的权限，助教长以上级别才有'),(54,'2019-11-26 15:29:32','2019-11-26 15:29:32','助教长','student:admin:update','编辑学员个人信息的权限，助教长以上级别才有'),(48,'2019-11-26 09:31:48','2019-11-26 09:31:48','助教长','studentAndClass:admin:delete','删除学员上课信息的权限，助教长以上级别才有'),(45,'2019-11-26 09:31:33','2019-11-26 09:31:33','助教长','studentAndClass:admin:insert','添加学员上课信息的权限，助教长以上级别才有'),(42,'2019-11-26 09:31:14','2019-11-26 09:31:14','助教长','studentAndClass:admin:update','编辑学员上课信息的权限，助教长以上级别才有'),(27,'2019-11-24 09:06:45','2019-11-24 09:06:45','助教长','teacher:admin','教师管理'),(23,'2019-11-24 08:48:42','2019-11-24 08:48:42','助教长','toolbox:assistant','百宝箱助教区的权限，助教以上级别才有'),(20,'2019-11-24 08:48:03','2019-11-24 08:48:03','助教长','toolbox:assistantAdministrator','百宝箱学管区的权限，助教长以上级别才有'),(105,'2019-12-06 11:13:43','2019-12-06 11:13:43','助教长','usefulInformation:admin:delete','删除常用信息的权限，助教长以上级别才有'),(102,'2019-12-06 11:13:28','2019-12-06 11:13:28','助教长','usefulInformation:admin:insert','添加常用信息的权限，助教长以上级别才有'),(99,'2019-12-06 11:13:13','2019-12-06 11:13:13','助教长','usefulInformation:admin:update','编辑常用信息的权限，助教长以上级别才有'),(80,'2019-12-02 01:36:44','2019-12-02 01:36:44','助教长','user:admin:import','用户信息导入，管理员和学管和助教长有'),(5,'2019-11-21 12:00:10','2019-11-21 12:00:10','助教长','user:basic','基本用户信息的操作，除游客外都有权限'),(108,'2019-12-08 07:33:28','2019-12-08 07:33:28','助教长','user:message:many','批量发消息，管理员和学管和助教长有'),(10,'2019-11-21 12:09:19','2019-11-21 12:09:19','助教长','user:showIcon','访问获取头像接口'),(87,'2019-12-03 15:12:52','2019-12-03 15:12:52','助教','question:admin:insert','添加登录问题的权限，助教以上级别才有'),(24,'2019-11-24 08:48:42','2019-11-24 08:48:42','助教','toolbox:assistant','百宝箱助教区的权限，助教以上级别才有'),(6,'2019-11-21 12:00:10','2019-11-21 12:00:10','助教','user:basic','基本用户信息的操作，除游客外都有权限'),(11,'2019-11-21 12:09:19','2019-11-21 12:09:19','助教','user:showIcon','访问获取头像接口'),(88,'2019-12-03 15:12:52','2019-12-03 15:12:52','教师','question:admin:insert','添加登录问题的权限，助教以上级别才有'),(76,'2019-11-28 15:03:20','2019-11-28 15:03:20','教师','toolbox:assistant','百宝箱助教区的权限，教师助教以上级别才有'),(7,'2019-11-21 12:00:10','2019-11-21 12:00:10','教师','user:basic','基本用户信息的操作，除游客外都有权限'),(12,'2019-11-21 12:09:19','2019-11-21 12:09:19','教师','user:showIcon','访问获取头像接口'),(16,'2019-11-22 02:38:56','2019-11-22 02:38:56','学管','assistant:admin','助教信息管理'),(35,'2019-11-26 05:50:40','2019-11-26 05:50:40','学管','class:admin:delete','删除班级的权限，助教长以上级别才有'),(38,'2019-11-26 05:50:53','2019-11-26 05:50:53','学管','class:admin:import','excel导入班级的权限，助教长以上级别才有'),(32,'2019-11-26 05:50:25','2019-11-26 05:50:25','学管','class:admin:insert','添加班级的权限，助教长以上级别才有'),(29,'2019-11-26 05:50:06','2019-11-26 05:50:06','学管','class:admin:update','编辑班级的权限，助教长以上级别才有'),(71,'2019-11-28 15:02:16','2019-11-28 15:02:16','学管','missLessonStudent:admin:delete','删除补课学员信息的权限，助教长以上级别才有'),(68,'2019-11-28 15:01:50','2019-11-28 15:01:50','学管','missLessonStudent:admin:insert','添加补课学员信息的权限，助教长以上级别才有'),(65,'2019-11-28 15:01:29','2019-11-28 15:01:29','学管','missLessonStudent:admin:update','编辑补课学员信息的权限，助教长以上级别才有'),(90,'2019-12-03 15:13:05','2019-12-03 15:13:05','学管','question:admin:delete','删除登录问题的权限，助教长以上级别才有'),(85,'2019-12-03 15:12:51','2019-12-03 15:12:51','学管','question:admin:insert','添加登录问题的权限，助教以上级别才有'),(82,'2019-12-03 15:11:46','2019-12-03 15:11:46','学管','question:admin:update','编辑登录问题的权限，助教长以上级别才有'),(59,'2019-11-26 15:30:00','2019-11-26 15:30:00','学管','student:admin:delete','删除学员个人信息的权限，助教长以上级别才有'),(62,'2019-11-26 15:30:19','2019-11-26 15:30:19','学管','student:admin:import','excel导入学员个人信息的权限，助教长以上级别才有'),(56,'2019-11-26 15:29:47','2019-11-26 15:29:47','学管','student:admin:insert','添加学员个人信息的权限，助教长以上级别才有'),(53,'2019-11-26 15:29:32','2019-11-26 15:29:32','学管','student:admin:update','编辑学员个人信息的权限，助教长以上级别才有'),(47,'2019-11-26 09:31:48','2019-11-26 09:31:48','学管','studentAndClass:admin:delete','删除学员上课信息的权限，助教长以上级别才有'),(44,'2019-11-26 09:31:33','2019-11-26 09:31:33','学管','studentAndClass:admin:insert','添加学员上课信息的权限，助教长以上级别才有'),(41,'2019-11-26 09:31:14','2019-11-26 09:31:14','学管','studentAndClass:admin:update','编辑学员上课信息的权限，助教长以上级别才有'),(26,'2019-11-24 09:06:45','2019-11-24 09:06:45','学管','teacher:admin','教师管理'),(22,'2019-11-24 08:48:42','2019-11-24 08:48:42','学管','toolbox:assistant','百宝箱助教区的权限，助教以上级别才有'),(19,'2019-11-24 08:48:03','2019-11-24 08:48:03','学管','toolbox:assistantAdministrator','百宝箱学管区的权限，助教长以上级别才有'),(104,'2019-12-06 11:13:43','2019-12-06 11:13:43','学管','usefulInformation:admin:delete','删除常用信息的权限，助教长以上级别才有'),(101,'2019-12-06 11:13:28','2019-12-06 11:13:28','学管','usefulInformation:admin:insert','添加常用信息的权限，助教长以上级别才有'),(98,'2019-12-06 11:13:13','2019-12-06 11:13:13','学管','usefulInformation:admin:update','编辑常用信息的权限，助教长以上级别才有'),(1,'2019-11-21 05:16:29','2019-11-21 09:41:21','学管','user:admin','用户管理权限'),(79,'2019-12-02 01:36:44','2019-12-02 01:36:44','学管','user:admin:import','用户信息导入，管理员和学管和助教长有'),(4,'2019-11-21 12:00:09','2019-11-21 12:00:09','学管','user:basic','基本用户信息的操作，除游客外都有权限'),(110,'2019-12-08 07:33:41','2019-12-08 07:33:41','学管','user:message:all','发全体消息，管理员和学管有'),(107,'2019-12-08 07:33:28','2019-12-08 07:33:28','学管','user:message:many','批量发消息，管理员和学管和助教长有'),(9,'2019-11-21 12:09:19','2019-11-21 12:09:19','学管','user:showIcon','访问获取头像接口'),(13,'2019-11-21 12:09:19','2019-11-21 12:09:19','游客','user:showIcon','访问获取头像接口');
/*!40000 ALTER TABLE `role_permisson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_message`
--

DROP TABLE IF EXISTS `user_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  `user_from_id` bigint(20) DEFAULT NULL,
  `message_title` varchar(100) COLLATE utf8_bin NOT NULL,
  `message_content` varchar(1000) COLLATE utf8_bin NOT NULL,
  `message_picture` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `message_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `read` tinyint(1) NOT NULL DEFAULT '0',
  `message_remark` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_f_idx` (`user_from_id`),
  KEY `u_idx` (`user_id`),
  CONSTRAINT `u` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `u_f` FOREIGN KEY (`user_from_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_message`
--

LOCK TABLES `user_message` WRITE;
/*!40000 ALTER TABLE `user_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'aows'
--

--
-- Dumping routines for database 'aows'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-08 15:52:56
