/**
 * Array-based implementation of the ADT list, implments ListInterface.
 * Holds a generic variable of any type.
 * Has a data field of type int[] items to hold all items in list, and int numItems to track the number of items in the list.
 */
public class ListArrayBased<T> implements ListInterface<T>
{

    //private static final int MAX_LIST = 3; Commeneted out to fix programming style issues
    protected T []items;  // an array of list items
    protected int numItems;  // number of items in list

    /**
     * Constructor for list. Takes in no parameters and sets the array list to an empty list of size 3, and sets numItems data field to 0
     */
    public ListArrayBased()
    {
        items =(T[]) new Object[3];
        numItems = 0;
    }  // end default constructor

    /**
     * Method to get if list is empty. Checks if numItems data field is equal to 0.
     * Returns true if empty, false if not.
     * @return boolean of if numItems is equal to 0.
     */
    public boolean isEmpty()
    {
        return (numItems == 0);
    } // end isEmpty

    /**
     * Getter method for size of list.
     * @return numItems -- int type that tracks amount of items in list.
     */
    public int size()
    {
        return numItems;
    }  // end size

    /**
     * Method that clears all values from list. Resets items array back to an empty list of size 3, and resets numItems to 0.
     */
    public void removeAll()
    {
        // Creates a new array; marks old array for
        // garbage collection.
        items = (T[]) new Object[3];
        numItems = 0;
    } // end removeAll

    /**
     * Method to add an item to the list. Takes in item to add and what index to add it to. If list is full, it will throw and error. Else, it will add to the correct index, and shift all values to the right of the desgnated index to avoid overwritting current values in the list. If adds, updates numItems size tracker by one.
     * @param index -- int type of where to add item in list
     * @param item -- Generic type of what to be added to list 
     */
    public void add(int index, T item)
    throws  ListIndexOutOfBoundsException
    {
        if (numItems == items.length) //Fixes Implementation issue
        {
            throw new ListException("ListException on add");
        }  // end if
        if (index >= 0 && index <= numItems)
        {
            // make room for new element by shifting all items at
            // positions >= index toward the end of the
            // list (no shift if index == numItems+1)
            for (int pos = numItems-1; pos >= index; pos--)  //textbook code modified to eliminate logic error causing ArrayIndexOutOfBoundsException
            {
                items[pos+1] = items[pos];
            } // end for
            // insert new item
            items[index] = item;
            numItems++;
        }
        else
        {
            // index out of range
            throw new ListIndexOutOfBoundsException(
                "ListIndexOutOfBoundsException on add");
        }  // end if
    } //end add

    /**
     * Method to get the genreic item at a given index of the list from the parameter. Confirms the index provided to retrieve from is valid. 
     * If valid, find item at index, else, throw exception.
     * @param index -- int type to describe index from where to retrieve in list.
     * @return item -- Generic Type of desired item being stored in list.
     */
    public T get(int index)
    throws ListIndexOutOfBoundsException
    {
        if (index >= 0 && index < numItems)
        {
            return  items[index];
        }
        else
        {
            // index out of range
            throw new ListIndexOutOfBoundsException(
                "ListIndexOutOfBoundsException on get");
        }  // end if
    } // end get

    /**
     * Method to remove and item from the list based on given index from parameter input.
     * Validates index, and if a valid index for array, delete value by shifting and overwriting the desired deleted value at given index input as parameter. Reduces counter for numItems which tracks amount of items in list. If index is invalid, throws ListException. 
     * @param index -- int type that is used to specify what index to delete from.
     */
    public void remove(int index)
    throws ListIndexOutOfBoundsException
    {
        if (index >= 0 && index < numItems)
        {
            // delete item by shifting all items at
            // positions > index toward the beginning of the list
            // (no shift if index == size)
            for (int pos = index+1; pos < numItems; pos++) //textbook code modified to eliminate logic error causing ArrayIndexOutOfBoundsExceptio
            {
                items[pos-1] = items[pos];
            }  // end for
            numItems--;
            items[numItems] = null;
        }
        else
        {
            // index out of range
            throw new ListIndexOutOfBoundsException(
                "ListIndexOutOfBoundsException on remove");
        }  // end if
    } //end remove
}
