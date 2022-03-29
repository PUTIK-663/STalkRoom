package site.hnuster.plugin;

import site.hnuster.plugin.tieba.Sign;

import java.util.HashMap;

public final class Launcher {

    public static final HashMap<String,String> cookieMap = new HashMap<>();

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("自动工具启动器启动...");
                int maxRunTimes = 31;
                while (maxRunTimes>0){
                    for (String cookie:cookieMap.keySet()){
                        new Sign().execute(cookieMap.get(cookie));
                    }
                    try {
                        Thread.sleep(1000*60*60*24);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    maxRunTimes--;
                }
            }
        }).start();
    }

}
