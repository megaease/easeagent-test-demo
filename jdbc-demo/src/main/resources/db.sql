
CREATE DATABASE `db_demo`  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;

CREATE TABLE `db_demo`.`t_user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `create_time` DATETIME(3) NOT NULL,
  PRIMARY KEY (`user_id`));

CREATE USER IF NOT EXISTS 'easeagent'@'localhost' IDENTIFIED BY 'asdasd';
GRANT ALL privileges ON `db_demo`.* TO 'easeagent'@`localhost`;
FLUSH PRIVILEGES;
