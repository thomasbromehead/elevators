package elevators;

import building.Building;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class Person {
    public Floor position;
    public static int ids = 0;
    public Floor destination;
    public LocalTime trip_start_time;
    public LocalTime trip_end_time;
    public int id;

    public Person(){
        Person.ids++;
        this.id = Person.ids;
        this.position = new Floor((int)(Math.floor(Math.random() * 10)));
    }

    public void callElevator(Building building){
        this.trip_start_time = LocalTime.now();
        building.elevatorCalled = true;
        Scanner reader = new Scanner(System.in);
        Map<Integer, String> directions = new HashMap<>(2);
        System.out.println("You are on floor " + this.position.floor_number);
        List<String> possibleDirections = this.position.displayOptions();
        String direction = reader.next();
        for(String myDirection : possibleDirections) {
            directions.put(possibleDirections.indexOf(myDirection) + 1, myDirection);
        }
        System.out.println(directions);
        building.requestList.add(this, new Request(new Floor(this.position.floor_number), new Floor(this.position.floor_number), directions.get(Integer.parseInt(direction))));
    }

    public void getIn(Elevator elevator){
        if(this.position.floor_number == elevator.position.floor_number){
            System.out.println("Occupant stepping into the elevator");
            elevator.occupants.add(this);
            System.out.println(elevator.occupants.size());
        }
    }

    public void getOut(Elevator elevator){
        Optional<Person> occupant = elevator.occupants.stream().filter(person -> person == this).findFirst();
        if(occupant.isPresent()){
            int personIndex = elevator.occupants.indexOf(occupant);
            elevator.occupants.remove(occupant);
            System.out.println("Occupant successfully stepped out of the elevator");
            this.trip_end_time = LocalTime.now();
            System.out.println(String.format("Total travel time between time elevator was called and arrival (sec): %s", Duration.between(this.trip_start_time, this.trip_end_time).toSeconds()));
        }
    }

}
