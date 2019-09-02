/*
Navicat MySQL Data Transfer

Source Server         : shortTerm
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : kitchenhelper

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2019-09-02 10:28:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admininfo
-- ----------------------------
DROP TABLE IF EXISTS `admininfo`;
CREATE TABLE `admininfo` (
  `adminNo` varchar(200) NOT NULL,
  `adminType` varchar(200) NOT NULL,
  `adminName` varchar(200) NOT NULL,
  `adminPassword` varchar(200) NOT NULL,
  PRIMARY KEY (`adminNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admininfo
-- ----------------------------
INSERT INTO `admininfo` VALUES ('admin', '管理员', 'admin', 'admin');

-- ----------------------------
-- Table structure for foodbuy
-- ----------------------------
DROP TABLE IF EXISTS `foodbuy`;
CREATE TABLE `foodbuy` (
  `buyNo` int(11) NOT NULL AUTO_INCREMENT,
  `foodNo` varchar(200) NOT NULL,
  `amount` double NOT NULL,
  `buyStatus` varchar(200) NOT NULL,
  PRIMARY KEY (`buyNo`),
  KEY `FK_Reference_8` (`foodNo`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`foodNo`) REFERENCES `foodinfo` (`foodNo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of foodbuy
-- ----------------------------
INSERT INTO `foodbuy` VALUES ('2', '12', '1', 'ordered');
INSERT INTO `foodbuy` VALUES ('4', '12', '3', 'ordered');
INSERT INTO `foodbuy` VALUES ('5', '11', '2', 'ordered');

-- ----------------------------
-- Table structure for foodinfo
-- ----------------------------
DROP TABLE IF EXISTS `foodinfo`;
CREATE TABLE `foodinfo` (
  `foodNo` varchar(200) NOT NULL,
  `foodTypeNo` int(11) NOT NULL,
  `foodName` varchar(200) NOT NULL,
  `foodPrice` double NOT NULL,
  `foodAmount` double NOT NULL,
  `foodDetail` varchar(200) DEFAULT NULL,
  `foodUnit` varchar(200) NOT NULL,
  `discount` double DEFAULT NULL,
  PRIMARY KEY (`foodNo`),
  KEY `foodtype` (`foodTypeNo`),
  CONSTRAINT `foodtype` FOREIGN KEY (`foodTypeNo`) REFERENCES `foodtypeinfo` (`foodTypeNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of foodinfo
-- ----------------------------
INSERT INTO `foodinfo` VALUES ('11', '6', 'parrot', '1', '9999', 'qd', 'bao', null);
INSERT INTO `foodinfo` VALUES ('12', '5', 'meat', '12', '9999', '12', '12', '0');

-- ----------------------------
-- Table structure for foodorder
-- ----------------------------
DROP TABLE IF EXISTS `foodorder`;
CREATE TABLE `foodorder` (
  `orderNo` int(11) NOT NULL AUTO_INCREMENT,
  `userNo` varchar(200) NOT NULL,
  `receiverName` varchar(200) NOT NULL,
  `createTime` date NOT NULL,
  `deliverTime` date DEFAULT NULL,
  `address` varchar(200) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `orderStatus` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`orderNo`),
  KEY `FK_Reference_5` (`userNo`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`userNo`) REFERENCES `userinfo` (`userNo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of foodorder
-- ----------------------------
INSERT INTO `foodorder` VALUES ('2', '11', 'hyz', '2019-08-29', null, 'zucc13', '150', 'ordered');
INSERT INTO `foodorder` VALUES ('8', '11', '333', '2019-08-31', null, '333', '333', 'ordered');
INSERT INTO `foodorder` VALUES ('12', '11', '3321313', '2019-09-01', null, '13213', '13213', 'ordered');
INSERT INTO `foodorder` VALUES ('13', '11', '222', '2019-09-02', null, '222', '222', 'ordered');

-- ----------------------------
-- Table structure for foodtypeinfo
-- ----------------------------
DROP TABLE IF EXISTS `foodtypeinfo`;
CREATE TABLE `foodtypeinfo` (
  `foodTypeNo` int(7) NOT NULL AUTO_INCREMENT,
  `foodTypeName` varchar(200) NOT NULL,
  `foodTypeDetail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`foodTypeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of foodtypeinfo
-- ----------------------------
INSERT INTO `foodtypeinfo` VALUES ('5', '12', '12');
INSERT INTO `foodtypeinfo` VALUES ('6', '13', '13');

-- ----------------------------
-- Table structure for orderdetail
-- ----------------------------
DROP TABLE IF EXISTS `orderdetail`;
CREATE TABLE `orderdetail` (
  `orderNo` int(11) NOT NULL AUTO_INCREMENT,
  `foodNo` varchar(200) NOT NULL,
  `amount` double NOT NULL,
  `price` double NOT NULL,
  `discount` double NOT NULL,
  PRIMARY KEY (`orderNo`,`foodNo`),
  KEY `FK_Reference_7` (`foodNo`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`foodNo`) REFERENCES `foodinfo` (`foodNo`),
  CONSTRAINT `orderdetail_ibfk_1` FOREIGN KEY (`orderNo`) REFERENCES `foodorder` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderdetail
-- ----------------------------
INSERT INTO `orderdetail` VALUES ('2', '12', '2', '0', '0');
INSERT INTO `orderdetail` VALUES ('8', '12', '1', '12', '0');
INSERT INTO `orderdetail` VALUES ('12', '12', '1', '12', '0');
INSERT INTO `orderdetail` VALUES ('13', '12', '1', '12', '0');

-- ----------------------------
-- Table structure for recipecomment
-- ----------------------------
DROP TABLE IF EXISTS `recipecomment`;
CREATE TABLE `recipecomment` (
  `recipeNo` varchar(200) NOT NULL,
  `userNo` varchar(200) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `look` tinyint(1) DEFAULT NULL,
  `collect` tinyint(1) DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`recipeNo`,`userNo`),
  KEY `FK_Reference_4` (`userNo`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`recipeNo`) REFERENCES `recipeinfo` (`recipeNo`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`userNo`) REFERENCES `userinfo` (`userNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recipecomment
-- ----------------------------
INSERT INTO `recipecomment` VALUES ('1', '11', '1', null, null, null);
INSERT INTO `recipecomment` VALUES ('233', '11', '33', null, null, null);

-- ----------------------------
-- Table structure for recipeinfo
-- ----------------------------
DROP TABLE IF EXISTS `recipeinfo`;
CREATE TABLE `recipeinfo` (
  `recipeNo` varchar(200) NOT NULL,
  `recipeName` varchar(200) NOT NULL,
  `userNo` varchar(200) NOT NULL,
  `recipeDetail` varchar(200) NOT NULL,
  `scoreTotal` double NOT NULL,
  `collectCount` int(11) NOT NULL,
  `lookCount` int(11) NOT NULL,
  PRIMARY KEY (`recipeNo`),
  KEY `FK_Reference_11` (`userNo`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`userNo`) REFERENCES `userinfo` (`userNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recipeinfo
-- ----------------------------
INSERT INTO `recipeinfo` VALUES ('1', '1', '11', '1', '0', '0', '0');
INSERT INTO `recipeinfo` VALUES ('2', '3', '11', '3', '0', '0', '0');
INSERT INTO `recipeinfo` VALUES ('233', '233', '11', '233', '0', '0', '0');

-- ----------------------------
-- Table structure for recipestep
-- ----------------------------
DROP TABLE IF EXISTS `recipestep`;
CREATE TABLE `recipestep` (
  `recipeNo` varchar(200) NOT NULL,
  `stepNo` int(11) NOT NULL,
  `stepDetail` varchar(200) NOT NULL,
  PRIMARY KEY (`recipeNo`,`stepNo`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`recipeNo`) REFERENCES `recipeinfo` (`recipeNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recipestep
-- ----------------------------
INSERT INTO `recipestep` VALUES ('1', '1', '12');
INSERT INTO `recipestep` VALUES ('1', '2', '2');
INSERT INTO `recipestep` VALUES ('1', '3', '3');
INSERT INTO `recipestep` VALUES ('1', '4', '4');
INSERT INTO `recipestep` VALUES ('233', '1', '123');

-- ----------------------------
-- Table structure for recipeuse
-- ----------------------------
DROP TABLE IF EXISTS `recipeuse`;
CREATE TABLE `recipeuse` (
  `recipeNo` varchar(200) NOT NULL,
  `foodNo` varchar(200) NOT NULL,
  `amount` double NOT NULL,
  `unit` varchar(200) NOT NULL,
  PRIMARY KEY (`recipeNo`,`foodNo`),
  KEY `recipeuse_ibfk_1` (`foodNo`),
  CONSTRAINT `recipeuse_ibfk_1` FOREIGN KEY (`foodNo`) REFERENCES `foodinfo` (`foodNo`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`recipeNo`) REFERENCES `recipeinfo` (`recipeNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recipeuse
-- ----------------------------
INSERT INTO `recipeuse` VALUES ('1', '12', '1', 'bao');
INSERT INTO `recipeuse` VALUES ('233', '12', '233', 'bao');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `userNo` varchar(200) NOT NULL,
  `userType` varchar(200) NOT NULL,
  `userName` varchar(200) DEFAULT NULL,
  `userSex` varchar(200) DEFAULT NULL,
  `userPassword` varchar(200) NOT NULL,
  `userPhone` varchar(11) DEFAULT NULL,
  `userMail` varchar(200) DEFAULT NULL,
  `userCity` varchar(200) DEFAULT NULL,
  `registerTime` datetime NOT NULL,
  PRIMARY KEY (`userNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('11', 'user', null, null, '11', null, null, null, '2019-08-24 14:19:49');
INSERT INTO `userinfo` VALUES ('123', 'user', null, null, '1', null, null, null, '2019-08-24 13:46:02');
INSERT INTO `userinfo` VALUES ('22', 'admin', '22', null, '22', null, null, null, '2019-08-25 09:05:10');
INSERT INTO `userinfo` VALUES ('33', 'admin', '33', null, '33', null, null, null, '2019-08-26 14:28:23');
INSERT INTO `userinfo` VALUES ('admin', 'admin', 'admin', null, 'admin', null, null, null, '2019-08-24 14:06:05');
INSERT INTO `userinfo` VALUES ('admin1', 'admin', 'admin', null, 'admin1', null, null, null, '2019-08-25 08:54:52');
