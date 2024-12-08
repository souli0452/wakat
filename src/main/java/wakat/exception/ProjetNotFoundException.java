package wakat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjetNotFoundException extends RuntimeException{
    private   String code;
    public ProjetNotFoundException(String message) {
        super(message);
    }

    public ProjetNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
}
