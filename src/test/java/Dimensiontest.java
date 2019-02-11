import com.mobile.paser.modle.dim.base.BrowserDiemenson;
import com.mobile.paser.modle.dim.base.DateDimension;
import com.mobile.paser.modle.dim.base.PlatfromDimension;
import com.mobile.paser.service.serviceImpl.DimensionOPerateImpl;

import java.util.Date;

/**
 * @Auther: 72428
 * @Date: 2018/12/6 11:10
 * @Description:
 */
public class Dimensiontest {
    public static void main(String[] args) {
        BrowserDiemenson browserDiemenson = new BrowserDiemenson("IE","10.0");
        DimensionOPerateImpl dimensionOPerate = new DimensionOPerateImpl();
        PlatfromDimension platfromDimension = new PlatfromDimension("ios");
        DateDimension dateDimension = new DateDimension(2018,4,12,6,1,new Date(),"year");
        System.out.println(dimensionOPerate.getDimensionIdByDimension(browserDiemenson));
    }
}
