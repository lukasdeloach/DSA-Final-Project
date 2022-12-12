/**
 * This class is ListArrayBasedPlusN, extends ListArrayBased. It creates and implementaion for the List ADT.
 * List Array based uses an array based implementation, and this extends and overrides some methods to optimize its efficency.
 * @author: Lukas DeLoach and Jessica Rodgers
 * @version: 2022.20.09
 */
public class ListArrayBasedPlusN<T> extends ListArrayBased<T> {

    /**
     * New improved add method that does only 1 check.
     * Checks if it has to resize and if it does, copies elements up to the specified index
     * Then drops the element at the correct index and then copies the rest of the old array over.
     * @param index
     * @param item
     */
	@Override
    public void add(int index, T item) {
        if(numItems==items.length) {
            T[] tmp = (T[]) new Object[1<<items.length];
            for(int i = 0; i < index; i++) {
                tmp[i] = items[i];
            }
            tmp[index] = item; //Dropping item in
            for(int i = index; i < numItems; i++) {
                tmp[i+1]=items[i];
            }
            numItems++;
            items = tmp;
        }
        else {
            super.add(index, item);
        }
    }

    /**
     * Method to reverse the list.
     * First holds tmp Object
     * then iterates through half of the list.
     * Does some swaps and then assigns items with correct values.
     */
    public void reverse() {
        T tmp;
        for(int i = 0; i < numItems/2; i++) {
            tmp = items[i];
            items[i] = items[numItems-i-1];
            items[numItems-i-1]=tmp;
        }
    }

    /**
     * Method toString
     * Returns a String builder of appended items in the list
     * @return s.toString()
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < numItems; i++) {
            s.append(items[i] + " ");
        }
        return s.toString();
    }
}
