/**
 * Updated with driver options. No other features added.
 * - Lukas DeLoach
 */

import java.io.*;
import java.util.*;

public class Driver {

    static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    // create static reader to be used throughout driver, in accordance with coding guidlines
    // static as it belongs to class

    public static void main(String[] args) throws IOException {


        ShoppingCenterModel shoppingCenter = new ShoppingCenterModel();
        shoppingCenter = simulateStart(shoppingCenter);

        // Print out to user options for input
        System.out.println("\nHere are the choices to select from: ");
        System.out.println("\t0. Close the Shopping Center.\n\t1. Customer enter Shopping Center.\n\t2. Customer picks an item and places it in the shopping cart." +
                           "\n\t3. Customer removes an item from the shopping cart.\n\t4. Customer is done shopping.\n\t5. Customer checks out.\n\t6. Print info about customers who are shopping."+
                           "\n\t7. Print info about customers in checkout lines.\n\t8. Print info about items at or below re-stocking level.\n\t9. Reorder an item.");
        System.out.print("Make your menu selection now: ");

        // do while that will repeat until user inputs a 0. Do while will run once the first time before initial input
        int choice = 0;
        do {
            boolean correct = false;
            while(!correct) { // until valid response given, will ask for input
                // scan and read inputted option
                try {
                    choice = Integer.parseInt(stdin.readLine().trim());
                    System.out.println(choice);
                    correct = true;
                } catch(Exception e) {
                    invalidInput();
                }
            }

            // switch on inputed option. Provide options to method calls. If non-valid input, call method to display it is invalid.
            switch(choice) {
            case 0:
                exitProgram();
                break;
            case 1:
                inputCaseOne(shoppingCenter);
                break;
            case 2:
                inputCaseTwo(shoppingCenter);
                break;
            case 3:
                inputCaseThree(shoppingCenter);
                break;
            case 4:
                inputCaseFour(shoppingCenter);
                break;
            case 5:
                inputCaseFive(shoppingCenter);
                break;
            case 6:
                inputCaseSix(shoppingCenter);
                break;
            case 7:
                inputCaseSeven(shoppingCenter);
                break;
            case 8:
                inputCaseEight(shoppingCenter);
                break;
            case 9:
                inputCaseNine(shoppingCenter);
                break;
            default:
                invalidInput();
                break;
            }
            System.out.print("\nSelect an operation from the following menu: ");
        } while(choice != 0);// do while choice is not 0


    }// end main

    // method to call when input is out of range
    public static void outOfRange() {
        System.out.println("Position specified is out of range!");
    }// end outOfRange call

    // method to exit program
    public static void exitProgram() {
        System.out.println("Exiting program...Good Bye");
    }// end exitProgram
    //
    public static ShoppingCenterModel simulateStart(ShoppingCenterModel store) throws IOException {

        System.out.println("Welcome to the Shopping Center!!!");
        System.out.println("\nPlease specify stock");
        System.out.print(" How many items do you have? ");
        int numOfItems = convertToInt(stdin.readLine().trim());
        System.out.println(numOfItems);
        System.out.print("Please specify restocking amount: ");
        int stockingAmount = convertToInt(stdin.readLine().trim());
        System.out.println(stockingAmount);
        store.setRestockingLevel(stockingAmount);
        for(int i = 0; i < numOfItems; i++) {
            System.out.print(">>Enter item name : ");
            String itemName = stdin.readLine().trim();
            System.out.println(itemName);
            System.out.print(">>How many " + itemName + "s? ");
            int itemNum = convertToInt(stdin.readLine().trim());
            System.out.println(itemNum);
            boolean status = store.addItem(itemName, itemNum);
            System.out.println(itemNum + " items of " + itemName + " have been placed in stock.");
            while(!status) {
                System.out.println("Invalid or Duplicate item. Try again");
                System.out.print(">>Enter item name : ");
                itemName = stdin.readLine().trim();
                System.out.println(itemName);
                System.out.print(">>How many " + itemName + "s? ");
                itemNum = convertToInt(stdin.readLine().trim());
                System.out.println(itemNum);
                status = store.addItem(itemName, itemNum);
            }
        }
        System.out.print("Please select the checkout line that should check out customers first (regular1/regular2/express): ");
        String checkOutLine = stdin.readLine().trim();
        System.out.println(checkOutLine);
        return store;
    }





