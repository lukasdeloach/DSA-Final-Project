/*
 * Purpose: Honors Data Structure and Algorithms Lab 6 Problem 0
 * Status: Complete and thoroughly tested
 * Last update: 10/12/22
 * Submitted:  10/13/22
 * Comment: test suite and sample run attached
 * Comment: I declare that this is entirely my own work
 * @author: Jessica Rodgers
 * @version: 2022.10.13
 */


public class Queue<T> implements QueueInterface<T> {

    // protected data fields for DEQ
    protected T[] items;
    protected int front;
    protected int back;
    protected int numItems;

    public Queue() {
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }// end constructor

    public boolean isEmpty() {
        // Determines whether a queue is empty.
        // Precondition: None.
        // Postcondition: Returns true if the queue is empty;
        // otherwise returns false.
        // uses numItems to determine if any items are in queue
        return (numItems == 0);
    }// end isEmpty

    public void enqueue(T newItem) throws QueueException {
        // Adds an item at the back of a queue.
        // Precondition: newItem is the item to be inserted.
        // Postcondition: If the operation was successful, newItem
        // is at the back of the queue. Some implementations
        // may throw QueueException if newItem cannot be added
        // to the queue.
        // if full, uses internal resize
        if(items.length == numItems) {
            resize();
        }// end if to resize
        // then add to back
        items[back] = newItem;
        // reassign back to next index of array avail via normalization
        back = (back + 1)%items.length;
        // increment numItems
        numItems++;
    }// end enqueue

    public T dequeue() throws QueueException {
        // Retrieves and removes the front of a queue.
        // Precondition: None.
        // Postcondition: If the queue is not empty, the item that
        // was added to the queue earliest is removed. If the queue is
        // empty, the operation is impossible and QueueException is thrown.
        // declare result variable
        T result = null;
        // if not empty, get from front
        if(numItems != 0) {
            result = items[front];
            // reassign at variable to null, prevent mem leak
            items[front] = null;
            // reassign front via normalization
            front = (front + 1)%items.length;
            // decrement numItems
            numItems--;
            // if empty throw exception
        } else {
            throw new QueueException("Queue Empty!");
        }// end if
        return result;
    }// end dequeue

    public void dequeueAll() {
        // Removes all items of a queue.
        // Precondition: None.
        // Postcondition: The queue is empty.
        // set items equal to new array of original size
        // reset all variables
        items = (T[]) new Object[3];
        front = 0;
        back = 0;
        numItems = 0;
    }// end dequeueAll

    public T peek() throws QueueException {
        // Retrieves the item at the front of a queue.
        // Precondition: None.
        // Postcondition: If the queue is not empty, the item
        // that was added to the queue earliest is returned.
        // If the queue is empty, the operation is impossible
        // and QueueException is thrown.
        // declare reutrn variable
        T result = null;
        // if not empty
        if(numItems != 0) {
            // set result to return value at front of Queue
            result = items[front];
            // if empty, throw error
        } else {
            throw new QueueException("Queue Empty!");
        }// end if
        return result;
    }// end peek

    protected void resize() {
        // to resize items array to fit more values
        // create temp to be able to store double amount of curernt array via bit shift
        T[] temp = (T[]) new Object[(items.length)<<1];
        // use current index tracker to collect from front to back in new array
        for(int newInd = 0; newInd < numItems; newInd++) {
            temp[newInd] = items[(front + newInd)%items.length];
        }// end for
        front = 0;
        back = numItems;
        items = temp;
    }// end resize


    public String toString() {
        //declare string builder to collect items from items
        StringBuilder str = new StringBuilder();
        // set curr to front to iterate through items
        int curr = front;
        // for loop iterates numItems times, adding starting from front and adding index that increments amount normalized to size of array
        for(int ind = 0; ind < numItems; ind++) {
            // saved for readability
            int normIndex = (front+ind)%items.length;
            str.append((items[normIndex]).toString() + "\n");
        }
        return str.toString();
    }// end toString
}// end QueueCRAB





