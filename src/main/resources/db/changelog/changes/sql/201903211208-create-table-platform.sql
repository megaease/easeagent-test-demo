CREATE TABLE `t_platform_user`
(
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `username`    VARCHAR(64)  NOT NULL COMMENT 'username',
  `password`    VARCHAR(128) NOT NULL COMMENT 'password',
  `create_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'record create date',
  `update_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'record update date',
  PRIMARY KEY (`id`)
);

CREATE TABLE `t_role`
(
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `code`        VARCHAR(255) NOT NULL COMMENT 'role identity ',
  `name`        VARCHAR(255) NOT NULL COMMENT 'role name',
  `description` VARCHAR(255)  NOT NULL DEFAULT '' COMMENT 'role description',
  `built_in`    TINYINT(1)   NOT NULL COMMENT 'whether the role is a built-in role',
  `module`      VARCHAR(45)  NOT NULL COMMENT 'the function module that the role is belong to',
  `create_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'record create date',
  `update_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'record update date',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC)
);

CREATE TABLE `t_permission`
(
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `code`        VARCHAR(255) NOT NULL COMMENT 'permission identity',
  `description` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'permission description',
  `module`      VARCHAR(45)  NOT NULL COMMENT 'the function module that the role is belong to',
  `create_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'record create date',
  `update_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'record update date',
  PRIMARY KEY (`id`)
);

CREATE TABLE `t_user_role`
(
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT(20)  NOT NULL COMMENT 't_user.id',
  `role_id`     BIGINT(20)  NOT NULL COMMENT 't_role.id',
  `user_realm`  VARCHAR(32)  NOT NULL COMMENT 'the realm that user is belong to.\nthe type of user. such as platform or customer.',
  `create_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'record create date',
  `update_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'record update date',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `U_USER` (`user_id` ASC, `user_realm` ASC, `role_id` ASC)
);

CREATE TABLE `t_role_permission`
(
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `role_id`       BIGINT(20) NOT NULL COMMENT 't_role.id',
  `permission_id` BIGINT(20) NOT NULL COMMENT 't_permission.id',
  `create_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'record create date',
  `update_date` DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'record update date',
  PRIMARY KEY (`id`)
);
