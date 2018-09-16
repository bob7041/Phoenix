# Phoenix database definition         6/23/2018
# Copyright 2018 B. Brander/AST

CREATE DATABASE phoenixdb character set utf8;
CREATE USER 'phoenix'@'%' IDENTIFIED BY 'UR2quick!';
GRANT ALL ON phoenixdb.* TO 'phoenix'@'%';