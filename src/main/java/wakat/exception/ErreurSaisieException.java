package wakat.exception;

public class ErreurSaisieException  extends  RuntimeException{
    private String code;

    public ErreurSaisieException(String code, String message) {
        super(message);
        this.code = code;
    }
}
