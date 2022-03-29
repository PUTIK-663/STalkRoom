package site.hnuster.controller.filter;

import java.util.List;

interface RequestChecking {

    /**
     * 检查该数组中的字符串对象是否有为null的对象
     * @param ckList 字符串list
     * @return 存在null则返回true,否则返回false
     */
    default boolean ckAnyNull(List<String> ckList){
        for (String s:ckList){
            if (s==null) return true;
        }
        return false;
    }

    /**
     * 检查数组中的字符串是否有为空的字符串
     * @param ckList 字符串list
     * @return 存在为空的字符串返回true，否则返回false
     */

    default boolean ckAnyEmpty(List<String> ckList){
        for (String s:ckList){
            if (s.length()==0) return true;
        }
        return false;
    }
    /**
     * 检查字符串类型
     * @return 暂时未定
     */
    boolean ckType(List<?> ckList);

    /**
     * 检查字符串是否超过最大长度
     * @param ckList 字符串list
     * @param maxLength 最大长度
     * @return 超过返回true,否则返回false
     */
    default boolean ckLength(List<String> ckList,int maxLength){
        for (String s:ckList){
            if (s.length()>maxLength) return true;
        }
        return false;
    }

}
