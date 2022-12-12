/**
 * Class to describe when a List index given has been found to be out of bounds and invalid. This is to be used in the List ADT.
 * Extends IndexOutOfBoundsException
 */

public class ListIndexOutOfBoundsException
    extends IndexOutOfBoundsException
{
    /**
     * Constructor for error that takes in the string that describes the error being thrown. Calls super class error constructor.
     * @param s -- String type variable passed to super constructor used to describe the error.
     */
    public ListIndexOutOfBoundsException(String s)
    {
        super(s);
    }  // end constructor
}  // end ListIndexOutOfBoundsException
