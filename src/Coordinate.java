import java.util.ArrayList;

public class Coordinate {
    int row;
    int col;
    int fValue;
    int gValue;
    ArrayList<Coordinate> path = new ArrayList<>();

    public Coordinate(int r, int c, int fval){
        row = r;
        col = c;
        fValue = fval;
    }

    public Coordinate(int r, int c){
        row = r;
        col = c;
    }

    public void setFValue(int fValue){
        this.fValue = fValue;
    }

    public void setGValue(int gValue){
        this.gValue = gValue;
    }

    public void addToPath(Coordinate c){
        path.add(c);
    }
}
