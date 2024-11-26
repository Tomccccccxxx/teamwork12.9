/*
 Navicat Premium Data Transfer

 Source Server         : 学生成绩管理系统
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : logistics_tracking_system

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 19/11/2021 14:01:15
*/

CREATE DATABASE logistics_tracking_system;

use logistics_tracking_system;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for merchantorder
-- ----------------------------
DROP TABLE IF EXISTS `merchantorder`;
CREATE TABLE `merchantorder`  (
  `orderId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `userPhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户手机号',
  `itemDesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述信息',
  `targetSite` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目标站点',
  `date` datetime NOT NULL COMMENT '下单时间（不显示毫秒数）',
  `orderState` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of merchantorder
-- ----------------------------
INSERT INTO `merchantorder` VALUES ('order_01_01', '12345678900', '洋娃娃 10', '10', '2021-11-15 02:00:00', '已签收');
INSERT INTO `merchantorder` VALUES ('order_02_02', '12345678900', '洋娃娃 19', '10', '2021-11-17 01:18:44', '已发货');
INSERT INTO `merchantorder` VALUES ('order_03_03', '12345678900', '洋娃娃 70', '10', '2021-11-15 01:00:00', '已发货');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编号',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
  `userPhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户手机号',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户地址',
  `orderId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `date` datetime NOT NULL COMMENT '下单时间（不显示毫秒数）',
  `ItemDesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物品描述信息',
  `thisSite` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当前站点',
  `targetSite` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目标站点',
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('10240000', '张三码农', '12345678900', '东城街道广东工程职业技术学院', 'order_01_01', '2021-11-15 01:00:00', '洋娃娃 10', '10', '10');
INSERT INTO `order` VALUES ('10240000', '张三码农', '12345678900', '东城街道广东工程职业技术学院', 'order_02_02', '2021-11-17 01:18:44', '洋娃娃 19', '5', '10');
INSERT INTO `order` VALUES ('10240000', '张三码农', '12345678900', '东城街道广东工程职业技术学院', 'order_03_03', '2021-11-15 01:00:00', '洋娃娃 70', '1', '10');

SET FOREIGN_KEY_CHECKS = 1;
