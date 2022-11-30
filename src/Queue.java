
/*
 * Purpose: Honors Data Structure and Algorithms Midterm
 * Status: Complete and thoroughly tested
 * Last update: 10/18/22
 * Submitted:  10/18/22
 * Comment: test suite and sample run attached
 * Comment: I declare that this is entirely my own work
 * @author: Lukas DeLoach
 * @version: 2022.18.10
 */

public class Queue<T> implements QueueInterface<T> {

    private CNode<T> back;

    public Queue() {
        this.back = null;
    }

    @Override
    /**
     * Checks to see if Queue is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return back==null;
    }

    @Override
    /**
     * Adds item to back of the queue
     */
    public void enqueue(T newItem) throws QueueException {
        if(back==null) {
            back = new CNode(newItem);
        }
        else {
            CNode<T> temp = new CNode<T>(newItem, back.getNext());
            back.setNext(temp);
            back = temp;
        }
    }

    @Override
    /**
     * Removes item from front of the queue and returns it
     * @return T
     */
    public T dequeue() throws QueueException {
        if(!isEmpty()) {
            if(back!=back.getNext()) {
                T result = back.getNext().getItem();
                back.setNext(back.getNext().getNext());
                return result;
            }
            else {
                T result = back.getItem();
                back = null;
                return result;
            }
        }
        else {
            throw new QueueException("Queue is empty");
        }
    }

    @Override
    /**
     * Returns item from front of the queue
     * @return T
     */
    public T peek() throws QueueException {
        return back.getNext().getItem();
    }

    @Override
    /**
     * Removes all items from queue
     */
    public void dequeueAll() {
        this.back = null;

    }

    /**
     * Iterates through queue and appends items to String builder
     * @return String
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        if(!isEmpty()) {
            CNode<T> temp = back;
            while(temp.getNext() != back) {
                str.append(temp.getNext().getItem()+" ");
                temp = temp.getNext();
            }
            str.append(back.getItem());
        }
        return str.toString();
    }
}

