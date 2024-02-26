import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator; 

public class repeatedAStar {
    
    public mazeGenerate maze; 
    public StatePriorityQueue open; 
    public ArrayList<State> closed; 

    repeatedAStar(mazeGenerate maze){
        this.maze = maze; 
        this.open = new StatePriorityQueue(); 
        this.closed = new ArrayList<State>(); 
    }

    public static void main(String[] args){

        long[] times = new long[50]; 

        //enclose everything below this line in for loop 1-50 
        process: for(int i=0; i<50; i++){

            long startTime = System.currentTimeMillis();

            //maze
            mazeGenerate maze = new mazeGenerate(101, 101); 

            //make repeated a star search with it  
            repeatedAStar search = new repeatedAStar(maze); //pf 

            //make path variable   
            LinkedList<State> finalPath = new LinkedList<State>(); //full_path 

            //System.out.print("Made it past initialization."); 

            while (true) {
                LinkedList<State> path = search.findPath();

                if (path == null || path.isEmpty()) {
                    System.out.println("No path found.");
                    long endTime = System.currentTimeMillis();
                    long solveTime = endTime - startTime;
                    times[i] = solveTime; 
                    System.out.println("Maze solve time: " + solveTime + " milliseconds");
                    continue process;
                }

                finalPath.addAll(path);

                if(!finalPath.isEmpty()){
                    finalPath.removeLast();
                }else{
                    //System.out.println("Final path is empty?"); 
                }

                State lastState = path.peekLast();
                
                if (!lastState.sameState(search.maze.goalState)){ //used to be !lastState.equals(search.maze.goalState)
                    search.maze.setStart(lastState);
                } else {
                    //System.out.println("Made it in break condition."); 
                    break;
                }
                

            }

            //System.out.println("Made it out of first while loop."); 

            
            long endTime = System.currentTimeMillis();
            long solveTime = endTime - startTime;
            times[i] = solveTime;
            System.out.println("Maze solve time: " + solveTime + " milliseconds");

            search.maze.print();

            ListIterator<State> iter = finalPath.listIterator(0);
            while (iter.hasNext()) {
                State next_state = iter.next();
                System.out.println("(" + next_state.row + "," + next_state.col + ")");
            }
            System.out.println("(" + search.maze.goalState.row + "," + search.maze.goalState.row + ")" );

            

        }
        long sum = 0; 
        for(int j=0; j<50; j++){
            sum += times[j]; 
        }
        long averageSolveTime = sum/50; 

        System.out.println("Average solve time: " + averageSolveTime); 
    }

    public boolean checkIfClosed(State s){
        //System.out.println("Closed Check."); 
        for (int i = 0; i < closed.size(); i++) {
            if (closed.get(i).sameState(s)) { //used to be 
                return true;
            }
        }
        return false;
    }

    public State findStateInHeap(State s){ //now working 

        for (int i = 0; i < open.getQueue().size(); i++) {
            //System.out.println("Queue size: " + open.getQueue().size()); 
            if (s.sameState(open.getQueue().get(i))) { //used to be s.equals(open.getQueue().get(i))
                //System.out.println("Found in queue."); 
                return s;
            }
        }
        return null; 
    }

    public LinkedList<State> pathMaker(State goal){
        LinkedList<State> path = new LinkedList<State>(); 
        State state = goal; 

        path.push(state); //changes to this block make 4.5 not reachable 
        while (!(state=state.previousBlock).sameState(maze.startState)) { //used to be !state.equals(maze.startState)
            //System.out.println("Code made it here 6."); 
            path.push(state); 
        }
        path.push(state);

        LinkedList<State> unblockedPath = new LinkedList<State>(); //dif iteration 
        for (int i = 0; i < path.size(); i++) { //try starting from 1?
            State element = path.get(i);
            
            if(!element.isBlocked()){
                unblockedPath.add(element); 
                if(element.isUnblocked()){
                    maze.markTraversed(path.get(i)); 
                }
            }else{
                return unblockedPath; 
            }
            
        
        }
        return unblockedPath; 
    }

    public LinkedList<State> findPath(){
        this.open = new StatePriorityQueue(); 
        this.closed = new ArrayList<State>(); 

        if(open.isEmpty()){
            //System.out.println("Is empty 1."); 
        }

        open.add(this.maze.startState); 
        //maze.visible[0][0] = true; 


        if(open.isEmpty()){
            //System.out.println("Is empty 2."); 
        }

        while(open.getQueue().size() > 0){

            //System.out.println("Made it in first while loop."); 

            State state = open.poll(); //check whether you want the beginning or the end (0 or last index)

            if(state.sameState(maze.goalState)){ //used to be state.equals(maze.goalState)
                return pathMaker(state); 
            }

            closed.add(state); 

            //System.out.println("Made it here 1."); 

            ArrayList<State> states = state.validMoves(); 

            //System.out.println("Made it here 2.");

            for(int i= 0; i<states.size(); i++){ //states is empty here, debug this 

                //System.out.println("Made it here 2.5.");

                State childState = states.get(i); 

                //System.out.println("Made it here 3.");

                if(checkIfClosed(childState)){ //might not be working 
                    continue; 
                }

                State oldState = findStateInHeap(childState); 
                //System.out.println("Coord: " + oldState.row + oldState.col);
                //System.out.println("Made it here 4.2.");
                if(oldState != null){//if already in open list 
                    //System.out.println("Made it here 4.5."); 
                    if(oldState.getG() > childState.getG()){ //if this visit is cheaper 
                        //System.out.println("Made it here 4."); 
                        open.remove(oldState); //should remove oldState //this is problem 
                        open.add(childState); 
                    }
                } else {
                    //System.out.println("Made it here 5.");
                    open.add(childState);
                }

            }
            

        }

        return null;

    }


}
