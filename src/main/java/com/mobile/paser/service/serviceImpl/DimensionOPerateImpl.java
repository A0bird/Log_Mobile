package com.mobile.paser.service.serviceImpl;

import com.mobile.Util.JDBCUtil;
import com.mobile.Util.PropertiesUtil;
import com.mobile.paser.modle.dim.base.*;
import com.mobile.paser.service.DimensionOperateI;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 23:12
 * @Description:
 */
public class DimensionOPerateImpl implements DimensionOperateI{
    private  static Logger logger=Logger.getLogger(DimensionOPerateImpl.class);
    //定义一个内存级别的缓存  对象属性的拼接key->维度信息 value->维度所对应的id
     private Map<String,Integer> cache= new LinkedHashMap<String,Integer>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
            return this.size()>5000;
        }
    };

    @Override
    public int getDimensionIdByDimension(BaseDimeension dimension) {
        String cacheKey = buildCacheKey(dimension);
        //判断缓存中是否有对应的cacheKey
        if(this.cache.containsKey(cacheKey)){
            return  this.cache.get(cacheKey);
        }
        //缓存中没有对应的id 1.查询数据库 2.没有的话，插入
        Connection conn= JDBCUtil.getConn();
        String sqls[]=null;
        if(dimension instanceof BrowserDiemenson){
            sqls=buildBrowserSQL();
        }else if(dimension instanceof DateDimension){
            sqls=buildDateSQL();
        } else
        if(dimension instanceof PlatfromDimension){
            sqls=buildPlateSQL();
        } else if( dimension instanceof KpiDimension){
            sqls=buildKpiSQL();
        } else if( dimension instanceof EventDimension){
            sqls=buildEventSQL();
        }else if( dimension instanceof loactionDimension){
            sqls=buildloactionSQL();
        }else if( dimension instanceof payTypeDimension){
            sqls=buildpaySQL();
        }else if( dimension instanceof currencyTypeDimension){
            sqls=buildcurrencyTypeSQL();
        }
        int id=-1;
        synchronized (DimensionOPerateImpl.class){
          id = exectedSQL(conn,sqls,dimension);
        }
        //获取到id后将id存储到cache中
        this.cache.put(cacheKey, id);
        return id;
    }



    private int exectedSQL(Connection conn, String[] sqls, BaseDimeension dimension) {
        PreparedStatement ps=null;
        ResultSet rs=null;
        //先查询-》插入
        try {
            ps =conn.prepareStatement(sqls[0]);
            handleArgs(ps,dimension);
            rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                return id ;
            }
            ps=conn.prepareStatement(sqls[1],Statement.RETURN_GENERATED_KEYS);
            handleArgs(ps,dimension);
            ps.executeUpdate();
            rs= ps.getGeneratedKeys();
            if(rs.next()){
                return  rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void handleArgs(PreparedStatement ps, BaseDimeension dimension) {
        try {
            int i=0;
            if(dimension instanceof  BrowserDiemenson){
                BrowserDiemenson br =(BrowserDiemenson)dimension;
                ps.setString(++i, br.getBrowser_name());
                ps.setString(++i, br.getBrowser_version());
            }else  if(dimension instanceof  KpiDimension){
                KpiDimension kpi=(KpiDimension)dimension;
                ps.setString(++i,kpi.getKpi_name() );
            }else if(dimension instanceof  DateDimension){
                DateDimension date=(DateDimension)dimension;
                ps.setInt(++i, date.getYear());
                ps.setInt(++i, date.getSeason());
                ps.setInt(++i, date.getMounth());
                ps.setInt(++i, date.getWeek());
                ps.setInt(++i, date.getDay());
                ps.setDate(++i, new Date(date.getCalender().getTime()));
                ps.setString(++i, date.getType());
            } if(dimension instanceof PlatfromDimension){
                PlatfromDimension plat=(PlatfromDimension)dimension;
                ps.setString(++i, plat.getPlatform_name());
            } if(dimension instanceof EventDimension){
                EventDimension event=(EventDimension)dimension;
                ps.setString(++i, event.getCategory());
                ps.setString(++i, event.getAction());
            }if(dimension instanceof loactionDimension){
                loactionDimension location=(loactionDimension)dimension;
                ps.setString(++i, location.getCountry());
                ps.setString(++i, location.getProvince());
                ps.setString(++i, location.getCity());
            }else if(dimension instanceof payTypeDimension){
                payTypeDimension paytype=(payTypeDimension)dimension;
                ps.setString(++i,paytype.getPayment_type());
            }else if(dimension instanceof currencyTypeDimension){
                currencyTypeDimension currencyType=(currencyTypeDimension)dimension;
                ps.setString(++i, currencyType.getCurrency_name());
            }

        } catch (SQLException e) {
            logger.warn("数据库中参数赋值异常");
        }
    }

    public static   PropertiesUtil propertiesUtil=new PropertiesUtil();

    private String[] buildpaySQL() {
        propertiesUtil.setFileName("sql.properties");
        String selectSql=propertiesUtil.readPropertyByKey("paySelect");
        String insertSql=propertiesUtil.readPropertyByKey("payInsert");
        return new String[]{selectSql,insertSql};
    }

    private String[] buildcurrencyTypeSQL() {
        propertiesUtil.setFileName("sql.properties");
        String selectSql=propertiesUtil.readPropertyByKey("currencyTypeSelect");
        String insertSql=propertiesUtil.readPropertyByKey("currencyTypeInsert");
        return new String[]{selectSql,insertSql};
    }

    private String[] buildloactionSQL() {
        propertiesUtil.setFileName("sql.properties");
        String selectSql=propertiesUtil.readPropertyByKey("locationSelect");
        String insertSql=propertiesUtil.readPropertyByKey("locationInsert");
        return new String[]{selectSql,insertSql};
    }
    private String[] buildEventSQL() {
    propertiesUtil.setFileName("sql.properties");
    String selectSql=propertiesUtil.readPropertyByKey("eventSelect");
    String insertSql=propertiesUtil.readPropertyByKey("eventInsert");

    return new String[]{selectSql,insertSql};
    }
    private String[] buildKpiSQL() {
        propertiesUtil.setFileName("sql.properties");
        String selectSql=propertiesUtil.readPropertyByKey("kpiSelect");
        String insertsql=propertiesUtil.readPropertyByKey("kpiInsert");
        return new String[]{selectSql,insertsql};
    }

    private String[] buildPlateSQL() {
        propertiesUtil.setFileName("sql.properties");
        String selectSql=propertiesUtil.readPropertyByKey("plateSelect");
        String insertSql=propertiesUtil.readPropertyByKey("plateInsert");
        return new String[]{selectSql,insertSql};
    }

    private String[] buildDateSQL() {
        propertiesUtil.setFileName("sql.properties");
        String selectSql=propertiesUtil.readPropertyByKey("dateSelect");
        String insertSql=propertiesUtil.readPropertyByKey("dateInsert");
        return new String[]{selectSql,insertSql};
    }

    private String[] buildBrowserSQL() {
        propertiesUtil.setFileName("sql.properties");
        String selectSql=propertiesUtil.readPropertyByKey("BrowserSelect");
        String insertSql=propertiesUtil.readPropertyByKey("BrowserInsert");
        return new String[]{selectSql,insertSql};
    }

    private String buildCacheKey(BaseDimeension dimension) {
        StringBuffer sb=new StringBuffer();
        if(dimension instanceof  BrowserDiemenson){
            BrowserDiemenson browserDiemenson=(BrowserDiemenson) dimension;
            sb.append("browser_").append(browserDiemenson.getBrowser_name()).append(browserDiemenson.getBrowser_version());
        }else  if(dimension instanceof  KpiDimension) {
            KpiDimension kpiDimension = (KpiDimension) dimension;
            sb.append("kpi_").append(kpiDimension.getKpi_name());
        }else   if(dimension instanceof  PlatfromDimension) {
            PlatfromDimension platfromDimension = (PlatfromDimension) dimension;
            sb.append("plat_").append(platfromDimension.getPlatform_name());
        }else  if(dimension instanceof  DateDimension){
         DateDimension dateDimension=(DateDimension) dimension;
         sb.append(dateDimension.getYear())
                 .append(dateDimension.getSeason())
                 .append(dateDimension.getMounth())
                 .append(dateDimension.getWeek())
                 .append(dateDimension.getDay())
                .append(dateDimension.getCalender())
                .append(dateDimension.getType());
        }else   if(dimension instanceof  EventDimension) {
            EventDimension event = (EventDimension) dimension;
            sb.append("event_").append(event.getCategory()).append(event.getAction());
        }
        return sb.length()==0 ? "" :sb.toString();
    }
}
