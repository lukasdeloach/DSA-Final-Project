/**
 * Interface ListInterface for the ADT list. Sets up all methods to be used in all List ADT Implementations.
 */
public interface ListInterface<T>
{
    boolean isEmpty();
    int size();
    void add(int index, T item) throws ListIndexOutOfBoundsException;
    T get(int index) throws ListIndexOutOfBoundsException;
    void remove(int index) throws ListIndexOutOfBoundsException;
    void removeAll();
    String toString();
}  // end ListInterface
