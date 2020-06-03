package elevators;

public class EmergencyButton {
    public boolean pushed;
    public int timesPushed;
    public EmergencyButton(){
        this.pushed = false;
        this.timesPushed = 0;
    }
    public void push(){
        this.pushed = !this.pushed;
        this.timesPushed++;
    }
}
