package building;

import elevators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Building {
    public Elevator elevatorA;
    public Elevator elevatorB;
    public List<Floor> floors = new ArrayList<>();
    public List<Person> visitors;
    public boolean elevatorCalled;
    public List<Elevator> elevatorList = new ArrayList<>();
    public List<Request> requestList = new ArrayList<>();

    public Building(Elevator firstElevator, Elevator secondElevator){
        this.elevatorA = firstElevator;
        this.elevatorB = secondElevator;
        elevatorList.add(0, this.elevatorA);
        elevatorList.add(1, this.elevatorB);
        createFloors();
        this.visitors = new ArrayList<Person>();
        this.visitors.add(0, new Person());
    }

    private void createFloors(){
        for(int i = -1; i < 11; i++){
          floors.add(i + 1, new Floor(i));
        }
    }

    public void dispatchElevator(){
        List<Elevator> elevatorList = new ArrayList<>();
        Person visitor = this.visitors.get(0);
    }

    public Elevator findElevatorWithCapacity(){
        Optional availableElevator = this.elevatorList.stream().parallel().filter(elevator -> elevator.hasCapacity()).findAny();
        return (Elevator) availableElevator.get();
    }

    public void processRequest(Request request){
        Optional requestToDelete = this.requestList.stream().filter(request1 -> request1.requestId == request.requestId).findFirst();
        if(requestToDelete.isPresent()){
            this.requestList.remove(requestToDelete.get());
        }
    }

}
