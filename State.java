import java.io.*;
import java.util.ArrayList; 

public class State {

    public mazeGenerate maze; 

    public int row; 
    public int col; 
    public int g; 
    State previousBlock; 

    public State(mazeGenerate maze, int row, int col, int g, State previousBlock){
        this.maze = maze; 
        this.row = row; 
        this.col = col; 
        this.g = g; 
        this.previousBlock = previousBlock;
    }

    public int decision(State possibility){
        int fDif = this.calcF() - possibility.calcF(); 

        if (fDif == 0){ //break the tie with the lower g value 
            int gDif = this.getG() - possibility.getG(); 
            if (gDif != 0) {
                return gDif; 
            }else{ //if g also a tie, break with manhattanDist 
                return this.manhattanDist() - possibility.manhattanDist(); 
            }
        } else { //not a tie 
            return fDif; 

        }
    }

    public int getG(){
        return g; 
    }

    public int calcF(){
        return getG() + manhattanDist(); 
    }

    public boolean isBlocked(){
        return maze.grid[row][col] == mazeGenerate.BLOCKED; //check this line
    }

    public boolean isUnblocked(){
        return maze.grid[row][col] == mazeGenerate.UNBLOCKED; 
    }

    public boolean isValid(){
        if(row<0 || row >= maze.grid.length){ // if state(block location/coordinate) is NOT in the bounds of the maze
            return false; 
        }
        if(col < 0 || col >= maze.grid[0].length){
            return false; 
        }
        if(maze.visible[row][col] && maze.grid[row][col] == mazeGenerate.BLOCKED){
            return false;  
        }
        else return true; 

    }

    
    public State move(String direction){

        //System.out.println("Made it in move()."); 

        //maze.visible[this.row][this.col] = true; 

        //maze.addNeighbors(this); //questionable 

        if (direction == "UP"){
            State s = new State(maze, row-1, col, g+1, this);
            /*
            if (s.row >=0) {
                maze.visible[s.row][s.col] = true; 
            }
            */
            //System.out.println("Made it in up.");
            return s; 
        }
        else if (direction == "DOWN"){
            State s =  new State(maze, row+1, col, g+1, this); 
            /* 
            if(s.row < maze.visible.length){
                maze.visible[s.row][s.col] = true;
            }
            */
            //System.out.println("Made it in down.");
            return s; 
        }
        else if (direction == "LEFT"){
            State s =  new State(maze, row, col-1, g+1, this); 
            /*
            if(s.col >= 0){
                maze.visible[s.row][s.col] = true;
            }
            */
            //System.out.println("Made it in left.");
            return s; 
        }
        else {
            State s =  new State(maze, row, col+1, g+1, this); 
            /*
            if(0 <= s.col && s.col < maze.visible[0].length){
                maze.visible[s.row][s.col] = true;
            }
            */
            //System.out.println("Made it in right.");
            return s; 
        }
    }

    public ArrayList<State> validMoves() {//note use of ArrayList, string directions 

        //System.out.println("Made it in validMoves()."); 

        ArrayList<State> states = new ArrayList<State>(); 

        State state = move("UP"); 
        if (state.isValid()){
            //System.out.println("Made it in validMoves() if condition."); 
            states.add(state); 
        }
        State state1 = move("DOWN"); 
        if (state1.isValid()){
            //System.out.println("Made it in validMoves() if condition.");
            states.add(state1); 
        }
        State state2 = move("LEFT"); 
        if (state2.isValid()){
            //System.out.println("Made it in validMoves() if condition.");
            states.add(state2); 
        }
        State state3 = move("RIGHT"); 
        if (state3.isValid()){
            //System.out.println("Made it in validMoves() if condition.");
            states.add(state3); 
        }

        //System.out.println("Made it to end of validMoves()."); 

        return states; 
        
    }

    public int manhattanDist(){
        return Math.abs(maze.goalState.row - row) + Math.abs(maze.goalState.col - col); 
    }

    public boolean sameState(State possibility){
        return row == possibility.row && col == possibility.col; 
    }


}
