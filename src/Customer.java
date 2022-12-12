/**
 *
 * should we perform checkout checks for add and remove -- completed
 * 	Should we for add/sim time?
 *
 * This class is the Customer class that extends the KeyedItem class, with the Keyed Item being a String describing the name.
 * It describes the customer to be used in the Shopping Center model.
 * It holds values for names, amount of items in cart, time in store, and a data field to determine if in checkout.
 * Appropriate getters, setters, and toString was created to properly access all values.
 * One Construcor is implemented that takes in one parameter of type String for the customer name. 
  * @author Lukas DeLoach and Jessica Rodgers
 */
public class Customer extends KeyedItem<String> {

    private String name;
    private int totalTime;
    private int itemAmount;
    private boolean checkOut;
 
    /**
     * The only Constructor for the Customer class which takes one parameter that represent the Customer name. 
     * The constructor calls upon the KeyedItem classes constructor which instantiates the key as the Strng type name.
     * After, it sets totalTime and itemAmount to 0, it sets checkOut boolean to false.
     * @param name - String type that represents the Customer's name
     **/
    public Customer(String name) {

        super(name);
        if(!name.isEmpty()) {
            this.name = name;
        }
        itemAmount = totalTime = 0;
        checkOut = false;
    }

    /**
     * Method to add an item to the customers cart. No need to track what item, so only update amount of items counter.
     * Performs addition of one value if not in a checkout lane. If in a checkout line, nothing occurs.
     * */
    public void addItem() {
        if(checkOut == false){
	    itemAmount++;
	}
    }
    
    /**
     * Method to update time customer spends in store. This only updates by one to showcase one minute each time it is called.
     */
    public void updateTime() {
        totalTime++;
    }

    /**
     * Getter method to return name of customer. 
     * @return name -- String type for name of customer.
     */ 
    public String getName() {
        return name;
    }

    /**
     * Setter method to reasssign name data field of customer. 
     * @param name -- String variable to be update data field name for customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method to return value of total time spent in store for customer.
     * @return totalTime - int type that describes total time in minutes spent in store by the customer.
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * Setter method to reassign totalTime data field which describes time in store to paramter value.
     * @param totalTime -- int type to be assigned to totalTime data field.
     */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * Getter mehtod to return total amount of items in customers cart
     * @return itemAmount -- int type data field describing amount of items in cart
     */
    public int getItemAmount() {
        return itemAmount;
    }

    /**
     * Method to remove one item from customer cart. 
     * When method is called, confimration the customer is not in a checkout line is done, and if not in checkout, itemAmount data field is decremented. If not, nothing occurs
     */
    public void removeItem() {
	    if(checkOut == false){
        	itemAmount--;
	    }
    }

    /**
     * Setter method to set checkout line status of customer. checkOut data field describes if a customer is in a checkout line.
     * The method sets to whatever the value of the parameter is passed in.
     * @param status - boolean type describing new status of customer, if in a checkout line, or just in store.
     */
    public void setCheckOut(boolean status) {
        checkOut = status;
    }

    /**
     * Getter method to get Checkout status of customer. 
     * @return checkOut -- boolean type checkOut data field describing if the customer is in a checkout line or just the store.
     */
    public boolean getCheckOut() {
        return checkOut;
    }

    /**
     * toString method to describe the customer. Puts customer name, itemAmount, totalTime, and checkOut status in a string delimited by spaces.
     * @return String type describing the customer
     */
    @Override
    public String toString() {
        String retStr = name + " " + itemAmount + " " + totalTime + " " + checkOut;
        return retStr;
    }
}
