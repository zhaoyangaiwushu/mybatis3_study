/*
 Navicat MySQL Data Transfer

 Source Server         : 本机docker-3308
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3308
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 14/09/2020 20:32:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_employee
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_employee
-- ----------------------------
INSERT INTO `tbl_employee` VALUES (1, '谢召阳', '462321010@qq.com', '1', NULL);
INSERT INTO `tbl_employee` VALUES (7, '梁晓鑫11', '462321010@qq.com', '1', '2020-09-14 10:24:17');

SET FOREIGN_KEY_CHECKS = 1;
