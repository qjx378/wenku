/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : wenku

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2016-11-17 08:36:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for doc_base_info
-- ----------------------------
DROP TABLE IF EXISTS `doc_base_info`;
CREATE TABLE `doc_base_info` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `summary` varchar(255) DEFAULT NULL COMMENT '摘要',
  `type` int(11) NOT NULL COMMENT '分类 1-公共文档，2-私有文档',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `file_id` varchar(255) DEFAULT NULL COMMENT '文件id',
  `view_count` int(11) NOT NULL COMMENT '浏览数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for doc_file_info
-- ----------------------------
DROP TABLE IF EXISTS `doc_file_info`;
CREATE TABLE `doc_file_info` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `path` varchar(255) DEFAULT NULL COMMENT '原文件路径',
  `pdf_path` varchar(255) DEFAULT NULL COMMENT 'pdf路径',
  `swf_path` varchar(255) DEFAULT NULL COMMENT 'swf路径',
  `thum_path` varchar(255) DEFAULT NULL COMMENT '首页缩略图路径',
  `hash` varchar(255) DEFAULT NULL COMMENT '原文件hash',
  `ext` varchar(255) DEFAULT NULL COMMENT '原文件后缀',
  `size` bigint(20) DEFAULT NULL COMMENT '原文件大小(字节）',
  `pages` int(11) DEFAULT '0' COMMENT '总页数',
  `con_state` int(11) NOT NULL DEFAULT '0' COMMENT '文档转换状态 0-待转换 1-转换中 2-转换完成 3-转换失败，默认0',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(62) NOT NULL COMMENT '主键',
  `email` varchar(255) DEFAULT NULL COMMENT '电子邮件',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `sex` int(1) DEFAULT '1' COMMENT '性别 0-女 1-男，默认1',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `pwd_salt` varchar(255) DEFAULT NULL COMMENT '密码盐',
  `city` varchar(255) DEFAULT NULL COMMENT '所在城市',
  `sign` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像url',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_visitor
-- ----------------------------
DROP TABLE IF EXISTS `user_visitor`;
CREATE TABLE `user_visitor` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键id',
  `user_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '被访问者用户id',
  `visitor_user_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '访问者用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户访客记录表';
