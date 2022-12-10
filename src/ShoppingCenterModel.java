/*
 *
 * NEED TO ADDRESS HANDLING OF AMOUNT OF SHOPPERS ONLY
 * Curently subtracting, use possible tracker
 */

import java.lang.*;

public class ShoppingCenterModel {

    private CheckOut<Customer> lineOne;
    private CheckOut<Customer> lineTwo;
    private CheckOut<Customer> expressLine;


    private AscendinglyOrderedList<Customer, String> customers;
    private AscendinglyOrderedList<Item, String> items;

    private int restockingLevel;
    // check out start will be 0-2, 0 being express, 1 being line 1, 2 being line 2
    private int checkOutStart;

    public ShoppingCenterModel() {
        lineOne = new CheckOut<>("first");
        lineTwo = new CheckOut<>("second");
        expressLine = new CheckOut<>("express");

        customers = new AscendinglyOrderedList<>();
        items = new AscendinglyOrderedList<>();

        restockingLevel = 0;
        checkOutStart = 0;
    }


    // temp until we create getItem and getCustomer
    public AscendinglyOrderedList getItems() {
        return items;
    }

    public AscendinglyOrderedList getCustomers() {
        return customers;
    }

    public boolean addItem(Item item){
	    int size = items.size();
	    items.add(item);
	    return (size==items.size()) ? false : true;
    }


    /**
     * Method to add a method.
     * Calls internal customer search method for customer validation.
     * If the search method's returned value indicates that the customer is already in the store, will not add the customer.
     * Otherwise, the customer will be added to the list of customers in ascendingly ordered fashion.
     * @param name - Customer's name to be added in a String format
     * @return status - boolean value that indicates whether or not a customer has been added. True means that the customer has been added and false means that the customer has not been added.
     */
    public boolean addCustomer(String name) {
        boolean status = false;
        if(customers.size() == 0) {
            customers.add(new Customer(name));
            status = true;
        }
        else {
            int search = customerSearch(name);
            if(search==-1) {
                customers.add(new Customer(name));
                status = true;
            }
        }
	return status;
    }

    /**
     * customerAddItem method
     * Adds am item to a customers cart
     * Decrements the item's stock
     * Updated the time for everyone in the shopping center
     * @param customer - a Customer object
     * @param item     - an Item object
     */
    public void customerAddItem(Customer customer, Item item) {
        customer.addItem(item);
        item.decrementAmount();
        simulateTime();
    }

    public void customerRemoveItem(Customer customer) {
        customer.removeItem();
        simulateTime();
    }

    private void simulateTime() {
        for(int i = 0;  i < customers.size(); i++) {
            customers.get(i).updateTime();
        }
    }


    // find out who is checking out next and return their name
    public String nextCheckOut() {
        checkOutStart = (checkOutStart + 1) % 3;
        String nextCust = null;
        switch(checkOutStart) {
        case 0:
            nextCust = (expressLine.peek()).getName();
            break;
        case 1:
            nextCust = (lineOne.peek()).getName();
            break;
        case 2:
            nextCust = (lineTwo.peek()).getName();
            break;
        }
        return nextCust;
    }


    // check out person based on which checkout lane is next
    // take in param of if they leave or return shopping
    // if leave, remove from list of all customers aswell
    // returns int of how many items they have. If -1, they are leaving and can be ignored,
    // if they go back to shopping, returns the amount of items they have
    public int checkOut(boolean leave) {
        int items = -1;
        checkOutStart = (checkOutStart + 1) % 3;
        Customer cust = null;
        switch(checkOutStart) {
        case 0:
            cust = expressLine.dequeue();
            break;
        case 1:
            cust = lineOne.dequeue();
            break;
        case 2:
            cust = lineTwo.dequeue();
            break;
        }

        if(leave) {
            int index = customers.search(cust.getName());
            index = index + customers.size();
            customers.remove(index);
        } else {
            items = cust.getItemAmount();
        }
        return items;
    }

    public int itemSearch(String name) {
	int result = -2;
	if(!items.isEmpty()){
        	result = (items.search(name));
        	if(result > -1) {
            	result = -1;
        	}
	}
        return result;
    }

