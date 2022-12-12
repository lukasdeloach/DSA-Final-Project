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
            int search = customerSearch(name);
            if(search==-1) {
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
     * Need to add which queue takes priority after given input at initial start of program
     * @param customer - passed customer object
     * @return String  - String object which describes which line the customer was added into
     */
    public String enqueueCustomer(Customer customer) {
        int itemAmount = customer.getItemAmount();
        int expressSize = expressLine.size(), lineOneSize = lineOne.size(), lineTwoSize = lineTwo.size();
        StringBuilder str = new StringBuilder();
        if(itemAmount < 5) {
            //System.out.println("line one: " + (expressLine.size() + 1 > lineOne.size()<<1));
            //System.out.println("line two: " + (expressLine.size() + 1 > lineTwo.size()<<1));
            if((expressLine.size() > lineOne.size()<<1) || (expressLine.size() > lineTwo.size()<<1)) {
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

    public void resetCustomer(Customer customer) {
        customer.setTotalTime(0);
        customer.setCheckOut(false);
        longestCustomer = null;
    }

    public void removeLongestCustomer(Customer customer) {
        customers.remove(customers.search(customer.getName())+customers.size());
        longestCustomer = null;
    }


    // confirm where the line counter should restart from
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

    //ADJUSTED FOR CASE 9, REVISIT
    public Item itemSearch(String name) {
        Item result =  null;
        int temp = 0;
        if(!items.isEmpty()) {
            temp = items.search(name);
            if(temp < 0) {
                result = items.get(temp+items.size());;
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
