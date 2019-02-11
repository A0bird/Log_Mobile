1、创建维度类，并创建对应的udf方法

2、创建表
create table if not exists stats_order_tmp(
      `date_dimension_id` int,
      `platform_dimension_id` int,
      `currency_type_dimension_id` int,
      `payment_type_dimension_id` int,
      `ct` int,
      `created` string
)
;

create table if not exists stats_order_tmp1(
      `date_dimension_id` int,
      `platform_dimension_id` int,
      `currency_type_dimension_id` int,
      `payment_type_dimension_id` int,
      `orders` int,
      `success_orders` int,
      `refund_orders` int,
      `created` string
)
;

create table if not exists stats_order_tmp2(
      `date_dimension_id` int,
      `platform_dimension_id` int,
      `currency_type_dimension_id` int,
      `payment_type_dimension_id` int,
      `orders_amount` double,
      `success_orders_amount` double,
      `refund_orders_amount`  double,
      `created` string
)
;


3、写指标

--总的订单数量：
set hive.exec.mode.local.auto=true;
set hive.groupby.skewindata=true;
set hive.map.aggr=true;
set hive.exec.parallel=true;
set hive.exec.mode.local.auto.input.files.max=5;
insert overwrite table stats_order_tmp
select date_udf(dt),platform_udf(pl),currencyll(cut),paymentypeaa(pt),sum(ct),dt
from(
select
from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd") as dt,
l.pl as pl,
l.cut as cut,
l.pt as pt,
count(distinct l.oid) as ct
from log l
where l.month = 12
and day = 13
and l.oid is not null
and l.oid <> 'null'
and l.en = 'e_crt'
group by from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd"),pl,cut,pt
) as tmp
group by dt,pl,cut,pt
;

sqoop export --connect jdbc:mysql://bigdata:3306/dbfirst \
--username root --password root \
--table stats_order --export-dir //bigdata:8020/user/hive/warehouse/mobile.db/stats_order_tmp/* \
--input-fields-terminated-by "\\01" --update-mode allowinsert \
--update-key date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id \
--columns 'date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id,orders,created' \
;
--成功支付的订单数量：

set hive.exec.mode.local.auto=true;
set hive.groupby.skewindata=true;
set hive.map.aggr=true;
set hive.exec.parallel=true;
set hive.exec.mode.local.auto.input.files.max=5;

insert overwrite table stats_order_tmp
select date_udf(dt),platform_udf(pl),currencyll(cut),paymentypeaa(pt),sum(ct),dt
from(
select
from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd") as dt,
l.pl as pl,
l.cut as cut,
l.pt as pt,
count(distinct l.oid) as ct
from log l
where l.month = 12
and l.day = 13
and l.oid is not null
and l.oid <> 'null'
and l.en = 'e_cs'
group by from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd"),pl,cut,pt
) as tmp
group by dt,pl,cut,pt
;

sqoop export --connect jdbc:mysql://bigdata:3306/dbfirst \
--username root --password root \
--table stats_order --export-dir //bigdata:8020/user/hive/warehouse/mobile.db/stats_order_tmp/* \
--input-fields-terminated-by "\\01" --update-mode allowinsert \
--update-key date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id \
--columns 'date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id,success_orders,created' \
;


--退款成功的订单

set hive.exec.mode.local.auto=true;
set hive.groupby.skewindata=true;
set hive.map.aggr=true;
set hive.exec.parallel=true;
set hive.exec.mode.local.auto.input.files.max=5;
insert overwrite table stats_order_tmp
select date_udf(dt),platform_udf(pl),currencyll(cut),paymentypeaa(pt),sum(ct),dt
from(
select
from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd") as dt,
l.pl as pl,
l.cut as cut,
l.pt as pt,
count(distinct l.oid) as ct
from log l
where l.month = 12
and day = 13
and l.oid is not null
and l.oid <> 'null'
and l.en = 'e_cr'
group by from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd"),pl,cut,pt
) as tmp
group by dt,pl,cut,pt
;


计算订单个数的写成一条语句如下：

