package elevators;

public class Request {
    public Floor to;
    public Floor from;
    public String direction;
    public static long ids = 0;
    public long requestId;
    public boolean processing;

    public Request(Floor to, Floor from, String direction){
        this.to = to;
        this.from = from;
        this.direction = direction;
        Request.ids++;
        this.requestId = Request.ids;
    }
}
