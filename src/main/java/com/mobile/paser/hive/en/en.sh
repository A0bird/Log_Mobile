#!/bin/bash
# ./en.sh -d 2018-12-08
month=
day=
dt=
until [ $0 = 0 ]
do
if [ '-ds' == $1's' ]
then
shift
dt=$1
break
fi
shift
done

echo $dt

if [ ${#dt} == 10 ]
then
month=`date -d "${dt}" "+%m" `
day=`date -d "${dt}" "+%d" `
else
dt=`date -d "1 day ago" "+%Y-%m-%d"`
month=`date -d "${dt}" "+%m" `
day=`date -d "${dt}" "+%d" `
fi

echo $dt
echo $month
echo $day

##run sql
#load partition data
hive -e " load data inpath 'hdfs://bigdata:9000/ods/${month}/${day}'  into table bigdata.log partition ( month="${month}",day="${day}" )";


#run hive sql
hive --database mobile -e "
set hive.exec.mode.local.auto=true;
set hive.groupby.skewindata=true;
set hive.map.aggr=true;
set hive.exec.parallel=true;
set hive.exec.mode.local.auto.input.files.max=5;
from(
select
from_unixtime(cast(l.s_time as bigint),'yyyy-MM-dd') dt,
l.pl pl,
l.ca ca,
l.ac ac,
count(*) as ct
from log l
where l.month = "${month}"
and day = "${day}"
and l.en = 'e_e'
and l.s_time is not null
group by from_unixtime(cast(l.s_time as bigint),'yyyy-MM-dd'),l.pl,l.ca,l.ac
) as tmp
insert overwrite table stats_event
select plat_udf(pl),date_udf(dt),event_udf(ca,ac),ct,dt
;
"

#run sqoop
bin/sqoop  export --connect jdbc:mysql://bigdata:3306/result \
--username root --password root \
--table stats_event \
--export-dir hdfs://bigdata:9000/user/hive/warehouse/bigdata.db/stats_event/* \
--input-fields-terminated-by "\001" \
--update-mode allowinsert \
--update-key platform_dimension_id,date_dimension_id,event_dimension_id \
;

echo "event job is finished"

//定时任务执行
crontab -e
*(分钟)   *(小时)   *(天)   *(月)   *(周)       /home/bigdata/shell
