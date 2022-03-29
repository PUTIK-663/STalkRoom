package site.hnuster.exception;

public class DBOperationException extends Exception{

    public DBOperationException(String message,Throwable cause){
        super(message,cause);
    }

}
