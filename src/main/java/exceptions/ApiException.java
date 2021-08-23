
package exceptions;

public class ApiException extends RuntimeException {
    private final int code;

    public ApiException (int code, String msg){
        super(msg);
        this.code = code;
    }
}