    // method to validate customer status, if they return -1, the customer does not exist
    // if 0, customer is in checkout, if 1, customer is still shopping
    public int customerSearch(String name) {
        int result = 0;
        int index = customers.search(name);
        if(index > -1) {
            result = -1;
        }
        else {
            int trueIndex = index + customers.size();
            Customer cust = customers.get(trueIndex);
            if(cust.getCheckOut()) {
                result = -2;
            } else {
                result = trueIndex;
            }
        }
        return result;
    }

    public String displayLine1() {
        return lineOne.toString();
    }

    public String displayLine2() {
        return lineTwo.toString();
    }

    public String displayExpress() {
        return expressLine.toString();
    }

    // Possible fix 1 vs multiple
    public String displayShoppers() {
        StringBuilder retStr = new StringBuilder();
        //TEMPORARY SOLUTION FOR HOW MANY CUSTOMERS ARE SHOPPING, NOT JUST ALL CUSTOMERS
        //CURRENTLY SUBTRACTION, POSSIBLE COUNTER
        int numCust = customers.size();
        int shoppers = numCust - lineOne.size() - lineTwo.size() - expressLine.size();
        if(shoppers == 0) {
            retStr.append("No customers are in the Shopping Center!");
        } else {
            retStr.append("The following " + shoppers + " customers are in the Shopping Center:\r");
            for(int i = 0; i < numCust; i++) {
                Customer cust = customers.get(i);
                if(!cust.getCheckOut()) {
                    retStr.append("Customer " +cust.getName() +" with " + cust.getItemAmount() +
                                  " items present for " + cust.getTotalTime() + " minutes.\n");
                }
            }
        }

        return retStr.toString();
    }
	
    /**
     * Method to display the restocking level
     * Iterates through the AscendignlyOrdered List of Items and checks if the item's stock at that index is equal to or lower than the stocking level
     * @return String - a String type that holds all of the items that are equal or below the stocking level in the format of: "item name" with "item amount" items.
     */
    public String displayRestockingLevelItems(){
	    StringBuilder str = new StringBuilder();
	    int size = items.size();
	    for(int i = 0; i < size; i++){
		    Item item = items.get(i);
		    if(item.getAmount() < restockingLevel+1){
			    str.append(item.getName() + " with " + item.getAmount() + " items.\n");
		    }
	    }
	    return str.toString();
    }

    /**
     * The orderItem method updates a given item current stock amount
     * This method calls upon the AscendinglyOrderedList's method called search.
     * The search method searches for a provided key in the items collection and returns an encoded value describing whether an item name is found or not.
     * The AscendinglyOrderedList Class further describes the search method and its encoded value. Simply put, if the item is found in the collection, a negative value is returned.
     * To decode a negative value, the AscendinglyOrderedList's size() method is added to the negative value. The decoded value is the index of the item in the collection.
     * If a positive index is returned, the item is not found. The AscendinglyOrderedList describes the meaning for a positively returned value
     * @param name - String type varaible which will hold the item's name of which the user wants to search for.
     * @param amount - primitive type int that holds the amount of new stock the user wants to add to the existing item stock.
     * @return item - Object type Item. If the item is not found based on the given name, the method will return a null item
     */
    public Item orderItem(String name, int amount){
	    Item item = null;
	    int index = items.search(name);
	    if(index < 0){
		    item = items.get(index+items.size()); // decodes value found
		    int updatedValue = item.getAmount()+amount;
		    item.setAmount(updatedValue);
	    }
	    return item;
    }

    /**
     * Method to get the restocking level
     * @return restockingLevel - the ShoppingCenterModel's private type int variable that stores the restocking level inputted by the user
     */
    public int getRestockingLevel(){
	    return restockingLevel;
    }

    /**
     * Mutator method to set the restocking level to the amount passed as a parameter
     * @param restockingLevel - int type that holds the new stocking level value to be stored
     */
    public void setRestockingLevel(int restockingLevel){
	    this.restockingLevel = restockingLevel;
    }



}
