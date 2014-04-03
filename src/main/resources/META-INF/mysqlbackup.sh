#!/bin/sh

DAY=`/bin/date +%Y%m%d_%H_%M_%S`

/opt/mysql/bin/mysqldump -umyschool -pmyschool myschool | gzip > /home/serveradmin/db_saves/myschool_$DAY.sql.gz

/opt/mysql/bin/mysql -umyschool -pmyschool myschool < /home/serveradmin/db_saves/gsr-all-major-10-03-2013-V0.sql
