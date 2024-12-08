package wakat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@ControllerAdvice
public class RestHandleException {
    /**
     *
     Toutes les exception personalisée se gererons ici
     * @param e
     * @return
     */
    @ExceptionHandler(value ={ErreurSaisieException.class} )
    public ResponseEntity<Object> handleErreurSaisieException(ErreurSaisieException e){
        WakatException wakatException= new WakatException(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(wakatException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ChampVideException.class})
    void handleChamVideException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(value ={UserNotFoundException.class} )
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e){
        WakatException wakatException= new WakatException(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(wakatException,HttpStatus.NOT_FOUND);
    }

}
