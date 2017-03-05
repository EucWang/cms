create database cms_test character set utf8 collate utf8_general_ci;

use cms_test;

create table t_user (id int primary key auto_increment,   
					username varchar(255) not null );