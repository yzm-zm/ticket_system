-- 车站售票系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS ticket_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ticket_system;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` int(1) DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 车站表
CREATE TABLE IF NOT EXISTS `station` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车站ID',
  `station_name` varchar(50) NOT NULL COMMENT '车站名称',
  `station_code` varchar(20) DEFAULT NULL COMMENT '车站代码',
  `city` varchar(50) DEFAULT NULL COMMENT '所属城市',
  `status` int(1) DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_station_name` (`station_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车站表';

-- 座位类型表
CREATE TABLE IF NOT EXISTS `seat_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '座位类型ID',
  `type_name` varchar(20) NOT NULL COMMENT '座位类型名称（商务座、一等座、二等座等）',
  `type_code` varchar(20) NOT NULL COMMENT '座位类型代码',
  `status` int(1) DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_code` (`type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='座位类型表';

-- 车次表
CREATE TABLE IF NOT EXISTS `train` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车次ID',
  `train_no` varchar(20) NOT NULL COMMENT '车次号',
  `train_name` varchar(50) DEFAULT NULL COMMENT '车次名称',
  `departure_station_id` bigint(20) NOT NULL COMMENT '出发站ID',
  `arrival_station_id` bigint(20) NOT NULL COMMENT '到达站ID',
  `departure_time` datetime NOT NULL COMMENT '出发时间',
  `arrival_time` datetime NOT NULL COMMENT '到达时间',
  `duration` int(11) DEFAULT NULL COMMENT '运行时长（分钟）',
  `status` int(1) DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_train_no` (`train_no`),
  KEY `idx_departure_station` (`departure_station_id`),
  KEY `idx_arrival_station` (`arrival_station_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车次表';

-- 车次座位表
CREATE TABLE IF NOT EXISTS `train_seat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车次座位ID',
  `train_id` bigint(20) NOT NULL COMMENT '车次ID',
  `seat_type_id` bigint(20) NOT NULL COMMENT '座位类型ID',
  `total_seats` int(11) NOT NULL DEFAULT 0 COMMENT '总座位数',
  `available_seats` int(11) NOT NULL DEFAULT 0 COMMENT '可用座位数',
  `price` decimal(10,2) NOT NULL COMMENT '票价',
  `status` int(1) DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_train_id` (`train_id`),
  KEY `idx_seat_type_id` (`seat_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车次座位表';

-- 订单表
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `train_id` bigint(20) NOT NULL COMMENT '车次ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `order_status` int(1) DEFAULT 0 COMMENT '订单状态：0-待支付，1-已支付，2-已退票，3-已改签',
  `pay_type` varchar(20) DEFAULT NULL COMMENT '支付方式',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_train_id` (`train_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单详情表
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单详情ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `passenger_name` varchar(50) NOT NULL COMMENT '乘车人姓名',
  `id_card` varchar(18) NOT NULL COMMENT '身份证号',
  `seat_type_id` bigint(20) NOT NULL COMMENT '座位类型ID',
  `seat_no` varchar(20) DEFAULT NULL COMMENT '座位号',
  `price` decimal(10,2) NOT NULL COMMENT '票价',
  `status` int(1) DEFAULT 1 COMMENT '状态：1-正常，0-已退票，2-已改签',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_seat_type_id` (`seat_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- 插入测试数据

-- 插入用户数据
INSERT INTO `user` (`username`, `password`, `real_name`, `id_card`, `phone`, `email`, `status`) VALUES
('admin', '123456', '管理员', '110101199001011234', '13800138000', 'admin@example.com', 1),
('user001', '123456', '张三', '110101199001011111', '13800138001', 'zhangsan@example.com', 1),
('user002', '123456', '李四', '110101199001012222', '13800138002', 'lisi@example.com', 1);

-- 插入车站数据
INSERT INTO `station` (`station_name`, `station_code`, `city`, `status`) VALUES
('北京南站', 'BJN', '北京', 1),
('上海虹桥站', 'SHHQ', '上海', 1),
('广州南站', 'GZN', '广州', 1),
('深圳北站', 'SZB', '深圳', 1),
('杭州东站', 'HZD', '杭州', 1),
('南京南站', 'NJN', '南京', 1);

-- 插入座位类型数据
INSERT INTO `seat_type` (`type_name`, `type_code`, `status`) VALUES
('商务座', 'BUSINESS', 1),
('一等座', 'FIRST', 1),
('二等座', 'SECOND', 1),
('硬卧', 'HARD_SLEEPER', 1),
('软卧', 'SOFT_SLEEPER', 1);

-- 插入车次数据
INSERT INTO `train` (`train_no`, `train_name`, `departure_station_id`, `arrival_station_id`, `departure_time`, `arrival_time`, `duration`, `status`) VALUES
('G1', 'G1次', 1, 2, '2024-12-01 08:00:00', '2024-12-01 12:30:00', 270, 1),
('G2', 'G2次', 2, 1, '2024-12-01 14:00:00', '2024-12-01 18:30:00', 270, 1),
('G101', 'G101次', 1, 3, '2024-12-01 09:00:00', '2024-12-01 18:00:00', 540, 1),
('G102', 'G102次', 3, 1, '2024-12-01 10:00:00', '2024-12-01 19:00:00', 540, 1);

-- 插入车次座位数据
INSERT INTO `train_seat` (`train_id`, `seat_type_id`, `total_seats`, `available_seats`, `price`, `status`) VALUES
(1, 1, 20, 20, 1748.00, 1),
(1, 2, 50, 50, 924.00, 1),
(1, 3, 200, 200, 553.00, 1),
(2, 1, 20, 20, 1748.00, 1),
(2, 2, 50, 50, 924.00, 1),
(2, 3, 200, 200, 553.00, 1),
(3, 1, 20, 20, 2727.00, 1),
(3, 2, 50, 50, 1380.00, 1),
(3, 3, 200, 200, 862.00, 1),
(4, 1, 20, 20, 2727.00, 1),
(4, 2, 50, 50, 1380.00, 1),
(4, 3, 200, 200, 862.00, 1);

