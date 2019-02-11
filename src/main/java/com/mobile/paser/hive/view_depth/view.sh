#!/bin/bash
#./view.sh -d 2018-12-08
#定义变量
month=
day=
dt=
#until 当元素个数为0时执行then
until [ $0 = 0 ]
do
#如果第一个元素等于d，那么第一个元素的值dt 否则就移除这个元素
if [ '-ds' == $1's' ]
then
#移除
shift
dt=$1
break
fi
shift
done

echo $dt

#获得dt的值，对dt的值进行解析
if [ ${#dt} == 10 ]
then
month=`date -d "${dt}" "+%m" `
day=`date -d "${dt}" "+%d" `
else
dt=`date -d "1 day ago" "+%Y-%m-%d" `
month=`date -d "${dt}" "+%m" `
dat=`date -d "${dt}" "+%d" `
fi

#输出运行的日志是哪天的
echo $dt
echo $month
echo $day

##runsql
#load partition data
hive -e " load data inpath 'hdfs://bigdata:9000/ods/${month}/${day}' into table bigdata.log partition (month="${month}",day="$day")"

#run hive sql
hive --database bigdata -e "
 create  table result3 as
  select
  plat_udf(temp.pl) plId,
  date_udf(temp.dt) dtId,
  kpi_udf("view_depth") kpiId,
  count(distinct(temp.uuid)) nums,
  temp.pv,
  temp.dt
  from
  (select l.pl pl,
  from_unixtime(cast(l.s_time as bigint),'yyyy-MM-dd') dt,
  l.u_ud uuid,
  case
  when count(l.p_url) = 1 then  'pv1'
  when count(l.p_url) = 2 then  'pv2'
  when count(l.p_url) = 3 then  'pv3'
  when count(l.p_url) = 4 then  'pv4'
  when count(l.p_url) < 10 then 'pv5_10'
  when count(l.p_url) < 30 then 'pv10_30'
  when count(l.p_url) < 60 then 'pv30_60'
  else 'pv60plus'
  end pv
  from log l
  where l.month='12'
  and l.day='04'
  and l.en='e_pv'
  and l.s_time is not null
  group by  from_unixtime(cast(l.s_time as bigint),'yyyy-MM-dd'),l.pl,l.u_ud
  ) as temp
  group by plat_udf(temp.pl),date_udf(temp.dt), kpi_udf("view_depth"), temp.pv,temp.dt
;


from(
select temp.dtid,temp.plid,temp.kpiid,sum(temp.pv1) pv1,sum(temp.pv2) pv2,sum(temp.pv3) pv3,sum(temp.pv4) pv4,
sum(temp.pv5_10) pv5_10,sum(temp.pv10_30) pv10_30,sum(temp.pv30_60) pv30_60,sum(temp.pv60plus) pv60plus ,temp.dt
from(
select dtid,plid,kpiid,nums as pv1 ,0 as pv2 ,0 as pv3 ,0 as pv4, 0 as pv5_10, 0 as pv10_30 ,0 as pv30_60 ,0 as pv60plus ,dt  from result3 where pv='pv1'
union all
select dtid,plid,kpiid,0 as pv1 ,nums as pv2 ,0 as pv3 ,0 as pv4, 0 as pv5_10, 0 as pv10_30 ,0 as pv30_60 ,0 as pv60plus ,dt  from result3 where pv='pv2'
union all
select dtid,plid,kpiid,0 as pv1 ,0 as pv2 ,nums as pv3 ,0 as pv4, 0 as pv5_10, 0 as pv10_30 ,0 as pv30_60 ,0 as pv60plus ,dt  from result3 where pv='pv3'
union all
select dtid,plid,kpiid,0 as pv1 ,0 as pv2 ,0 as pv3 ,nums as pv4, 0 as pv5_10, 0 as pv10_30 ,0 as pv30_60 ,0 as pv60plus ,dt  from result3 where pv='pv4'
union all
select dtid,plid,kpiid,0 as pv1 ,0 as pv2 ,0 as pv3 ,0 as pv4, nums as pv5_10, 0 as pv10_30 ,0 as pv30_60 ,0 as pv60plus ,dt  from result3 where pv='pv5_10'
union all
select dtid,plid,kpiid,0 as pv1 ,0 as pv2 ,0 as pv3 ,0 as pv4, 0 as pv5_10, nums as pv10_30 ,0 as pv30_60 ,0 as pv60plus ,dt  from result3 where pv='pv10_30'
union all
select dtid,plid,kpiid,0 as pv1 ,0 as pv2 ,0 as pv3 ,0 as pv4, 0 as pv5_10, 0 as pv10_30 ,nums as pv30_60 ,0 as pv60plus ,dt  from result3 where pv='pv30_60'
union all
select dtid,plid,kpiid,0 as pv1 ,0 as pv2 ,0 as pv3 ,0 as pv4, 0 as pv5_10, 0 as pv10_30 ,0 as pv30_60 ,nums as pv60plus ,dt  from result3 where pv='pv60plus')
as temp
group by temp.dtid,temp.plid,temp.kpiid,temp.dt) as view_depth
insert overwrite table `stats_view_depth`
select view_depth.dtid,view_depth.plid,view_depth.kpiid,view_depth.pv1,view_depth.pv2,view_depth.pv3,
view_depth.pv4,view_depth.pv5_10,view_depth.pv10_30,view_depth.pv30_60,view_depth.pv60plus,view_depth.dt
;
"

#run sqoop

sqoop export --connect jdbc:mysql://bigdata:3306/result \
--username root \
--password root \
--table stats_view_depth \
--export-dir hdfs://bigdata:9000/user/hive/warehouse/bigdata.db/stats_view_depth/* \
--input-fields-terminated-by "\001" \
--update-mode allowinsert \
--update-key  date_dimension_id,platform_dimension_id,kpi_dimension_id \