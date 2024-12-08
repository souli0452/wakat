package wakat.exception;

public class ModuleNotFoundException extends RuntimeException{

    public ModuleNotFoundException(String message){
        super(message);
    }
}
