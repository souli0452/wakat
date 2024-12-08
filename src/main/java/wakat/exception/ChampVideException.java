package wakat.exception;

public class ChampVideException extends RuntimeException{
    private String code;

    public ChampVideException(String message, String code) {
        super(message);
        this.code = code;
    }
}
