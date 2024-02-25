import java.util.ArrayList;

public class RepeatedBackwardA {
    private Coordinate startState = new Coordinate(1, 1);
    private Coordinate agent = new Coordinate(0, 0);
    private static Coordinate goal;
    private int heuristics[][];
    // Open list, sort by f value, g(s) + h(s)
    // g(s) is from start state to current state, h(s) is heuristic (Manhattan distances)
    private BinaryHeap openList = new BinaryHeap();
    private ArrayList<Coordinate> openListTracker = new ArrayList<>();
    private ArrayList<Coordinate> closedList = new ArrayList<>();
    private Boolean[][] maze;
    private Boolean[][] blankMaze;
    ArrayList<Coordinate> finalPath = new ArrayList<>();
    private final int MAX_ITERATIONS = 101*101;

    ArrayList<Coordinate> path = new ArrayList<Coordinate>();

    public RepeatedBackwardA(Coordinate goal, int heuristics[][], Boolean[][] maze){
        this.goal = new Coordinate(0, 0);
        this.heuristics = heuristics;
        this.maze = maze;
        agent.setFValue(calculateFValue(agent));
        initializeBlankMaze();
    }

    private void initializeBlankMaze(){
        blankMaze = new Boolean[maze.length][maze[0].length];
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[0].length; j++){
                blankMaze[i][j] = true;
            }
        }
    }

    private int findGofS(Coordinate state){
        int gOfS = 0;
        int row = startState.row;
        int col = startState.col;
        while(row != state.row){
            row++;
            gOfS++;
        }
        while(col != state.col){
            col++;
            gOfS++;
        }
        return gOfS;
    }

    private ArrayList<Coordinate> aStar(Coordinate start) {
        openList = new BinaryHeap();
        closedList = new ArrayList<>();
        int k = 0;
        ArrayList<Coordinate> possibleNeighbors = new ArrayList<>();
        while ((start.row != goal.row || start.col != goal.col) && k < 10) {
            ArrayList<Coordinate> neighbors = getNeighbors(start);
            closedList.add(start);
            // Add neighbors to the open list.
            for (int i = 0; i < neighbors.size(); i++) {
                Coordinate curNeighbor = neighbors.get(i);
                // Only add if the space is unblocked in the blank maze
                if (blankMaze[curNeighbor.row][curNeighbor.col]
                        && !listContains(curNeighbor, closedList)) {
                    possibleNeighbors.add(curNeighbor);
                }
            }
            if(possibleNeighbors.isEmpty()){
                for (int i = 0; i < neighbors.size(); i++) {
                    Coordinate curNeighbor = neighbors.get(i);
                    // Only add if the space is unblocked in the blank maze
                    if (blankMaze[curNeighbor.row][curNeighbor.col]) {
                        possibleNeighbors.add(curNeighbor);
                    }
                }
            }
            if(possibleNeighbors.isEmpty()){
                break;
            }
            Coordinate minNeighbor = possibleNeighbors.get(0);
            for(int i = 0; i < possibleNeighbors.size() - 1; i++){
                if(findHValue(minNeighbor) > findHValue(possibleNeighbors.get(i+1))){
                    minNeighbor = possibleNeighbors.get(i + 1);
                }
            }
            start = minNeighbor;
            if(start == null){
                return closedList;
            }
            possibleNeighbors.clear();
            k++;
        }
        closedList.add(start);
        return closedList;
    }

    private void repeatedAStar(){
        ArrayList<Coordinate> path = aStar(startState);
        agent = startState;
        int iteration = 0;
        while((agent.row != goal.row || agent.col != goal.col) && iteration < 10){
            for (int i = 1; i < path.size(); i++) {
                updateBlankMaze(agent);
                if (maze[path.get(i).row][path.get(i).col]) {
                    finalPath.add(agent);
                    agent.setLastCoord(agent);
                    agent = path.get(i);
                } else {
                    break;
                }
            }
            do{
                path = aStar(agent);
                if(agent.lastCoord == null){
                    break;
                }
                agent = agent.lastCoord;
                path = aStar(agent);
            }while(path == null);
            iteration++;
        }
        finalPath.add(agent);
        if(MAX_ITERATIONS != iteration){
            fixFinalPath();
            /*
            for(int i = 0; i < finalPath.size(); i++){
                System.out.println("path: (" + finalPath.get(i).row + ", " + finalPath.get(i).col + ")");
            }
            */
            System.out.println("Solution found.");
        }
        else{
            System.out.println("No solution found.");
        }
    }

    private void fixFinalPath(){
        for(int i = 0; i < finalPath.size(); i++){
            Coordinate cur = finalPath.get(i);
            for(int j = i + 1; j < finalPath.size(); j++){
                if(finalPath.get(j).row == cur.row && finalPath.get(j).col == cur.col){
                    int b = j;
                    while(b != i){
                        finalPath.remove(b);
                        b--;
                    }
                }
            }
        }
    }

    private int findHValue(Coordinate c){
        return heuristics[c.row][c.col];
    }

    private boolean listContains(Coordinate c, ArrayList<Coordinate> a){
        if(a == null){
            return false;
        }
        for(int i = 0; i < a.size(); i++){
            if(c.row == a.get(i).row && c.col == a.get(i).col){
                return true;
            }
        }
        return false;
    }

    public void printCoords(){
        repeatedAStar();
    }

    // To update the blank maze with the current coordinate's neighbors
    private void updateBlankMaze(Coordinate c){
        ArrayList<Coordinate> neighbors = getNeighbors(c);
        for(int i = 0; i < neighbors.size(); i++){
            Coordinate n = neighbors.get(i);
            if(!maze[n.row][n.col]){
                blankMaze[n.row][n.col] = false;
            }
        }
    }


    private int calculateFValue(Coordinate state){
        return findGofS(state) + heuristics[state.row][state.col];
    }

    private ArrayList<Coordinate> getNeighbors(Coordinate c){
        ArrayList<Coordinate> neighbors = new ArrayList<>();
        // Down neighbor
        if(c.row + 1 <= goal.row){
            Coordinate h = new Coordinate(c.row + 1, c.col, calculateFValue(new Coordinate(c.row + 1, c.col)));
            neighbors.add(h);
        }
        // Up neighbor
        if(c.row - 1 >= 0){
            neighbors.add(new Coordinate(c.row - 1, c.col, calculateFValue(new Coordinate(c.row - 1, c.col))));
        }
        // Left neighbor
        if(c.col - 1 >= 0){
            neighbors.add(new Coordinate(c.row, c.col - 1, calculateFValue(new Coordinate(c.row, c.col - 1))));
        }
        // Right neighbor
        if(c.col + 1 <= goal.col){
            neighbors.add(new Coordinate(c.row, c.col + 1, calculateFValue(new Coordinate(c.row, c.col + 1))));
        }
        return neighbors;
    }
}

