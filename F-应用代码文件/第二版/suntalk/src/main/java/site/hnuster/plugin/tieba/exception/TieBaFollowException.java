package site.hnuster.plugin.tieba.exception;

public class TieBaFollowException extends Exception{

    public TieBaFollowException(Throwable cause){
        super("获取贴吧列表部分出现错误", cause);
    }

}
