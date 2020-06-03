package exceptions;

public class CannotMove extends Exception {
    public String message;
    public CannotMove(String message){
        this.message = message;
    }
}
