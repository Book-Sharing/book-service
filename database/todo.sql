-- 正在设计的表

CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `describe` varchar (128) NOT NULL DEFAULT '' COMMENT '类别描述',
  `name` varchar (64) NOT NULL DEFAULT '' COMMENT '类别名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '书本类别表';



CREATE TABLE `order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `from_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '发起用户id',
  `to_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '接收用户id',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0换书 1借书 2赠书 卖书',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0未完成 1已完成',
  `message` varchar (64) NOT NULL DEFAULT '' COMMENT '留言',
  `price` numeric(10,2) NOT NULL DEFAULT 0 COMMENT '订单总额',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '订单信息表';

CREATE TABLE `order_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更改时间',
  `order_id` int(11) NOT NULL DEFAULT 0 COMMENT '订单id',
  `book_id` int(11) NOT NULL DEFAULT 0 COMMENT '书本id',
  `message` varchar (64) NOT NULL DEFAULT '' COMMENT '留言',
  `item_price` numeric(10,2) NOT NULL DEFAULT 0 COMMENT '价格',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0否 1是',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT '订单详细信息表';



