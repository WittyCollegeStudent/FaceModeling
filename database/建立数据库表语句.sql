-- �������ݿ�
create database fmdb;
-- �����û�
create user 'facemodeling'@'localhost' identified by 'mysql';
-- ��Ȩ��
grant select,update,insert,create on fm.* to facemodeling identified by 'mysql';