    // method called when want to add item
    public static void inputCaseOne(ShoppingCenterModel store) throws IOException {
        // set up for variables needed
        boolean added;
        do {
            System.out.print(">>Enter customer name : ");
            String name = stdin.readLine().trim();
            System.out.println(name);
            added = store.addCustomer(name);
            if(added == true) {
                System.out.println("Customer " + name + " is now in the Shopping Center.");
            }
            else {
                System.out.println("Customer " + name + " is already in the Shopping Center!");
            }
        } while(added == false);
        // prompt for item to insert
    }// end method


    // HOW MUCH OF THE ITEMS NEEDS TO BE DONE IN SHOPPING CENTER, NOT HERE
    // ADJUST ITEMSEARCH METHOD
    // method to add item based on index
    public static void inputCaseTwo(ShoppingCenterModel store) throws IOException {
        // prompt for and read index to remove from, if empty unecessary to call for prompt
        if(!store.isEmpty()) {
            System.out.print(">>Enter customer name : ");
            String name = stdin.readLine().trim();
            System.out.println(name);
            int custResult = store.customerSearch(name);
            while(custResult<0) {
                if(custResult == 0) {
                    System.out.println("Customer "+ name+ " is in a checkoutline !");
                } else {
                    System.out.println("Customer " + name + " is not in shopping center!");
                }

                System.out.print(">>Enter customer name : ");
                name = stdin.readLine().trim();
                System.out.println(name);
                custResult = store.customerSearch(name);
            }
            Customer customer = (Customer) store.getCustomers().get(custResult);
            System.out.print("What item does " + name + " want? ");
            String itemName = stdin.readLine().trim();
            System.out.println(itemName);
            Item item = store.itemSearch(itemName);
            if(item == null) {
                System.out.println("No " + itemName + "s in Shopping Center.");
            }

            else {
                if(item.getAmount() < 1) {
                    System.out.println("No " + itemName + "s in stock.");
                }
                else {
                    store.customerAddItem(customer, item);
                    //ternary operator?
                    int custItems = customer.getItemAmount();
                    if(custItems != 1) {
                        System.out.println("Customer " + name + " has now " + customer.getItemAmount() + " items in the shopping cart.");
                    } else {
                        System.out.println("Customer " + name + " has now " + customer.getItemAmount() + " item in the shopping cart.");
                    }

                }
            }
        } else {
            System.out.println("\tNo one is in the Shopping Center!");
        }
    }// end inputCaseTwo
    //

    public static void inputCaseThree(ShoppingCenterModel store) throws IOException {
        if(store.isEmpty()) {
            System.out.println("\tNo one is in the Shopping Center!");
        }
        else {
            System.out.print(">>Enter customer name : ");
            String name = stdin.readLine().trim();
            System.out.println(name);
            int custResult = store.customerSearch(name);
            while(custResult<0) {

                System.out.println(">>Enter customer name : ");
                name = stdin.readLine().trim();
                System.out.println(name);
                custResult = store.customerSearch(name);
            }
            Customer customer = (Customer) store.getCustomers().get(custResult);
            int amount = customer.getItemAmount();
            //fix to call item amount once
            if(amount == 0) {
                System.out.println("Customer " + name + " does not have any items in their shopping cart!");
            }
            //get rid of repeated code
            else {
                store.customerRemoveItem(customer);
                if(customer.getItemAmount() == 1) {
                    System.out.println("Customer " + name + " has now " + customer.getItemAmount() +
                                       " item in the shopping cart.");
                } else {
                    System.out.println("Customer " + name + " has now " + customer.getItemAmount() +
                                       " items in the shopping cart.");
                }
            }
        }
    }

