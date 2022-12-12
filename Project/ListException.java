/**
 * ListException Class that will be implemented by List ADTS
 * Extends Java RuntimeException class
 */
public class ListException extends RuntimeException
{
    /**
     * Constructor for error. Takes in String that is used to describe the error. Calls super class constructor.
     * @param s -- String type used to descrive error.
     */
    public ListException(String s)
    {
        super(s);
    }  // end constructor
}  // end ListException
