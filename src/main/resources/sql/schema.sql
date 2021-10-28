drop table if exists zh_user;

CREATE TABLE zh_user
(
    id                       int         not null primary key auto_increment,
    email                    varchar(50) not null unique,
    password                 varchar(40) not null comment '密码',
    email_status             boolean                                default false comment '邮箱验证状态',
    avatar                   varchar(255)                           default null comment '用户头像',
    description              tinytext COLLATE utf8mb4_unicode_ci    default null comment '用户的描述',
    recently_landed_datetime datetime                               default null comment '最近的登录时间',
    display_name             varchar(30) COLLATE utf8mb4_unicode_ci default null comment '展示的昵称',
    status                   tinyint(1)  not null                   default 0 comment '账户状态',
    update_datetime          datetime                               default null comment '更新日期',
    create_datetime          datetime                               default NOW() comment '创建日期',
    key idx_user_email (email)
) comment '用户表';