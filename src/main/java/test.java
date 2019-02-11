import com.mobile.common.EventEnum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @Auther: 72428
 * @Date: 2018/12/7 16:51
 * @Description:
 */
public class test {
    public static void main(String[] args) {
        System.out.println(EventEnum.LANUCH.alias);
    }

    private static String findCode(String ip) {
        //读取文件内容
        ArrayList<String> strings = new ArrayList<>();
        try {
            String fileName="D:/千锋项目/项目阶段周考/iprule";
            FileReader fileReader=new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fileReader);
            String str="";
            while((str=bf.readLine())!=null){
                strings.add(str);
            }
            bf.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将strings数组中的每一个元素进行切割，存放到linkedHashmap中
        ArrayList<String[]> startiplist = new ArrayList<>();
        for (String ipcode : strings) {
            String[] fileds =ipcode.split(",");
            String[] startIP=fileds[1].split(".");
            startiplist.add(startIP);
        }

        return null;
    }

}
