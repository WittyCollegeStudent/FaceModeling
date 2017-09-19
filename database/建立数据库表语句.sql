-- 创建数据库
create database fmdb;
-- 创建用户
create user 'facemodeling'@'localhost' identified by 'mysql';
-- 赋权限
grant select,update,insert,create on fm.* to facemodeling identified by 'mysql';