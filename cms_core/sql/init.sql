create database cms_core character set utf8 collate utf8_general_ci;

use cms_core;

create table t_user (id int primary key auto_increment,   
					username varchar(255) not null,  
					nickname varchar(255),  
					password varchar(255), 					 
					phone varchar(255),					 
					email varchar(255),					 
					status smallint,
					create_date datetime);