package com.mobile.paser.MR.session;

import com.mobile.Util.TimeUtil;
import com.mobile.common.kpiTypeEnum;
import com.mobile.paser.modle.dim.base.StatsUserDiemension;
import com.mobile.paser.modle.dim.value.map.TimeOutPutWriteable;
import com.mobile.paser.modle.dim.value.reduce.ReduceOutPUtWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 21:57
 * @Description:
 *      k->StatsUserDiemension-》
 *      dimension_browser（浏览器的维度），dimension_date（时间维度），dimension_kpi（kpi维度），dimension_platform（平台维度）
 *      v->ReduceOutPUtWritable kpiname+mapWritable<k,新增用户数>
 *
 */
public class SessionReduce extends Reducer<StatsUserDiemension,TimeOutPutWriteable,StatsUserDiemension,ReduceOutPUtWritable>{
    private ReduceOutPUtWritable reduceoutvalue = new ReduceOutPUtWritable();
    private HashSet<String> set= new HashSet<String>();
    private HashMap<Integer,HashSet<String>> hourlyMap= new HashMap();
    private MapWritable hourlymapWritable=new MapWritable();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
    for(int i=0;i<24;i++){
        hourlyMap.put(i, new HashSet<String>());
        hourlymapWritable.put(new IntWritable(), new IntWritable());

        }
    }

    @Override
    protected void reduce(StatsUserDiemension key, Iterable<TimeOutPutWriteable> values, Context context) throws IOException, InterruptedException {

        MapWritable mapWritable=new MapWritable();
        long sessionLength=0;
        //将value放入到 arraylist集合中 D0AF9CBB-250A-46BF-92AD-FC1E253A29691543935934161
        ArrayList<String> arrayList = new ArrayList<>();

        //将每个小时的用户的sessionID存放到集合中
        for (TimeOutPutWriteable value : values) {
            int hourlyTonday = TimeUtil.getHourlyTonday(value.getTime()*1000);
           hourlyMap.get(hourlyTonday).add(value.getId());

        }
        for (Map.Entry<Integer, HashSet<String>> entry : hourlyMap.entrySet()) {
            hourlymapWritable.put(new IntWritable(entry.getKey()), new IntWritable(entry.getValue().size()));
        }

        for (TimeOutPutWriteable value : values) {
            arrayList.add(value.getId()+ ","+value.getTime());//usd  time
            set.add(value.getId());

        }
        //将相同的u_sd的时间时间相减 得到session时长

        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            ArrayList<Long> sessionTime = new ArrayList<>();
            String usd=iterator.next();
            for(int j=0;j<arrayList.size();j++){
                String[] split = arrayList.get(j).split(",");
                String us=split[0];
                String C_time=split[1];
                if(usd.equals(us)){
                    sessionTime.add(Long.valueOf(C_time));
                }
            }
            if(sessionTime.size()>1){
                Collections.sort(sessionTime);
                sessionLength+= sessionTime.get(sessionTime.size()-1) - sessionTime.get(0);
            }


        }
        Text text = new Text();
        text.set(set.size()+","+sessionLength);
        mapWritable.put(new IntWritable(-1), text);



        reduceoutvalue.setValue(mapWritable);
        reduceoutvalue.setKpiname(kpiTypeEnum.valueofKpi(key.getStatsCommonDimension().getKpiDimension().getKpi_name()));

        context.write(key, reduceoutvalue);


    }
}
