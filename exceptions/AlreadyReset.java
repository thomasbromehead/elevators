package exceptions;

public class AlreadyReset extends Exception {
    public String message;
    public AlreadyReset(String message){
        this.message = message;
    }
}
