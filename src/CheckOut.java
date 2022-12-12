/**
 * This is the CheckOut class that extends class Queue.
 * This class has one additional variable of name variable used to describe the checkout lane it represents. 
 * Name is of String type and is used to describe the Checkout based on input.
 * Most queue methods are the same, with the constructor being updated and a size method to
 * return size. Along with a getter and setter for name, and a proper toString to describe
 * the checkout.
 * @author: Lukas DeLoach and Jessica Rodgers
 * @version: 2022.12.12
 **/


import java.lang.*;

public class CheckOut<T> extends Queue<T> {

    String name;
    
    /**
     * The only Constructor for the Checkout class, which takes one parameter that represent the Checkout name. The constructor calls upon the Queue class constructor, and then sets the name variable with the parameter input.
     * @param name - String type that represents the Checkout's name
     */
    public CheckOut(String name) {
        super();
        this.name = name;
    }

    /**
     * Getter method for the data field name.
     * @return name -- String type that represents the Checkout's name.
     * */
    public String getName(){
	    return name;
    }

    /**
     * Setter method for data field name.
     * @param newName -- String type to represent new name value.
     * */ 
    public void setName(String newName){
	    name = newName;
    }

    /**
     * Getter method for size of Checkout, which is the number of customers in the Checkout Queue.
     * @return numItems -- int datafield of Queue size of Checkout.
     * */
    public int size() {
        return numItems;
    }


    /**
     * toString method to properly describe Checkout status. 
     * If the Checkout is empty, it will return a statement desciribing there are no customers in the line and use the checkout name data field.
     * If not empty, it will iterate through the checkout and compile all information about the customers in the checkout.
     * */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        // set curr to front to iterate through items
	int normIndex = 0;
        for(int ind = 0; ind < numItems; ind++) {
		// saved for readability
                normIndex = (front+ind)%items.length;
                Customer cust = (Customer) items[normIndex];
                str.append("\n\tCustomer " + cust.getName() + " has " + cust.getItemAmount() +
                           " items in the shopping basket.");
	}
        return str.toString();
    }
}


