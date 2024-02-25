import java.util.ArrayList;

public class BinaryHeap {
    private Coordinate elements[];
    public int currentSize;

    private int MAX_SIZE = 101;

    public BinaryHeap(){
        elements = new Coordinate[MAX_SIZE];
        currentSize = 0;
    }

    public void addElement(Coordinate c){
        if(currentSize == MAX_SIZE){
            return;
        }
        elements[currentSize] = c;
        currentSize++;

        int currentIndex = currentSize - 1;
        while(currentIndex != 0 &&
                elements[getParentIndex(currentIndex)].fValue >
                        elements[currentIndex].fValue){
                switchElements(currentIndex, getParentIndex(currentIndex));
                currentIndex = getParentIndex(currentIndex);
        }
    }

    public void deleteMin(){
        if(currentSize == 0){
            return;
        }
        elements[0] = elements[currentSize - 1];
        minHeapify(0);
        currentSize--;
    }

    public void minHeapify(int index){
        if(elements[index] == null){
            return;
        }
        int leftIndex = getLeftIndex(index);
        int rightIndex = getRightIndex(index);

        int minValueIndex = index;

        if(leftIndex < currentSize &&
                elements[leftIndex].fValue < elements[minValueIndex].fValue){
            minValueIndex = leftIndex;
        }

        if(rightIndex < currentSize &&
                elements[rightIndex].fValue < elements[minValueIndex].fValue){
            minValueIndex = rightIndex;
        }

        if(minValueIndex != index){
            switchElements(minValueIndex, index);
            minHeapify(minValueIndex);
        }
    }

    public Coordinate getMin(){
        return elements[0];
    }

    private void switchElements(int a, int b){
        Coordinate temp = elements[b];
        elements[b] = elements[a];
        elements[a] = temp;
    }

    private int getLeftIndex(int index){
        return (2 * index) + 1;
    }

    private int getRightIndex(int index){
        return (2 * index) + 2;
    }

    private int getParentIndex(int index){
        return (index - 1) / 2;
    }

    public ArrayList<Coordinate> getArrayList(){
        ArrayList<Coordinate> c = new ArrayList<>();
        for(int i = 0; i < elements.length; i++){
            if(elements[i] != null){
                c.add(elements[i]);
            }
        }
        return c;
    }

    public void clear(){
        for(int i = 0; i < elements.length; i++){
            elements[i] = null;
        }
        currentSize = 0;
    }
}
