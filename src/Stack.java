/*
 * Purpose: Honors Data Structure and Algorithms Midterm
 * Status: Complete and thoroughly tested
 * Last update: 10/04/22
 * Submitted:  10/04/22
 * Purpose: Honors Data Structure and Algorithms Midterm
 * Status: Complete and thoroughly tested
 * Last update: 10/04/22
 * Submitted:  10/04/22
 * Comment: test suite and sample run attached
 * Comment: I declare that this is entirely my own work
 * @author: Lukas DeLoach
 * @version: 2022.04.10
 */
public class Stack<T> implements StackInterface<T> {

    private Node<T> top;

    public Stack() {
        top = null;
    }

    @Override
    /**
     * Checks to see if the stack is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    /**
     * Removes all elements
     */
    public void popAll() {
        top = null;
    }

    @Override
    /**
     * Pushes a new item onto the end
     */
    public void push(T newItem) {
        top = new Node(newItem, top);
    }

    @Override
    /**
     * Returns the top
     * @return T
     */
    public T peek() {
        T result;
        result = top.getItem();
        return result;
    }

    @Override
    /**
     * Removes the item from the end
     * @return T
     */
    public T pop() {
        T result = top.getItem();
        top = top.getNext();
        return result;
    }

    @Override
    /**
     * Iterates through the indexes and appends the items
     * @return string
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node temp = top;
        while(temp!=null) {
            str.append(temp.getItem() + " ");
            temp = temp.getNext();
        }
        return str.toString();
    }
}


