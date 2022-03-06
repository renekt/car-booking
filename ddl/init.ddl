DROP TABLE IF EXISTS `t_car`;
CREATE TABLE `t_car`  (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'unique key',
                          `model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'car model',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `t_car` VALUES (1, 'Toyota Camry');
INSERT INTO `t_car` VALUES (2, 'Toyota Camry');
INSERT INTO `t_car` VALUES (3, 'BMW 650');
INSERT INTO `t_car` VALUES (4, 'BMW 650');

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'unique key ',
                            `car_id` bigint NOT NULL COMMENT 'car id of booking order',
                            `status` int NOT NULL COMMENT '0 cancelled; 1 booked',
                            `start_time` datetime(0) NOT NULL COMMENT 'booking start time',
                            `end_time` datetime(0) NOT NULL COMMENT 'booking end time',
                            `create_time` datetime(0) NOT NULL COMMENT 'order create time ',
                            `update_time` datetime(0) NOT NULL COMMENT 'order update time ',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
