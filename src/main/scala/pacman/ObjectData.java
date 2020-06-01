package pacman;

import java.io.Serializable;

public class ObjectData implements Serializable {
    public double x;
    public double y;
    public int direction;
    ObjectData(double x, double y){
        this.x = x;
        this.y = y;
    }
    ObjectData(double x, double y, int direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
