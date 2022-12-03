import java.lang.*;

public class CheckOut<T> extends Queue<T> {

    int size;
    String name;

    public CheckOut(String name) {
        super();
        this.name = name;
        size = 0;
    }

    @Override
    /**
    * Adds item to back of the queue and updates size
    */
    public void enqueue(T newItem) throws QueueException {
        super.enqueue(newItem);
        size++;
    }

    public T dequeue() throws QueueException {
        T ret =super.dequeue();
        size--;
        return ret;
    }

    public void dequeueAll() {
        super.dequeueAll();
        size = 0;
    }

    public int size() {
        return size;
    }

    public String toString() {
        //declare string builder to collect items from items
        StringBuilder str = new StringBuilder();
        // set curr to front to iterate through items
        int curr = front;
        if(size == 0) {
            str.append("No customers are in the " + name + " checkout line!\n");
        } else {
            // for loop iterates numItems times, adding starting from front and adding index that
            // increments amount normalized to size of array
            if(size == 1) {
                str.append("The following customer is in the " + name + " checkout line:");
            } else {
                str.append("The following " + size + " customers are in the " + name + " checkout line:");
            }
            for(int ind = 0; ind < numItems; ind++) {
                // saved for readability
                int normIndex = (front+ind)%items.length;
                str.append("\r" + (items[normIndex]).toString());
            }
            str.append("\n");
        }
        return str.toString();
    }
}


