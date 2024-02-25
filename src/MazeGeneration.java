import java.util.ArrayList;
import java.util.Random;
import java.util.*;

public class MazeGeneration {
    static int numbCols = 2;
    static int numbRows = 2;
    static int numbMazes = 50;

    static ArrayList<Boolean[][]> mazeList = new ArrayList<>();
    static int[][] heuristicValues = new int[numbRows][numbCols];
    static Coordinate goal = new Coordinate(numbRows -1, numbCols - 1);

    static double chanceBlocked = .3;
    static int randomSeed = 500;

    static int lastTime = 0;

    public static void main(String[] args) {
        generateMazes();
        drawMazes();
        setHeuristicValues();
        runAllTestsForward();
        runAllTestsBackward();
    }

    private static void runAllTestsForward(){
        System.out.println("Forward Tests");
        for(int i = 0; i < mazeList.size(); i++){
            lastTime = (int) System.currentTimeMillis();
            runTestForward(i);
        }
    }

    private static void runAllTestsBackward(){
        System.out.println("Backward Tests");
        for(int i = 0; i < mazeList.size(); i++){
            lastTime = (int) System.currentTimeMillis();
            runTestBackward(i);
        }
    }

    private static void runTestForward(int maze){
        RepeatedForwardA r = new RepeatedForwardA(goal, heuristicValues, mazeList.get(maze));
        r.printCoords();
        System.out.println("Time solving maze " + (maze + 1) + ": " + ((System.currentTimeMillis() - lastTime) % 10000));
    }

    private static void runTestBackward(int maze){
        RepeatedBackwardA r = new RepeatedBackwardA(new Coordinate(0, 0), heuristicValues, mazeList.get(maze));
        r.printCoords();
        System.out.println("Time solving maze " + (maze + 1) + ": " + ((System.currentTimeMillis() - lastTime) % 10000));
    }

    public static void setHeuristicValues(){
        for(int i = 0; i < numbRows; i++){
            for(int j = 0; j < numbCols; j++){
                int heuristic = 0;
                int row = goal.row;
                int col = goal.col;
                while(row != i){
                    row--;
                    heuristic++;
                }
                while(col != j){
                    col--;
                    heuristic++;
                }
                heuristicValues[i][j] = heuristic;
            }
        }
    }

    public static void printHeuristics(){
        System.out.println("Heuristic value maze: ");
        for(int i = 0; i < numbRows; i++) {
            for (int j = 0; j < numbCols; j++) {
                System.out.print(heuristicValues[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void generateMazes(){
        Random rand = new Random(randomSeed);
        //Start off on first maze.
        for(int i = 0; i < numbMazes; i++) {
            Boolean[][] maze = new Boolean[numbCols][numbRows];
            for(int j = 0; j < numbRows; j++){
                for(int k = 0; k < numbCols; k++){
                    double random = rand.nextInt(10);
                    random *= .1;
                    if((j == 0 && k == 0) || (j == numbRows - 1 && k == numbCols - 1)){
                        maze[j][k] = true;
                    }
                    else if(random <= chanceBlocked){
                        maze[j][k] = false;
                    }
                    else{
                        maze[j][k] = true;
                    }
                }
            }
            // At this point a full maze has been done generating, so add it to
            // finished maze array.
            mazeList.add(maze);
        }
    }

    public static void findAndPickNeighbor(Coordinate a, Stack s){
        boolean picked = false;
        while(picked = false){

        }
        if(a.row + 1 < numbRows){
        }
        if(a.row - 1 >= 0){
        }
        if(a.col - 1 >= 0){
        }
        if(a.col + 1 < numbCols){
        }
    }

    public static void drawMazes(){
        for(int i = 0; i < numbMazes; i++){
            System.out.println("---------- MAZE " + (i + 1) + " ----------");
            for(int j = 0; j < numbRows; j++){
                for(int k = 0; k < numbCols; k++){
                    if (mazeList.get(i)[j][k] == true){
                        System.out.print("□ ");
                    }
                    else{
                        System.out.print("■ ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}