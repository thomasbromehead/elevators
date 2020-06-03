import building.Building;
import elevators.*;
import exceptions.CannotMove;
;
import java.util.Scanner;

// I want to do the Extra Credit part with the script running on its own
// I have objects for everything as you will see, Elevators Call (Request), Person, Floor, Elevator, Emergency Button etc
// I am unsure if an elevator should stop on its way and pick up other passengers.
// I am keeping track of the Person making the Request so that I know which occupant to pop
// I wonder if
// If the EmergencyButton is pressed it should also interrupt the moveToFloor function, unsure how to do that.
// Also, should I use a concurrent List or multi-threading so that each elevator can access the requestList without race conditions (needed...?)
// I'm a Junior dev and would like to implement something like this
// I think I am taking the assignment quite far but also really liking it so hopefully you can adjust if I am digging too deep.

public class MoveAround {
    // In this main method I am testing with just a single user
    public static void main(String[] args) throws InterruptedException {
        // Create Elevators
        Elevator firstElevator = new Elevator("A", (int)(Math.floor(Math.random() * 10)), 10);
        Elevator secondElevator = new Elevator("B", (int)( Math.floor(Math.random() * 10)), -1);
        // Instantiate Building
        Building building = new Building(firstElevator, secondElevator);
        // Instantiate Person
        Person person = new Person();
        // Call Elevator
        String direction = person.callElevator(building);
        // Print List of Requests/Calls
        System.out.println(building.requestList);
        // Find an elevator that has capacity
        Elevator elevator = building.findElevatorWithCapacity();
        Scanner reader = new Scanner(System.in);
        // Try moving to the floor requested
        try{
            elevator.moveToFloor(building.requestList.get(0), building);
        } catch(CannotMove ex) {
            System.out.println(ex.message);
        } catch(InterruptedException ex) {
            System.out.println(ex);
        }
        if(!elevator.isMoving){
            // Get Person Inside
            person.getIn(elevator);
            System.out.println("Number of people inside: ");
            System.out.println(elevator.occupants.size());
        }
        int destination = elevator.displayOptions(building.floors, person.position.floor_number, direction);
        // Build new request - new Request(to, from, direction)
        Request latest_request = new Request(new Floor(destination), elevator.position, destination - elevator.position.floor_number > 0 ? "up" : "down", person);
        building.requestList.add(latest_request);
        for (Request request : building.requestList) {
            System.out.println(request.requestId);
            System.out.println(request.to);
            System.out.println(request.direction);
            System.out.println(request.caller.id);
        }
        try{
            elevator.moveToFloor(latest_request, building);
            person.pushEmergencyButton(elevator);
        }catch(CannotMove ex){

        }catch(InterruptedException ex){

        }
        // Extra Credit Part -- TO BE IMPLEMENTED
/*        for(int i = 0; i < 180; i++){
            // Sleep thread for one second
            Thread.sleep(1000);
            // Do the rest

        }*/
    }
}
