/**
 * This is the ShoppingCenterModel class that houses all of the Logic of the Shopping Center.
 * @author Lukas DeLoach
 * @author Jessica Rodgers
 * @version 12/12/2022
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

    private Customer longestCustomer;

    /**
     * Constructor for ShoppingCenterModel. Takes not input, and sets the first few checkout lanes to be the proper title names.
     * Also initializes customers and items list to empty, and restocking level, checkoutstart to 0, and longestCustomer to null.
     */
    public ShoppingCenterModel() {
        lineOne = new CheckOut<>("first");
        lineTwo = new CheckOut<>("second");
        expressLine = new CheckOut<>("express");

        customers = new AscendinglyOrderedList<>();
        items = new AscendinglyOrderedList<>();

        restockingLevel = 0;
        checkOutStart = 0;
	longestCustomer = null;
    }

    /**
     * Method to add a customer.
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
            Customer cust = customerSearch(name);
            if(cust == null) {
                customers.add(new Customer(name));
                status = true;
            }
        }
        return status;
    }

    /**
     * Method to add an Item to the AscendignlyOrderedList of Items.
     * Method initially stores the size of the collection of items.
     * An item of the passed parameters is created by calling the constructor of the Item class.
     * The AscendinglyOrderedList of items then calls its add method to try and add the item to the collection.
     * If the item is not already found within the collection, the item will be added.
     * This is why the inital size of the collection is kept to determine if the collection has grown after the add method is called.
     * The method then checks the initial size to see if they are equal (equal meaning the item has not been added)
     * If the initial size is equal to the current size false is returned to describe that the item has NOT been added.
     * Otherwise, true is returned to describe that the item has been added.
     * @param name - String type to represent the name of the item
     * @param num - Amount of the item to be added of type int
     * @return boolean - A boolean value is returned to decsribe whether an Item has been added to the collection or not.
     */
    public boolean addItem(String name, int num) {
        int size = items.size();
        Item item = new Item(name, num);
        items.add(item);
        return (size==items.size()) ? false : true;
    }

    /**
     * customerAddItem method
     * Adds am item to a customers cart
     * Decrements the item's stock
     * Updates the time for everyone in the shopping center
     * @param customer - a Customer object
     * @param item     - an Item object
     */
    public void customerAddItem(Customer customer, Item item) {
        customer.addItem();
        item.decrementAmount();
        simulateTime();
    }

    /**
     * This is a method to remove an Item from the customer's shopping cart as well as update the time of everyone in the store.
     * This method will call upon the Customer's remove item method.
     * Since this action is supposed to simulate the passing of time, the simulateTime method is called which increments every customer in the shopping center's time by one.
     * @param customer - A Customer data type that will be passed
     */
    public void customerRemoveItem(Customer customer) {
        customer.removeItem();
        simulateTime();
    }

    /**
     * Method to simulate the time of the shopping center by iterating through the list of customers and incrementing their time by one.
     * Since every customer's time has to be updated, this algorithm works in O(n) time.
     * This method also searches for the customer who has been in the store the longest if that customer has not already been found.
     * If the longestCustomer is not null, the method will only iterate through the customers and update the time of each customer.
     * If the longestCustomer is null, throughout the iteration this method will also check for the customer who has been in the store the longest and update the longestCustomer with that found Customer.
     * This is important so that we do not have to iterate through the entire collection every time we wish to checkout a customer with the longest time.
     * The Customer with the longest time will not be stored if they are found to be in the checkout line.
     */
    private void simulateTime() {
        int size = customers.size();
        if(longestCustomer!=null) {
            for(int i = 0;  i < size; i++) {
                customers.get(i).updateTime();
            }
        }
        else {
            int max = Integer.MIN_VALUE, index = 0;
            for(int i = 0; i < size; i++) {
                Customer cust = customers.get(i);
                cust.updateTime();
                if(cust.getTotalTime() > max && !cust.getCheckOut()) {
                    max = cust.getTotalTime();
                    index = i;
                }
            }
            longestCustomer = customers.get(index);
        }
    }

     /**
     * Item search method which searches for an item given a key name.
     * Calls upon the AscendignlyOrderedList size method which returns an encoded negative value if the key was found or a positive value if the key was not found.
     * If the temp is a negative value after items.search() is called, then the Object with the name is found.
     * To decode temp to obtain the correct index of the item in the list, items.size() is added to temp.
     * Then the object is retrieved from the collection and returned.
     * If no item is found, null is returned.
     * @param name - String type that will be the key to search for
     * @return result - Item object which is either null (meaning no object with the given name was found) or containing the reference to the Item which was found.
     */
    public Item itemSearch(String name) {
        Item result =  null;
        int temp = 0;
        if(!items.isEmpty()) {
            temp = items.search(name);
            if(temp < 0) {
                result = items.get(temp+items.size());
            }
        }
        return result;
    }

    /**
     * This is the customerSearch method which searches for a customer name in the collection of Customers.
     *  This method calls upon the AscnedinglyOrderedList's search method which returns a negative encoded value if the key was found.
     *  The search method returns a positive value if the key is not found indicating where the item should be added.
     *  If the index value is less than zero, this means that they item was found. To decode it we add the size() of the collection to the negative value.
     *  This decoding will give the proper index the Customer is found in the collection.
     *  Then we get the customer and return the customer.
     *  If no customer is found we return null.
     *  @param name - The customer's name to be searched for passed as a String type.
     *  @return cust - A customer object which either returns the customer found by their name or null meaning that no customer with the name passed was found.
     */
    public Customer customerSearch(String name) {
        int result = 0;
        Customer cust = null;
        int index = customers.search(name);
        if(index < 0) {
            int trueIndex = index + customers.size();
            cust = customers.get(trueIndex);
        }
        return cust;
    }

    /**
     * Method to search for customer in the shopping center that is not in any of the checkout lines
     * @return Customer at the index found
     */
    private Customer searchMaxTime() {
        int max = Integer.MIN_VALUE;
        int index = 0;
        int size = customers.size();
        for(int i = 0; i < size; i++) {
            int time = customers.get(i).getTotalTime();
            if(time > max && !customers.get(i).getCheckOut()) {
                max = time;
                index = i;
            }
        }
        return customers.get(index);
    }

    /**
     * Method to enqueue a customer
     * If the customer has less than or equal to 4 items they should be added into the express line unless it is two times bigger than a regular line.
     * If it is two times bigger, the two regular lines are compared in order to see which line a customer should be enqueued into.
     * The customer will be enqued into the lesser of those two compared lines.
     * If the customer has more than 4 items, they are added to either line one or line two, depending on which one has less customers.
     * @return String  - String object which describes which line the customer was added into
     */
    public String enqueueCustomer() {
        int itemAmount = longestCustomer.getItemAmount();
        int expressSize = expressLine.size(), lineOneSize = lineOne.size(), lineTwoSize = lineTwo.size();
        StringBuilder str = new StringBuilder();
        if(itemAmount < 5) {
            if((expressSize > lineOneSize<<1) || (expressSize > lineTwoSize<<1)) {
                if(lineOneSize < lineTwoSize + 1) {
                    lineOne.enqueue(longestCustomer);
                    str.append("first checkout line");
                }
                else {
                    lineTwo.enqueue(longestCustomer);
                    str.append("second checkout line");
                }
            }
            else {
                expressLine.enqueue(longestCustomer);
                str.append("express line");
            }
        }
        else {
            if(lineOneSize < lineTwoSize + 1) {
                lineOne.enqueue(longestCustomer);
                str.append("first checkout line");
            }
            else {
                lineTwo.enqueue(longestCustomer);
                str.append("second checkout line");
            }
        }
        longestCustomer.setCheckOut(true);
        longestCustomer = null;
        return str.toString();
    }

    /**
     * Method to determine next lane to checkout from based on checkout counter.
     * The checkoutstart data field tracks what the next checkout lane to use is, and ciruclarly iterates it to keep track.
     * If it next checkout is empty, it will move to the next. If if goes through all 3 and there are no customers, it will return null.
     * @return nextCheckout -- CheckOut variable of the checkout to be used to dequeue from
     */
    private CheckOut<Customer> nextLane() {
        CheckOut<Customer> next = null;
        boolean customers = false;
        int counter = 0;
        int lane = checkOutStart;
        while(customers == false && counter < 3) {
            switch(lane) {
            case 0:
                next = expressLine;
                break;
            case 1:
                next = lineOne;
                break;
            case 2:
                next = lineTwo;
                break;
            }
            if(next.size() > 0) {
                customers = true;
            }
            lane = (lane + 1) % 3;
            counter++;
        }
        if(customers == false) {
            next = null;
        }
        return next;
    }

    /**
     * Method to get the name of the next Customer to checkout of the store based on the next checkout lane to be dequeue from.
     * @return customer -- String variable that holds name of next customer to checkout.
     */
    public String nextCheckOut() {
        String cust = null;
        CheckOut<Customer> next = nextLane();
        if(next != null) {
            cust = next.peek().getName();
        }
        return cust;
    }

    /**
     * Method to check out the next person in the next Checkout lane.
     * This method gets the correct next person to checkout based on who is next in the proper checkout line.
     * If the line to be taken is not null, as a validation check, it will dequeue, andeither fully remove them, or have them return to shopping and reset their time counter. It then increments the values of the next line to be checked out from.
     * @param leave -- boolean type that describes whether or not the peson should fully leave, true, or return to shopping, false.
     * @return customer -- Customer type, returns the person who just left the checkout line
     */
    public Customer checkOut(boolean leave) {
        CheckOut<Customer> line = this.nextLane();
        Customer cust = null;
       	if(line != null){
		cust = line.dequeue();
<<<<<<< HEAD
=======

>>>>>>> abfac2cb794931c3efed4f73dc0f9201de5a983b
        	if(leave) {
            		int index = customers.search(cust.getName());
            		index = index + customers.size();
            		customers.remove(index);
        	}else {
            		cust.setTotalTime(0);
            		cust.setCheckOut(false);
        	}
        	checkOutStart = (checkOutStart + 1)%3;
	}
        return cust;
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
    public int orderItem(Item item, int amount) {
        int updatedValue = -1;
        if(item != null) {
            updatedValue = item.getAmount()+amount;
            item.setAmount(updatedValue);
        }
        return updatedValue;
    }

     /**
     * This is a method to display the shoppers in the shopping center's list of customers.
     * If there aren't any customers in the shopping center, a message is appended stating so.
     * Otherwise, the method iteratively gets every customer in the AscnedinglyOrderedList of customers and appends a message with the customers name, item amount, and time.
     * @return String - a String object that represents all of the customers within the shopping center.
     */
    public String displayShoppers() {
        StringBuilder retStr = new StringBuilder();
        //TEMPORARY SOLUTION FOR HOW MANY CUSTOMERS ARE SHOPPING, NOT JUST ALL CUSTOMERS
        //CURRENTLY SUBTRACTION, POSSIBLE COUNTER
        int numCust = customers.size();
        int shoppers = numCust - lineOne.size() - lineTwo.size() - expressLine.size();
        if(shoppers == 0) {
            retStr.append("No customers are in the Shopping Center!");
        } else {
            retStr.append("The following " + shoppers + " customers are in the Shopping Center:");
            for(int i = 0; i < numCust; i++) {
                Customer cust = customers.get(i);
                if(!cust.getCheckOut()) {
                    retStr.append("\n\tCustomer " +cust.getName() +" with " + cust.getItemAmount() +
                                  " items present for " + cust.getTotalTime() + " minutes.");
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
    public String displayRestockingLevelItems() {
        StringBuilder str = new StringBuilder();
        int size = items.size();
        for(int i = 0; i < size; i++) {
            Item item = items.get(i);
            if(item.getAmount() < restockingLevel+1) {
                str.append(item.getName() + " with " + item.getAmount() + " items.\n");
            }
        }
        return str.toString();
    }

    /**
     * This method is used to move the person longest in the store into a checkout line.
     * This method is used to move a person who has no items, either back to shopping, or leave the store completely.
     * @param leave -- boolean variable describie whether a person with no items should leave the store(true), or return shopping(false)
     * @return result -- String type that describes action of customer that just occured through this method.
     */
    public String doneShopping(boolean leave){
            String result = null;
            String name = longestCustomer.getName();
            if(leave == true){
                customers.remove(customers.search(name)+customers.size());
                longestCustomer = null;
                result = "Customer " + name + "has left.";
            }else{
                longestCustomer.setTotalTime(0);
                longestCustomer.setCheckOut(false);
                result = "Customer " + name + " with " + longestCustomer.getItemAmount() + "items returned to shopping.";
                longestCustomer = null;
            }
            return result;
    }

    /**
     * This method is for a customer who has items, and enqueues them in the proper checkoutline.
     * @return result -- String type describing customer infomration and what checkout line they are in.
     */
    public String doneShopping(){
	String result = "After " + longestCustomer.getTotalTime() + " minutes in the Shopping Center, customer " + longestCustomer.getName() + " with " + longestCustomer.getItemAmount() + " is now in the ";
	result = result +  this.enqueueCustomer() +".";
	return result;	
    }

      /**
     * Method to display the contents of the first line
     * @return String - information of the contents in the first line.
     */
    public String displayLine1() {
            StringBuilder str = new StringBuilder();
            String name = lineOne.getName();
            if(lineOne.isEmpty()){
                    str.append("No customers are in the " + name + " checkout line!");
            }else{
                int size = lineOne.size();
                if(size == 1) {
                        str.append("The following customer is in the " + name  + " checkout line:");
                } else {
                        str.append("The following " + size + " customers are in the " + name + " checkout line:");
                }
                str.append(lineOne.toString());
            }
        return str.toString();
    }

    /**
     * Method to display the contents of the second line
     * @return String - information of the contents in the second line.
     */
    public String displayLine2() {
            StringBuilder str = new StringBuilder();
            String name = lineTwo.getName();
            if(lineTwo.isEmpty()){
                    str.append("\nNo customers are in the " + name + " checkout line!");
            }else{
                int size = lineTwo.size();
                if(size == 1) {
                        str.append("\nThe following customer is in the " + name  + " checkout line:");
                } else {
                        str.append("\nThe following " + size + " customers are in the " + name + " checkout line:");
                }
                str.append(lineTwo.toString());
            }
        return str.toString();

    }

    /**
     * Method to display the contents of the express line
     * @return String - information of the contents in the express line.
     */
    public String displayExpress() {
            StringBuilder str = new StringBuilder();
            String name = expressLine.getName();
            if(expressLine.isEmpty()){
                    str.append("\nNo customers are in the " + name + " checkout line!");
            }else{
                int size = expressLine.size();
                if(size == 1) {
                        str.append("\nThe following customer is in the " + name  + " checkout line:");
                } else {
                        str.append("\nThe following " + size + " customers are in the " + name + " checkout line:");
                }
                str.append(expressLine.toString());
            }
        return str.toString();

    }

    /**
     * Method to get the longest shopper within the shopping center.
     * If the longestCustomer data field is null, the method will call upon the searchMaxTime method to obtain the customer with the most time
     * @return Customer - A customer who has been in the store the longest and is not currently in any of the checkout lines.
     */
    public Customer getLongestShopper() {
        if(longestCustomer == null) {
            longestCustomer = searchMaxTime();
        }
        return longestCustomer;

    }


     /**
     * Method to get the restocking level
     * @return restockingLevel - the ShoppingCenterModel's private type int variable that stores the restocking level inputted by the user
     */
    public int getRestockingLevel() {
        return restockingLevel;
    }

    /**
     * Getter method to get the collection of items.
     * @return items - an AscendinglyOrderedList of type Items.
     */
    public AscendinglyOrderedList getItems() {
        return items;
    }

    /**
     * Getter method to get the collection of customers.
     * @return customers - an AscnedinglyList of Customers
     */
    public AscendinglyOrderedList getCustomers() {
        return customers;
    }

    /**
     * Is empty method that checks if the list of customers is empty
     * @return boolean - true if the list has a customer or customrs. False if the the list does not have any customers.
     */
    public boolean isEmpty() {
        return (customers.isEmpty());
    }

    /**
     * Mutator method to set the restocking level to the amount passed as a parameter
     * @param restockingLevel - int type that holds the new stocking level value to be stored
     */
    public void setRestockingLevel(int restockingLevel) {
        this.restockingLevel = restockingLevel;
    }
}
