package site.hnuster.plugin.tieba.exception;

public class TieBaTBException extends Exception{

    public TieBaTBException(Throwable cause){
        super("获取tds发生错误", cause);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
