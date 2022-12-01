/*
 * Purpose: Honors Data Structure and Algorithms Lab 8 Problem 4
 * Status: Complete and thoroughly tested
 * Last update: 11/08/22
 * Submitted:  11/08/22
 * Comment: test suite and sample run attached
 * Comment: I declare that this is entirely my own work
 * @author: Lukas DeLoach
 * @version: 2022.08.11
 */
public class AscendinglyOrderedList<T extends KeyedItem<KT>, KT extends Comparable <? super KT>> extends ListArrayBasedPlusN implements AscendinglyOrderedListInterface<T, KT> {

    /**
     * This is the add method which adds an item to an ordered list.
     * Before an item is added, the search method is called and its return value is stored in the variable result.
     * The result variable will hold either a positive or negative value
     * Positive - means that the search method did not find the item and returned the correct index at which it should be inserted
     * Negative - means the search method found the item, and therefore it should not be added
     * @param key - a generic item be added
     * */
    public void add(T key) throws ListIndexOutOfBoundsException {
        if(numItems==0) {
            items[0] =  key;
            numItems++;
        }
        else {
            int result = search(key.getKey());
            if(result < 0) {
                result+=numItems; //decodes;
            }
            else {
                add(result, key);
            }

        }
    }

    /**
     * This is the search method for the AscendinglyOrderedList
     * It uses an eagerly advancing binary search method variation
     * It encodes any found values into a negative number which is index - size() where size() is a public method for the AscendignlyOrderedList Class.
     * If a negative number is returned, to find the index any implementing methods will add back size() to get the correct index
     * If an item is not found, it will return the index where it should be added in a positive value.
     * The add method will be implemented to add an item at all non-negative values returned by search
     * The add method will be implemented to NOT add an item at all negative values returned by search
     * @param key  - Type KT object
     * @return low - If an item is found, a negative encoded value is returned - the encoded value is the index where the item is found-size(). If a negative value is returned, decode the value by adding size() - this addition will result in the correct posisitve index for which the element is located. Otherwise a, positive vlaue is returned that represents correct index to insert.
     */
    public int search(KT key) {
        int low = 0;
        int high = size()-1;
        int mid = 0;
        while(low < high) {
            mid = (high+low)>>1;
            if(key.compareTo((KT)get(mid).getKey()) > 0) {
                low = mid +1;
            }
            else {
                high = mid;
            }
        }
        int result = (key).compareTo((KT) get(low).getKey());
        if(result==0) {
            low-=numItems; //encodes the value
            return low;
        }
        else {
            return (result>0) ? low+1 : low;
        }
    }

    /**
     * Method to clear all elements in the array.
     * Calls upon parent class removAll method.
     * */
    public void clear() {
        removeAll();
    }

    /**
     * This is a method to return an item at a provided index.
     * @param index the index of the desired item
     * @return items[index]	The element in the collection at position index
     * */
    public T get(int index)
    throws ListIndexOutOfBoundsException
    {
        if (index >= 0 && index < numItems)
        {
            return (T) items[index];
        }
        else
        {
            // index out of range
            throw new ListIndexOutOfBoundsException(
                "ListIndexOutOfBoundsException on get");
        }  // end if
    } // end get
}
