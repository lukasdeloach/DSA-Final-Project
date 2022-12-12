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

    public ShoppingCenterModel() {
        lineOne = new CheckOut<>("first");
        lineTwo = new CheckOut<>("second");
        expressLine = new CheckOut<>("express");

        customers = new AscendinglyOrderedList<>();
        items = new AscendinglyOrderedList<>();

        restockingLevel = 0;
        checkOutStart = 0;
    }

    public boolean isEmpty() {
        return (customers.isEmpty());
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
     * @param customer - passed customer object
     * @return String  - String object which describes which line the customer was added into
     */
    public String enqueueCustomer(Customer customer) {
        int itemAmount = customer.getItemAmount();
        int expressSize = expressLine.size(), lineOneSize = lineOne.size(), lineTwoSize = lineTwo.size();
        StringBuilder str = new StringBuilder();
        if(itemAmount < 5) {
            if((expressSize > lineOneSize<<1) || (expressSize > lineTwoSize<<1)) {
                if(lineOneSize < lineTwoSize + 1) {
                    lineOne.enqueue(customer);
                    str.append("first checkout line");
                }
                else {
                    lineTwo.enqueue(customer);
                    str.append("second checkout line");
                }
            }
            else {
                expressLine.enqueue(customer);
                str.append("express line");
            }
        }
        else {
            if(lineOneSize < lineTwoSize + 1) {
                lineOne.enqueue(customer);
                str.append("first checkout line");
            }
            else {
                lineTwo.enqueue(customer);
                str.append("second checkout line");
            }
        }
        customer.setCheckOut(true);
        longestCustomer = null;
        return str.toString();
    }

    /**
     * This is a method to reset a Customer's shopping information after they have checked out.
     * After the customer checks out, they will have the decision to leave or continue to shop.
     * If they continue to shop, this method will be called.
     * Their total time shopping will be set to 0 and the boolean value describing if they are in a checkout will be set to false.
     * Since the customer checked out, they will have been the longestCustomer in the store so the longestCustomer is updated to null.
     * This meeans they are no longer the customer in the store the longest.
     * @param customer - A customer object representing the customer to
     */
    public void resetCustomer(Customer customer) {
        customer.setTotalTime(0);
        customer.setCheckOut(false);
        longestCustomer = null;
    }

    /**
     * The removeLongestCustomer method sets the longestCustomer field to null meaning that there will be a new customer in the store the longest.
     * @param customer - the Customer to be removed
     */
    public void removeLongestCustomer(Customer customer) {
        customers.remove(customers.search(customer.getName())+customers.size());
        longestCustomer = null;
    }

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

    public String nextCheckOut() {
        String cust = null;
        CheckOut<Customer> next = nextLane();
        if(next != null) {
            cust = next.peek().getName();
        }
        return cust;
    }

    // check out person based on which checkout lane is next
    // take in param of if they leave or return shopping
    // if leave, remove from list of all customers aswell
    // returns int of how many items they have. If -1, they are leaving and can be ignored,
    // if they go back to shopping, returns the amount of items they have
    // ADD ERROR CHECKING?
    public Customer checkOut(boolean leave) {
        CheckOut<Customer> line = this.nextLane();
        Customer cust = line.dequeue();

        if(leave) {
            int index = customers.search(cust.getName());
            index = index + customers.size();
            customers.remove(index);
        } else {
            cust.setTotalTime(0);
            cust.setCheckOut(false);
        }
        checkOutStart = (checkOutStart + 1)%3;
        return cust;
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
        if(index > < 0) {
            int trueIndex = index + customers.size();
            cust = customers.get(trueIndex);
	}
        return cust;
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
            retStr.append("The following " + shoppers + " customers are in the Shopping Center:\n");
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
     * Method to display the contents of the first line
     * @return String - information of the contents in the first line.
     */
    public String displayLine1() {
        return lineOne.toString();
    }

    /**
     * Method to display the contents of the second line
     * @return String - information of the contents in the second line.
     */
    public String displayLine2() {
        return lineTwo.toString();
    }

    /**
     * Method to display the contents of the express line
     * @return String - information of the contents in the express line.
     */
    public String displayExpress() {
        return expressLine.toString();
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
     * Mutator method to set the restocking level to the amount passed as a parameter
     * @param restockingLevel - int type that holds the new stocking level value to be stored
     */
    public void setRestockingLevel(int restockingLevel) {
        this.restockingLevel = restockingLevel;
    }
}
