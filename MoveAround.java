import building.Building;
import elevators.*;
import exceptions.CannotMove;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MoveAround {
    public static void main(String[] args){
        Elevator first_elevator = new Elevator("A", (int)(Math.floor(Math.random() * 10)), 10);
        Elevator second_elevator = new Elevator("B", (int)( Math.floor(Math.random() * 10)), -1);
        Building building = new Building(first_elevator, second_elevator);
        Person person = new Person();
        person.callElevator(building);
        // Add the request to the list of requests to process
        System.out.println(building.requestList);
        // Find an elevator that has capacity
        Elevator elevator = building.findElevatorWithCapacity();
        Scanner reader = new Scanner(System.in);
        try{
            elevator.moveToFloor(building.requestList.get(0), building);
        } catch(CannotMove ex) {
            System.out.println(ex.message);
        } catch(InterruptedException ex) {
            System.out.println(ex);
        }
        if(!elevator.isMoving){
            person.getIn(elevator);
            System.out.println("Number of people inside: ");
            System.out.println(elevator.occupants.size());
        }
        // whose position is greater/lesser than destination
        // and going in the same direction
        // or can go the opposite direction if zero occupants.
        int destination = elevator.displayOptions(building.floors);
        Request latest_request = new Request(elevator.position, new Floor(destination), destination - elevator.position.floor_number > 0 ? "up" : "down");
        building.requestList.add(latest_request);
        for (Request request : building.requestList) {
            System.out.println(request.requestId);
            System.out.println(request.to);
            System.out.println(request.direction);
        }
        try{
            elevator.moveToFloor(latest_request, building);
        }catch(CannotMove ex){

        }catch(InterruptedException ex){

        }

    }
}
