package elevators;

public class ResetButton {
    public boolean pushed;
    public ResetButton(){
    }
    public void push(){
        this.pushed = !this.pushed;
    }
}
