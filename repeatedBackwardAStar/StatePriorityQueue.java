import java.util.ArrayList;
import java.util.List;

public class StatePriorityQueue {

    private List<State> queue;

    public StatePriorityQueue() {
        queue = new ArrayList<State>();
    }

    public List<State> getQueue() {
        return queue; 
    }

    public void add(State state) {
        queue.add(state);
        bubbleUp(queue.size() - 1);
    }

    public State poll() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }

        State minState = queue.get(0);
        int lastIndex = queue.size() - 1;
        State lastState = queue.remove(lastIndex);

        if (lastIndex > 0) {
            queue.set(0, lastState);
            bubbleDown(0);
        }

        return minState;
    }

    public boolean remove(State state) {
        int index = queue.indexOf(state);
        if (index == -1) {
            System.out.print("Element not found."); 
            return false; // Element not found
        }

        int lastIndex = queue.size() - 1;
        if (index == lastIndex) {
            queue.remove(lastIndex);
            System.out.print("Element 'removed'."); 
        } else {
            State lastState = queue.remove(lastIndex);
            queue.set(index, lastState);
            update(index);
        }
        return true;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (compare(queue.get(index), queue.get(parentIndex)) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void bubbleDown(int index) {
        int size = queue.size();
        while (index < size) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            int smallest = index;

            if (leftChildIndex < size && compare(queue.get(leftChildIndex), queue.get(smallest)) < 0) {
                smallest = leftChildIndex;
            }
            if (rightChildIndex < size && compare(queue.get(rightChildIndex), queue.get(smallest)) < 0) {
                smallest = rightChildIndex;
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void update(int index) {
        int parentIndex = (index - 1) / 2;
        if (index > 0 && compare(queue.get(index), queue.get(parentIndex)) < 0) {
            bubbleUp(index);
        } else {
            bubbleDown(index);
        }
    }

    private void swap(int i, int j) {
        State temp = queue.get(i);
        queue.set(i, queue.get(j));
        queue.set(j, temp);
    }

    private int compare(State s1, State s2) {
        int fDif = s1.calcF() - s2.calcF(); 

        if (fDif == 0){ //break the tie with the lower g value 
            int gDif = s2.getG() - s1.getG(); 
            if (gDif != 0) {
                return gDif; 
            }else{ //if g also a tie, break with manhattanDist 
                return s1.manhattanDist() - s2.manhattanDist(); 
            }
        } else { //not a tie 
            return fDif; 

        }
    }
}