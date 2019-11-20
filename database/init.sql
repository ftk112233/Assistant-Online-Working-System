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
-- Table structure for table `assistant`
--

DROP TABLE IF EXISTS `assistant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assistant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `assistant_work_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '助教工号',
  `assistant_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '助教姓名，应当唯一，如果有重名需要另外特殊处理',
  `assistant_sex` enum('男','女','') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '助教性别',
  `assistant_depart` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '助教部门',
  `assistant_campus` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `assistant_phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '助教手机',
  `assistant_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '助教备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `assistant_work_id_UNIQUE` (`assistant_work_id`) COMMENT '助教工号唯一',
  UNIQUE KEY `assistant_name_UNIQUE` (`assistant_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assistant`
--

LOCK TABLES `assistant` WRITE;
/*!40000 ALTER TABLE `assistant` DISABLE KEYS */;
INSERT INTO `assistant` VALUES (3,'2019-11-13 08:37:11','2019-11-19 05:32:51','12234','asdaas','',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `assistant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `class_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '班级编码，2019年新东方优能中学部的班级编码为12位，U6MCFB020001，其中‘u''表示优能中学部，6表示六年级，M学科，C为班型：志高，F为班级规模：25人，B表示季度，’02‘表示曹杨校区，''0001''为序号。所以这里不另外加年级，学科等字段，以便后续班级编码规则改变系统更新',
  `class_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '班级名称',
  `class_year` year(4) DEFAULT NULL COMMENT '班级上课学期，如：''2019''',
  `class_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '班级上课时间，如周日8:00-10:：00',
  `class_times` int(11) DEFAULT NULL COMMENT '班级上课次数，有具体班级情况和学期决定',
  `class_teacher_id` bigint(20) DEFAULT NULL COMMENT '班级任课教师的主键id',
  `class_assistant_id` bigint(20) DEFAULT NULL COMMENT '班级助教的主键id',
  `classroom` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '班级上课教室，如：313',
  `class_teacher_requirement` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '任课教师开课要求',
  `class_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '班级备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `class_id_UNIQUE` (`class_id`) COMMENT '班级编码唯一',
  KEY `c_a_idx` (`class_assistant_id`),
  KEY `c_t_idx` (`class_teacher_id`),
  CONSTRAINT `c_a` FOREIGN KEY (`class_assistant_id`) REFERENCES `assistant` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `c_t` FOREIGN KEY (`class_teacher_id`) REFERENCES `teacher` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'2019-11-12 15:28:52','2019-11-12 15:29:06','U6MCFB020001','啊啊啊啊',2019,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `student_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '学员号',
  `student_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学员姓名',
  `student_sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学员性别',
  `student_phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学生联系方式',
  `student_phone_backup` char(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学生联系方式（备用）',
  `student_school` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学生就读学校',
  `student_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学生备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_id_UNIQUE` (`student_id`) COMMENT '学员号，唯一' /*!80000 INVISIBLE */
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_class`
--

DROP TABLE IF EXISTS `student_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `student_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `student_id` bigint(20) NOT NULL COMMENT '上课学生的主键id',
  `class_id` bigint(20) NOT NULL COMMENT '上课班级的主键id',
  `register_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '学生进班时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '学生与该班级的备注',
  PRIMARY KEY (`id`),
  KEY `sc_c_idx` (`class_id`),
  KEY `sc_s_idx` (`student_id`),
  CONSTRAINT `sc_c` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sc_s` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_class`
--

LOCK TABLES `student_class` WRITE;
/*!40000 ALTER TABLE `student_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `teacher_work_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '教师工号',
  `teacher_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '教师姓名',
  `teacher_sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '教师性别',
  `teacher_phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '教师电话',
  `teacher_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '教师备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `teacher_work_id_UNIQUE` (`teacher_work_id`) COMMENT '工号唯一'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (2,'2019-11-12 15:28:16','2019-11-12 15:32:00','654321','佟立雪',NULL,NULL,NULL);
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_work_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户的工号',
  `user_id_card` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名，可以自定义，初始值为a_身份证',
  `user_password` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户密码',
  `user_salt` char(32) COLLATE utf8_bin NOT NULL COMMENT '用户密码加密用的盐，uuid，首次插入时生成不可修改',
  `user_real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '张三' COMMENT '用户的真实姓名',
  `user_role` enum('管理员','助教长','助教','教师','学管','游客') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户身份',
  `user_icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户的头像存储的地址路径',
  `user_email` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户邮箱',
  `user_phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户手机',
  `user_remark` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`) COMMENT '用户名唯一，初始值为身份证' /*!80000 INVISIBLE */,
  UNIQUE KEY `user_work_id_UNIQUE` (`user_work_id`) COMMENT '用户工号唯一',
  UNIQUE KEY `user_phone_UNIQUE` (`user_phone`) COMMENT '用户电话唯一',
  UNIQUE KEY `user_email_UNIQUE` (`user_email`) COMMENT '用户邮箱唯一',
  UNIQUE KEY `user_id_card_UNIQUE` (`user_id_card`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'2019-11-13 09:54:30','2019-11-20 02:52:28','12345678','310107199808314912','kurochan','7be7b5a8ccdb5b88e5bca1b2118b2437','569cadcd0b4011eaab1d54ab3aa53988','金之贇','管理员','2019_11_18_07_01_09_user_icon_3.jpg','929703621@qq.com','13681864361','1'),(4,'2019-11-15 14:48:53','2019-11-20 02:52:28','000000',NULL,'admin1','1a6243153caf96d40468f520b19b8f0d','763ea4bc0b4011eaab1d54ab3aa53988','超级酷乐酱','管理员',NULL,'1536524029@qq.com',NULL,''),(6,'2019-11-19 06:33:51','2019-11-20 02:52:28','000001','65290119991230041X','xg111111','b93cc724cb4ac68589076bc41cd92d19','79727be80b4011eaab1d54ab3aa53988','刘子晗','学管',NULL,NULL,NULL,'1111111'),(7,'2019-11-20 03:05:49','2019-11-20 03:05:49',NULL,NULL,'k11111','11fdbcb9180f6c83776bf6091182ae98','c63c87e6fa19453eba6ab0fdc7fc1d6e','张三','管理员',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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

-- Dump completed on 2019-11-20 11:27:26
