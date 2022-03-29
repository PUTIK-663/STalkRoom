package site.hnuster.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

/**
 * 集合了加密方法的类
 */
public class Encoder {

    public static String sha1(List<String> paraList){
        //1.字典排序三个参数，并sha1加密
        //1.1字典排序
        paraList.sort((str1, str2) -> {
            char[] chars1 = str1.toCharArray();
            char[] chars2 = str2.toCharArray();
            int i = 0;
            while (i < chars1.length && i < chars2.length) {
                if (chars1[i] > chars2[i]) return 1;
                else if (chars1[i] < chars2[i]) return -1;
                else i++;
            }
            if (i == chars1.length) {//1到头
                return -1;
            }
            if (i == chars2.length) {//2到头
                return 1;
            }
            return 0;
        });
        //1.2sha1加密
        StringBuilder nowSignature = new StringBuilder();
        for (String para:paraList){
            nowSignature.append(para);
        }
        nowSignature = new StringBuilder(DigestUtils.sha1Hex(nowSignature.toString()));
        return nowSignature.toString();
    }
}
