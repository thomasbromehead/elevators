package elevators;

import building.Building;
import exceptions.AlreadyReset;
import exceptions.CannotMove;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Elevator {
    public boolean doors_open;
    public EmergencyButton emergency_button;
    public ResetButton reset_button;
    public Floor position;
    public int maxCapacity;
    public Time trip_time;
    public int forbiddenFloor;
    public boolean isMoving;
    public List<Person> occupants = new ArrayList<Person>();
    public String name;
    public static Scanner reader = new Scanner(System.in);

    public Elevator(String name, int startingFloor, int forbiddenFloor){
        this.emergency_button = new EmergencyButton();
        this.reset_button = new ResetButton();
        this.position = new Floor(startingFloor);
        this.forbiddenFloor = forbiddenFloor;
        this.maxCapacity = 5;
    }

    public boolean canMove(int floor, String direction){
        if(this.position.floor_number == 10 && direction.toLowerCase().equals("up")){
            return false;
        }
        if(this.position.floor_number == -1 && direction.toLowerCase().equals("down")){
            return false;
        }
        if(floor == this.forbiddenFloor){
            return false;
        }
        return true;
    }

    public void goToNearest(Building building){
        try{
            switch(this.position.floor_number){
                case(-1):
                    moveToFloor(new Request(this.position, new Floor(0), "up"), building) ;
                    break;
                case(10):
                    moveToFloor(new Request(this.position, new Floor(9), "down"), building) ;
                default:
                    this.position = new Floor(this.position.floor_number + 1);
            }
        } catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        catch(CannotMove ex)
        {
            System.out.println(ex.message);
        }
    }

    public void moveToFloor(Request request, Building building) throws InterruptedException, CannotMove {
        if(this.maxCapacity >= this.occupants.size()){
            System.out.println(String.format("Currently on floor %s", this.position.floor_number));
            if(canMove(request.to.floor_number, request.direction) && this.position.floor_number == request.to.floor_number) {
                System.out.println("Opening doors immediately, already on the requested floor");
                this.doors_open = true;
                this.isMoving = false;
                return;
            }
            if(canMove(request.to.floor_number, request.direction)){
                request.processing = true;
                System.out.println("Closing doors");
                this.doors_open = false;
                this.isMoving = true;
                System.out.println(String.format("Moving to Floor %s", request.to.floor_number));
                int travelTime = Math.abs(request.to.floor_number - this.position.floor_number);
                Thread.sleep(travelTime * 1_000);
                this.position = new Floor(request.to.floor_number);
                System.out.println(String.format("Arrived on Floor %s", this.position.floor_number));
                System.out.println("Opening doors");
                this.isMoving = false;
                this.doors_open = true;
                request.processing = false;
                boolean clearedRequest = building.processRequest(request);
                if(clearedRequest && this.occupants.size() >= 1){
                    request.caller.getOut(this);
                    System.out.println("Caller " + request.caller.id + " just stepped out.");
                }
            } else {
                throw new CannotMove("You cannot go to this floor");
            }
        }
    }

    public void reset() throws AlreadyReset {
        this.reset_button.push();
        if (this.reset_button.pushed) {
            this.doors_open = false;
            this.emergency_button.timesPushed = 0;
        } else {
            throw new AlreadyReset("You already reset the Elevator");
        }
    }

    public void emergency(){
        this.emergency_button.push();
        if (this.emergency_button.timesPushed > 1) {
            System.out.println("Please reset the elevator by pressing the reset button");
        } else {
            try {
                reset();
            } catch(AlreadyReset ex) {
                System.out.println(ex.message);
                this.doors_open = false;
                this.emergency_button.timesPushed = 0;
            }
        }
    }

    public int displayOptions(List<Floor> floors, int startFrom, String direction){
        List<Integer> floorOptions = floors.stream().map(floor -> floor.floor_number)
                .filter(floorNumber -> direction.equals("up") ? floorNumber > startFrom : floorNumber < startFrom)
                .collect(Collectors.toList());
        System.out.println("Please choose a destination floor");
        floorOptions.forEach(floor -> System.out.println(floor));
        int destination = Integer.parseInt(reader.next());
        System.out.println("User selected to go to floor: " + destination);
        return destination;
    }

    public boolean hasCapacity(){
        return this.occupants.size() < this.maxCapacity;
    }
}
