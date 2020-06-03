package elevators;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Floor {
    public Button[] buttons;
    public int floor_number;

    public Floor(int number){
        this.floor_number = number;
        this.buttons = new Button[2];
        createButtons();
        Arrays.toString(buttons);
    }

    private void createButtons(){
        if(this.floor_number == 10){
            this.buttons[0] = new Button("down");
            return;
        }
        if(this.floor_number == -1){
            this.buttons[0] = new Button("up");
            return;
        }
        this.buttons[0] = new Button("up");
        this.buttons[1] = new Button("down");
        return;
    }

    public List<String> displayOptions(){
        List<String> possibleDirections = Arrays.stream(this.buttons).map(button -> button.direction).collect(Collectors.toList());
        if(possibleDirections.size() > 1){
            System.out.println(String.format("Where would you like to go? \n 1) %s \n 2) %s", possibleDirections.get(0), possibleDirections.get(1)));
        } else {
            System.out.println(String.format("Where would you like to go? \n 1) %s", possibleDirections.get(0)));
        }
        return possibleDirections;
    }
}
