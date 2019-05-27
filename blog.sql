-- MySQL dump 10.17  Distrib 10.3.15-MariaDB, for Linux (x86_64)
--
-- Host: 206.189.220.160    Database: blog
-- ------------------------------------------------------
-- Server version	10.1.38-MariaDB-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `labelId` int(11) DEFAULT NULL,
  `detail` mediumtext,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_label` (`labelId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (2,'2019-05-17 09:03:03',1,'另一篇测试博文，随便写了一点内容','第二篇'),(4,'2019-05-20 06:31:52',1,'<div id=\"doc-content\"><textarea style=\"display:none;\">截至今日，博客系统前端显示初步完成，标签、文章正常显示，暂缺分页浏览。\n后端初步完成数据显示，数据操作尚未开工。\n写下第四篇文章，测试一下文章编写和发布浏览</textarea></div><script>var testEditor;$(function() {testEditor = editormd.markdownToHTML(\"doc-content\", {htmlDecode: \"style,script,iframe\",emoji: true,taskList: true,tex: true,flowChart: true, sequenceDiagram: true,codeFold: true,});});</script><script src=\"/md/lib/marked.min.js\"></script><script src=\"/md/lib/prettify.min.js\"></script><script src=\"/md/lib/raphael.min.js\"></script><script src=\"/md/lib/underscore.min.js\"></script><script src=\"/md/lib/sequence-diagram.min.js\"></script><script src=\"/md/lib/flowchart.min.js\"></script><script src=\"/md/lib/jquery.flowchart.min.js\"></script><script src=\"/md/lib/editormd.min.js\"></script>','第四篇'),(5,'2019-05-23 03:56:47',1,'<div id=\"doc-content\"><textarea style=\"display:none;\">截至今日，完成了文章详情页的展示和评论功能。\n后台管理进一步完善，要求登录后才能操作\n分页功能等再有一些文章之后添加吧</textarea></div><script>var testEditor;$(function() {testEditor = editormd.markdownToHTML(\"doc-content\", {htmlDecode: \"style,script,iframe\",emoji: true,taskList: true,tex: true,flowChart: true, sequenceDiagram: true,codeFold: true,});});</script><script src=\"/md/lib/marked.min.js\"></script><script src=\"/md/lib/prettify.min.js\"></script><script src=\"/md/lib/raphael.min.js\"></script><script src=\"/md/lib/underscore.min.js\"></script><script src=\"/md/lib/sequence-diagram.min.js\"></script><script src=\"/md/lib/flowchart.min.js\"></script><script src=\"/md/lib/jquery.flowchart.min.js\"></script><script src=\"/md/lib/editormd.min.js\"></script>','第五篇'),(6,'2019-05-24 04:23:48',1,'<div id=\"doc-content\"><textarea style=\"display:none;\">今天把主页的分页写好了，考虑要不要在后台管理建立分页。\n虽然目前没有这个需求，但是以后的事情，谁能说得清楚呢</textarea></div><script>var testEditor;$(function() {testEditor = editormd.markdownToHTML(\"doc-content\", {htmlDecode: \"style,script,iframe\",emoji: true,taskList: true,tex: true,flowChart: true, sequenceDiagram: true,codeFold: true,});});</script><script src=\"/md/lib/marked.min.js\"></script><script src=\"/md/lib/prettify.min.js\"></script><script src=\"/md/lib/raphael.min.js\"></script><script src=\"/md/lib/underscore.min.js\"></script><script src=\"/md/lib/sequence-diagram.min.js\"></script><script src=\"/md/lib/flowchart.min.js\"></script><script src=\"/md/lib/jquery.flowchart.min.js\"></script><script src=\"/md/lib/editormd.min.js\"></script>','第六篇');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `articleId` int(11) DEFAULT NULL,
  `name` varchar(25) DEFAULT NULL,
  `isRead` tinyint(1) DEFAULT '0',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_article` (`articleId`) USING BTREE,
  CONSTRAINT `fk_article` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'comment#1','u1@123.com',NULL,'u1',1,'2019-05-23 12:37:25'),(2,'comment#2','u2@123.com',NULL,'u2',1,'2019-05-23 12:37:25'),(3,'第一条评论','aagu@foxmail.com',NULL,NULL,1,'2019-05-23 12:37:25'),(4,'第二条评论','aaguisme@gmail.com',NULL,NULL,1,'2019-05-23 12:37:25'),(5,'这一篇也要有我的足迹','aaguisme@gmail.com',2,NULL,1,'2019-05-23 12:37:25'),(6,'怎么没有第三篇？','aaguisme@gmail.com',4,NULL,1,'2019-05-23 12:37:25'),(7,'再来一条评论','aaguisme@gmail.com',4,NULL,1,'2019-05-23 12:37:25'),(8,'我又来评论了，嘿嘿','aaguisme@gmail.com',5,NULL,1,'2019-05-23 12:48:27');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `label`
--

DROP TABLE IF EXISTS `label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `label`
--

LOCK TABLES `label` WRITE;
/*!40000 ALTER TABLE `label` DISABLE KEYS */;
INSERT INTO `label` VALUES (1,-1,'默认标签'),(2,-1,'编程'),(3,2,'Java'),(4,2,'Android');
/*!40000 ALTER TABLE `label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'aagu','aagu098',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-24 22:25:37
