import java.io.*;
import java.util.ArrayList; 

public class binaryHeap {

    private ArrayList<State> heap;
    //public int currentSize;

    public ArrayList<State> getHeap(){
        return heap; 
    }

    public binaryHeap(){
        heap = new ArrayList<State>(); 
        //currentSize = 0; 
    }

    public void addState(State s){
        heap.add(s); 
        heapifyUpward(heap.size() - 1); 
    }

    public void heapifyUpward(int index){
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).decision(heap.get(parentIndex)) >= 0) {
                break;
            }
            switchElements(index, parentIndex);
            index = parentIndex;
        }
    }

    public void heapifyDownward(int index){

        int size = heap.size();
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap.get(leftChild).decision(heap.get(smallest)) < 0) {
                smallest = leftChild;
            }
            if (rightChild < size && heap.get(rightChild).decision(heap.get(smallest)) < 0) {
                smallest = rightChild;
            }
            if (smallest == index) {
                break;
            }
            switchElements(index, smallest);
            index = smallest;
        }

    }

    public void switchElements(int index1, int index2){

        State temp= heap.get(index1); 
        heap.set(index1, heap.get(index2)); 
        heap.set(index2, temp); 

    }

    public State popMinElement(){

        if (heap.isEmpty()){
            return null;
        }

        State min = heap.get(0); 
        heap.set(0, heap.get(heap.size()-1)); 
        heap.remove(heap.size()-1); 
        heapifyDownward(0);
        return min; 

    }

    //Old methods, might still use tho: 

    private int getLeftIndex(int index){
        return (2 * index) + 1;
    }

    private int getRightIndex(int index){
        return (2 * index) + 2;
    }

    private int getParentIndex(int index){
        return (index - 1) / 2;
    }
} 
