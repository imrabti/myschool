#!/bin/sh

DAY=`/bin/date +%Y%m%d_%H_%M_%S`

/opt/bitnami/mysql/bin/mysqldump -u root -pbitnami myschool | gzip > /home/bitnami/db_saves/myschool_$DAY.sql.gz

/opt/bitnami/mysql/bin/mysql -u root -pbitnami     myschool    < /home/bitnami/db_saves/gsr-all-major-10-03-2013-V0.sql


