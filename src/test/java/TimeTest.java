import com.mobile.common.GlobalConstans;

import java.text.ParseException;

/**
 * @Auther: 72428
 * @Date: 2018/12/5 19:50
 * @Description:
 */
public class TimeTest {
    public static void main(String[] args) throws ParseException {
/*        Calendar calendar=Calendar.getInstance();
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        int d = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -7;
        } else {
            d = 1 - calendar.get(Calendar.DAY_OF_WEEK);
        }
        calendar.add(Calendar.DAY_OF_WEEK, d);
        System.out.println(calendar.getTime().toString());*/
        String dates= GlobalConstans.RUNNING_DATE;
        System.out.println("dates = " + dates);
/*        String date="1543935932583";
        long a=Long.valueOf(date);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(new Date(a)  ));*/
    }
}
