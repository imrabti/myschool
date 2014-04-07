#!/bin/sh

DAY=`/bin/date +%Y%m%d_%H_%M_%S`

/usr/bin/mysqldump -umyschool -pmyschool myschool | gzip > /home/serveradmin/db_dumps/myschool_$DAY.sql.gz

/usr/bin/mysql -umyschool -pmyschool myschool < /home/serveradmin/db_dumps/gsr-all-major-10-03-2013-V0.sql
