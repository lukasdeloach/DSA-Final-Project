public interface AscendinglyOrderedListInterface<T,KT>
{   boolean isEmpty();
    int size();
    void add(T item) throws ListIndexOutOfBoundsException;
    T get(int index) throws ListIndexOutOfBoundsException;
    void remove(int index) throws ListIndexOutOfBoundsException;
    int search(KT key);
    void clear();
}
