
-- 增加角色
insert into t_role (name, description)
values
('逗逼青年','90后'),
('二逼青年','80后');


-- 增加用户 ,这里假设 用户的角色id分别只有 21,22 这两个值
insert into t_user (username, gender, nickname, birthday, gid)
values 
('zhangfei','male','zhangfei','1987-02-01 00:00:00','21'),
('guanyu','male','guanyu','1988-02-04 00:00:00','21'),
('liubei','male','liubei','1989-02-03 00:00:00','21'),
('caocao','male','caocao','1990-03-02 00:00:00','21'),
('zhugeliang','male','zhugeliang','1991-02-15 00:00:00','21'),
('huangzhong','male','huangzhong','1992-05-26 00:00:00','21'),
('machao','male','machao','1993-02-06 00:00:00','21'),
('weiyan','male','weiyan','1987-06-09 00:00:00','22'),
('maliang','male','maliang','1988-02-23 00:00:00','22'),
('zhaoyun','male','zhaoyun','1989-07-21 00:00:00','21'),
('pangtong','male','pangtong','1990-08-13 00:00:00','22'),
('zhangliao','male','zhangliao','1991-09-30 00:00:00','21'),
('yanliang','male','yanliang','1992-02-11 00:00:00','22'),
('wenchou','male','wenchou','1987-02-12 00:00:00','22'),
('lvbu','male','lvbu','1988-02-01 00:00:00','21'),
('chenggong','male','chenggong','1989-02-14 00:00:00','21'),
('caoyun','male','caoyun','1990-02-21 00:00:00','22'),
('simayi','male','simayi','1991-02-02 00:00:00','22'),
('simazhao','male','simazhao','1992-04-01 00:00:00','22'),
('huanggai','male','huanggai','1993-03-01 00:00:00','21');