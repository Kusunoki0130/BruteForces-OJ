/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : bfoj

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-12-19 20:55:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admins
-- ----------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  `ad_id` int NOT NULL AUTO_INCREMENT,
  `ad_username` varchar(32) NOT NULL,
  `ad_password` varchar(32) NOT NULL,
  PRIMARY KEY (`ad_id`),
  UNIQUE KEY `ad_username_unique` (`ad_username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for blogs
-- ----------------------------
DROP TABLE IF EXISTS `blogs`;
CREATE TABLE `blogs` (
  `blog_id` int NOT NULL AUTO_INCREMENT,
  `blog_title` varchar(128) NOT NULL,
  `blog_src` varchar(255) NOT NULL,
  `blog_time` timestamp NOT NULL,
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for contests
-- ----------------------------
DROP TABLE IF EXISTS `contests`;
CREATE TABLE `contests` (
  `con_id` int NOT NULL AUTO_INCREMENT,
  `con_title` varchar(64) NOT NULL,
  `ad_id` int NOT NULL,
  `startTime` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `endTime` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `participate` int NOT NULL,
  PRIMARY KEY (`con_id`),
  KEY `ad_id` (`ad_id`),
  CONSTRAINT `ad_id` FOREIGN KEY (`ad_id`) REFERENCES `admins` (`ad_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for con_pro
-- ----------------------------
DROP TABLE IF EXISTS `con_pro`;
CREATE TABLE `con_pro` (
  `con_id` int NOT NULL,
  `pro_id` int NOT NULL,
  KEY `con_id1` (`con_id`),
  KEY `pro_id1` (`pro_id`),
  CONSTRAINT `con_id1` FOREIGN KEY (`con_id`) REFERENCES `contests` (`con_id`),
  CONSTRAINT `pro_id1` FOREIGN KEY (`pro_id`) REFERENCES `problems` (`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for con_pro_stu
-- ----------------------------
DROP TABLE IF EXISTS `con_pro_stu`;
CREATE TABLE `con_pro_stu` (
  `cps` varchar(255) NOT NULL,
  `con_id` int NOT NULL,
  `pro_id` int NOT NULL,
  `stu_id` int NOT NULL,
  `ifAccepted` int NOT NULL,
  `attemptTimes` int NOT NULL,
  `solveTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cps`),
  KEY `con_id` (`con_id`),
  KEY `pro_id` (`pro_id`),
  KEY `stu_id` (`stu_id`),
  CONSTRAINT `con_id` FOREIGN KEY (`con_id`) REFERENCES `contests` (`con_id`),
  CONSTRAINT `pro_id` FOREIGN KEY (`pro_id`) REFERENCES `problems` (`pro_id`),
  CONSTRAINT `stu_id` FOREIGN KEY (`stu_id`) REFERENCES `students` (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for con_stu
-- ----------------------------
DROP TABLE IF EXISTS `con_stu`;
CREATE TABLE `con_stu` (
  `con_id` int NOT NULL,
  `stu_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for problems
-- ----------------------------
DROP TABLE IF EXISTS `problems`;
CREATE TABLE `problems` (
  `pro_id` int NOT NULL AUTO_INCREMENT,
  `pro_title` varchar(32) NOT NULL,
  `pro_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pro_input` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pro_output` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pro_inputsample` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pro_outputsample` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pro_timelimit` int NOT NULL,
  `pro_memorylimit` int NOT NULL,
  `pro_submitted` int NOT NULL,
  `pro_accepted` int NOT NULL,
  `pro_datanum` int NOT NULL,
  `pro_data` varchar(255) NOT NULL,
  `isHidden` int NOT NULL,
  PRIMARY KEY (`pro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `stu_id` int NOT NULL AUTO_INCREMENT,
  `stu_username` varchar(32) NOT NULL,
  `stu_password` varchar(32) NOT NULL,
  `stu_submitted` int NOT NULL,
  `stu_solved` int NOT NULL,
  `stu_email` varchar(32) NOT NULL,
  `stu_phone` varchar(11) NOT NULL,
  PRIMARY KEY (`stu_id`),
  UNIQUE KEY `stu_username_unique` (`stu_username`) USING BTREE,
  UNIQUE KEY `stu_email_unique` (`stu_email`),
  UNIQUE KEY `stu_phone_unique` (`stu_phone`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for submissions
-- ----------------------------
DROP TABLE IF EXISTS `submissions`;
CREATE TABLE `submissions` (
  `sub_id` int NOT NULL AUTO_INCREMENT,
  `con_id` int NOT NULL,
  `pro_id` int NOT NULL,
  `stu_id` int NOT NULL,
  `sub_src` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sub_time` timestamp NOT NULL,
  `result` varchar(32) NOT NULL,
  `language` varchar(255) NOT NULL,
  `timelim` int NOT NULL,
  `memorylim` int NOT NULL,
  `length` int NOT NULL,
  PRIMARY KEY (`sub_id`),
  KEY `sub_pro` (`pro_id`),
  KEY `sub_stu` (`stu_id`),
  CONSTRAINT `sub_pro` FOREIGN KEY (`pro_id`) REFERENCES `problems` (`pro_id`),
  CONSTRAINT `sub_stu` FOREIGN KEY (`stu_id`) REFERENCES `students` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;