set hive.exec.mode.local.auto=true;
set hive.groupby.skewindata=true;
set hive.map.aggr=true;
set hive.exec.parallel=true;
set hive.exec.mode.local.auto.input.files.max=5;
with tmp as(
select
from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd") as dt,
l.pl as pl,
l.cut as cut,
l.pt as pt,
l.en as en,
if((case when l.en = 'e_crt' then count(distinct l.oid) end) is null,0,(case when l.en = 'e_crt' then count(distinct l.oid) end))as orders,
if((case when l.en = 'e_cs' then count(distinct l.oid) end) is null,0,(case when l.en = 'e_cs' then count(distinct l.oid) end))as success_orders,
if((case when l.en = 'e_cr' then count(distinct l.oid) end) is null,0,(case when l.en = 'e_cr' then count(distinct l.oid) end))as refund_orders
from log l
where l.month = 12
and day = 13
and l.oid is not null
and l.oid <> 'null'
group by from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd"),pl,cut,pt,l.en
)
from(
select dt as dt1,pl as pl ,cut as cut,pt as pt,orders as orders,0 as success_orders,0 as refund_orders,dt from tmp where en = 'e_crt'
union all
select dt as dt1,pl as pl ,cut as cut,pt as pt,0 as orders,success_orders as success_orders,0 as refund_orders,dt from tmp where en = 'e_cs'
union all
select dt as dt1,pl as pl ,cut as cut,pt as pt,0 as orders,0 as success_orders,refund_orders as refund_orders,dt from tmp where en = 'e_cr'
) as tmp1
insert overwrite table stats_order_tmp1
select date_udf(dt1),platform_udf(pl),currencyll(cut),paymentypeaa(pt),sum(orders),sum(success_orders),sum(refund_orders),dt1
group by dt1,pl,cut,pt
;

sqoop export --connect jdbc:mysql://bigdata:3306/dbfirst \
--username root --password root -m 1 \
--table stats_order --export-dir //bigdata:8020/user/hive/warehouse/mobile.db/stats_order_tmp1/* \
--input-fields-terminated-by "\\01" --update-mode allowinsert \
--update-key date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id \
--columns 'date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id,orders,success_orders,refund_orders,created' \
;
####将三种金额的计算写成一条语句

set hive.exec.mode.local.auto=true;
set hive.groupby.skewindata=true;
set hive.map.aggr=true;
set hive.exec.parallel=true;
set hive.exec.mode.local.auto.input.files.max=5;
insert overwrite table stats_order_tmp2
select date_udf(dt),platform_udf(pl),currencyll(cut),paymentypeaa(pt),sum(orders_amount),sum(success_orders_amount),sum(refund_orders_amount),dt
from(
	select
	from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd") as dt,
	l.pl as pl,
	l.cut as cut,
	l.pt as pt,
	if((case when l.en = 'e_crt' then sum(l.cua) end) is null,0,(case when l.en = 'e_crt' then sum(l.cua) end))as orders_amount,
	if((case when l.en = 'e_cs' then sum(l.cua) end) is null,0,(case when l.en = 'e_cs' then sum(l.cua) end))as success_orders_amount,
	if((case when l.en = 'e_cr' then sum(l.cua) end) is null,0,(case when l.en = 'e_cr' then sum(l.cua) end))as refund_orders_amount
	from log l
	where l.month = 12
	and l.day = 13
	and l.oid is not null
	and l.oid <> 'null'
	group by from_unixtime(cast(l.s_time/1000 as bigint),"yyyy-MM-dd"),l.pl,l.cut,l.pt,l.en
) as tmp
group by dt,pl,cut,pt
;


sqoop export --connect jdbc:mysql://bigdata:3306/dbfirst \
--username root --password root -m 1 \
--table stats_order --export-dir //bigdata:8020/user/hive/warehouse/mobile.db/stats_order_tmp2/* \
--input-fields-terminated-by "\\01" --update-mode allowinsert \
--update-key date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id \
--columns 'date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id,order_amount,revenue_amount,refund_amount,created' \
;


计算总金额
create external table total (
success_orders_amount int,
refund_orders_amount int,
date_dimension_id int,
platform_dimension_id int,
revenue_amount int,
refund_amount int,
created string
)row format delimited fields terminated by '\001';

insert overwrite table total
select
date_dimension_id,
platform_dimension_id,
currency_type_dimension_id,
payment_type_dimension_id,
sum(success_orders_amount),
sum(refund_orders_amount),
created
from stats_order_tmp2
group by
date_dimension_id,
platform_dimension_id,
currency_type_dimension_id,
payment_type_dimension_id,
created;


sqoop export --connect jdbc:mysql://bigdata:3306/dbfirst \
--username root --password root -m 1 \
--table stats_order --export-dir //bigdata:8020/user/hive/warehouse/mobile.db/total/* \
--input-fields-terminated-by "\\01" --update-mode allowinsert \
--update-key date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id \
--columns 'date_dimension_id,platform_dimension_id,currency_type_dimension_id,payment_type_dimension_id,total_revenue_amount,total_refund_amount,created' \
;