    public static void inputCaseFour(ShoppingCenterModel store) throws IOException {
        if(store.isEmpty()) {
            System.out.println("\tNo customers in the Shopping Center!");
        }
        else {
            Customer cust = store.getLongestShopper();
            String name = cust.getName();
            int items = cust.getItemAmount();
            if(cust == null) {
                System.out.println("Every Customer is in a Checkout Line");
            }
            else if(cust.getItemAmount() == 0) {
                System.out.print("Should customer " + name + " leave or keep on shopping? Leave?(Y/N): ");
                String answer = stdin.readLine().trim();
                System.out.println(answer);
                if(answer.equalsIgnoreCase("y")) {
                    store.removeLongestCustomer(cust);
                    System.out.println("Customer " + name + "has left.");
                }
                else {
                    store.resetCustomer(cust);
                    System.out.println("Customer " + name + " with " + items + " returned to shopping.");
                }
            }
            else {
                String lineStatus = store.enqueueCustomer(cust);
                System.out.println("After " + cust.getTotalTime() + " minutes in the Shopping Center, customer " + name + " with " + items + " is now in the " + lineStatus + ".");
            }
        }
    }


    public static void inputCaseFive(ShoppingCenterModel store) throws IOException {
        String name = store.nextCheckOut();
        if(name == null) {
            System.out.println("\tNo customers in any line.");
        } else {
            boolean leave = true;
            String confirm;
            boolean correct = false;
            do {
                System.out.print("Should customer " +  name + " leave or keep on shopping? Leave?(Y/N):");
                confirm = stdin.readLine().trim();
                if(confirm.equalsIgnoreCase("Y")) {
                    correct = true;
                } else if(confirm.equalsIgnoreCase("N")) {
                    leave = false;
                    correct = true;
                } else {
                    invalidInput();
                }
            } while(correct == false);

            Customer left = store.checkOut(leave);
            if(leave == true) {
                System.out.print("Customer" + name + " is now leaving the Shopping Center.");
            } else {
                int items = left.getItemAmount();
                System.out.print("Customer " + name + " with ");
                if(items > 1) {
                    System.out.print(items + " items ");
                } else {
                    System.out.print(items + " item ");
                }
                System.out.println(" returned to shopping.");
            }
        }
    }

    //
    public static void inputCaseSix(ShoppingCenterModel store) {
        System.out.println(store.displayShoppers());
    }

    public static void inputCaseSeven(ShoppingCenterModel store) {
        System.out.println(store.displayLine1() + store.displayLine2() + store.displayExpress());
    }

    public static void inputCaseEight(ShoppingCenterModel store) {
        System.out.println("Items at re-stocking level: ");
        System.out.print(store.displayRestockingLevelItems());
    }
    // change how it is retrieved, need to confirm if it exists first, then
    // display if not existant, so search, return item, directly adjust, or pass to
    // method to update
    // CONFRIM BEFORE CORRECTION
    // -- AS OF NOW, SEARCHES TWICE
    public static void inputCaseNine(ShoppingCenterModel store) throws IOException {
        System.out.print("Enter item name to be re-ordered : ");
        String name = stdin.readLine().trim();
        System.out.println(name);
        Item item = store.itemSearch(name);
        if(item == null) {
            System.out.println(name + " is not it stock!");
        } else {
            // search goes here, confirm if it exists
            System.out.print("Enter number of " + name + "s to be re-ordered : ");
            int num = convertToInt(stdin.readLine().trim());
            System.out.println(num);
            store.orderItem(item, num);
            // then order based on name/item
            System.out.println("Stock has now " + item.getAmount() + " " + item.getName() + "s.");
        }
    }


    // method to declare that inputted option is not valid from menu items
    public static void invalidInput() {
        System.out.println("This is not an option. Try again.");
    }// end invalidInput


    /**
     * Method to convert to an integer from a string
     * @param s
     * @return i
     */
    private static int convertToInt(String s) {
        int i = -1;
        try {
            i = Integer.parseInt(s);
        }
        catch(Exception e) {
            System.out.println("Invalid number.");
        }
        return i;
    }

}// end class
