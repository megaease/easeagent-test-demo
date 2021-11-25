insert into `t_platform_user` (`id`, `username`, `password`)
values (1, 'admin', '{md5}{FjruFo3WvdbAw3GrodOala0Izoov8Lug5yMavVtMbok=}83abdb036afecd2907c902e01ded53e0');
insert into `t_role` (`id`, `code`, `name`, `description`, `built_in`, `module`)
values (1, 'platform/administrator', 'Platform Administrator', 'The highest privilege owner in platform', '1', 'platform');
insert into `t_user_role` (`id`, `user_id`, `role_id`, `user_realm`)
values (1, 1, 1, 'PLATFORM');