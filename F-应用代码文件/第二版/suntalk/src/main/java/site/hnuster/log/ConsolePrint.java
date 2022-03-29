package site.hnuster.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsolePrint {

    private static final int DECLARING_CLASS = 0;
    private static final int METHOD_NAME = 1;
    private static final int FILE_NAME = 2;
    private static final int LINE_NUMBER = 3;

    public static void printShort(Throwable e){
        try{
            //使用当前系统时间记录
            Date date=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowDate = dateFormat.format(date);

            String message = e.getMessage();
            String cause = e.getCause().toString();
            String location = e.getStackTrace()[DECLARING_CLASS].toString();

            System.out.println(nowDate
                    +"\n描述："+message
                    +"\n原因："+cause
                    +"\n触发初始位置："+location
                    +"\n");
        }catch (Exception logE){
            System.out.println("异常打印出错，请检查抛出的异常对象是否正确！--Cause："+logE.getCause());
        }
    }

    public static void printAll(Throwable e){
        try{
            e.printStackTrace();
        }catch (Exception logE){
            System.out.println("异常打印出错，请检查抛出的异常对象是否正确！--Cause："+logE.getCause());
        }
    }

}
