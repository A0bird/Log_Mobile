import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Auther: 72428
 * @Date: 2018/12/14 17:04
 * @Description:
 */
public class Anagram {
    public static void main(String[] args) {

        String str="rail safety fairy tales";
        boolean flag=false;
        int count=0;
        //将字符串解析切割
        String[] fileds=str.split(" ");
        //将每个两个单词存放到为一个元素
        //将每个元素全部转化为小写并且去除空格，再按照字典顺序排序
        ArrayList<String> strings = new ArrayList<>();
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=1;i<fileds.length;i+=2){
            char[] chars = (fileds[i-1]+fileds[i]).toCharArray();
            Arrays.sort(chars);
            for (char aChar : chars) {
                StringBuffer append = stringBuffer.append(aChar);
            }
            stringBuffer.append(",");
        }
        String[] split = stringBuffer.toString().split(",");
        for (String s : split) {
            strings.add(s);
        }
        //对数组中的每个元素进行比较
        for(int j=1;j<strings.size();j++){
            flag= strings.get(j-1).equalsIgnoreCase(strings.get(j));
            if(flag==true){
                count++;
            }
        }
        System.out.println("count = " + count);

    }
}
