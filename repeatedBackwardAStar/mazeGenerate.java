import java.io.*;
import java.util.Random; 

public class mazeGenerate {
    

    public static char BLOCKED = '■'; 
    public static char UNBLOCKED ='□'; 
    public static char TRAVERSED ='X'; 

    public char[][] grid; 
    public boolean[][] visible; 

    public State startState; 
    public State goalState; 

    mazeGenerate(int length, int width){
        this.grid = new char[length][width]; 
        this.visible = new boolean[length][width]; 

        startState = new State(this, length-1, width-1, Integer.MAX_VALUE, null ); 
        addNeighbors(startState);

        goalState = new State(this, 0, 0, 0, null); 

        Random rand = new Random(); 

        //randomly block 30 percent of boxes 
        for(int i=0; i<length; i++){
            for(int j=0; j<width; j++){
                int randomNum = rand.nextInt(100);
                if (randomNum <= 30) {
                    grid[i][j] = BLOCKED; // Blocked
                } else {
                    grid[i][j] = UNBLOCKED; // Unblocked
                }
            }
        }

        print(); 
    }

    public void setStart(State state){
        startState = state; 
    }

    public void markTraversed(State state){
        grid[state.row][state.col] = mazeGenerate.TRAVERSED; 
        addNeighbors(state);
    }

    public void addNeighbors(State state){
        visible[state.row][state.col] = true; 
        if (state.row-1 >=0) {
            visible[state.row-1][state.col] = true; 
        }
        if(state.row+1 < visible.length){
            visible[state.row+1][state.col] = true;
        }
        if(state.col-1 >= 0){
            visible[state.row][state.col-1] = true;
        }
        if(state.col+1 < visible[0].length){
            visible[state.row][state.col+1] = true;
        }
    }

    public void print(){
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    

}
