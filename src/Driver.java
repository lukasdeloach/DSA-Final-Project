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

    public static void main(String[] args) {

        // Declare ListArrayBased Plus variable local to main
        ListArrayBasedPlusN myList = new ListArrayBasedPlusN();

        // Print out to user options for input
        System.out.println("Enter an option below:");
        System.out.println("0. Close the Shopping Center.\n1. Customer enter Shopping Center.\n2. Customer picks an item and places it in the shopping cart." +
                           "\n3. Customer removes an item from the shopping cart.\n4. Customer is done shopping.\n5. Customer checks out.\n6. Print info about customers who are shopping."+
                           "\n7. Print info about customers in checkout lines.\n8. Print info about items at or below re-stocking level.\n9. Reorder an item.");

        // do while that will repeat until user inputs a 0. Do while will run once the first time before initial input
        int choice = 0;
        do {
            boolean correct = false;
            while(!correct) { // until valid response given, will ask for input
                // print out each time to choose option from menu
                System.out.print("Make your menu selection now: ");
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
                insertItem(myList);
                break;
            case 2:
                removeItem(myList);
                break;
            case 3:
                getItem(myList);
                break;
            case 4:
                getSearchKey(myList);
                break;
            case 5:
                clearList(myList);
                break;
            case 6:
                printListStats(myList);
                break;
            default:
                invalidInput();
                break;
            }
        } while(choice != 0);// do while choice is not 0


    }// end main

    // method to call when input is out of range
    public static void outOfRange() {
        System.out.println("Position specified is out of range!");
    }// end outOfRange call

    // method to declare when list is empty
    public static void listEmpty() {
        System.out.println("List is empty.");
    }// end listEmpty

    // method to exit program
    public static void exitProgram() {
        System.out.println("Exiting program...Good Bye");
    }// end exitProgram

    // method called when want to add item
    public static void insertItem(ListArrayBasedPlusN list) {
        // set up for variables needed
        String item = null;
        int position;

        // prompt for item to insert
        String key = "";
        boolean correct = false;
        while(!correct) { // reprompt until valid input given
            System.out.println("You are now inserting an item into the ordered list.");
            System.out.print("\tEnter item: ");
            try {
                key = stdin.readLine().trim();
                System.out.println(key);
                correct = true;
            } catch(Exception e) {
                invalidInput();
            }
        }// end while

        // if list is greater than size 0, search for the key
        if(list.size() > 0) {
            position = search(list, key);
            //if position is less than one, it is negtaive,
            //negative means it does not exist, and can be added
            //so multiply by -1, and minus one to retrieve where it needs to be placed
            if(position < 0) {
                position = (position *-1) - 1;
                list.add(position, key);
                // otherwise it already exists, and no eduplicates are allowed
            } else {
                System.out.println(key + " already exists in list at position " + position);
            }// end else
            // if list is of size 0, means there can be no duplicates and can be added.
        } else {
            list.add(0, key);
        }// end else
    }// end method


    // method to remove item based on index
    public static void removeItem(ListArrayBasedPlusN list) {
        // prompt for and read index to remove from, if empty unecessary to call for prompt
        if(list.size() != 0) {
            int position = 0;

            boolean correct = false;
            while(!correct) { // continue to prompt until valid response is given.
                System.out.print("\tEnter position to remove item from: ");
                try {
                    position = Integer.parseInt(stdin.readLine().trim());
                    System.out.println(position);
                    correct = true;
                } catch (Exception e) {
                    invalidInput();
                }
            }
            // if index is valid, remove
            if(position >= 0 && position < list.size()) {
                String removed = (String)list.get(position);
                list.remove(position);
                System.out.println("Item "+ removed  + " removed from position " + position +" in the list.");
                // else, print outOfRange
            } else {
                outOfRange();
            }// end else
        } else { // if list is empty, print
            listEmpty();
        }//end else
    }// end removeItem

    // method to get Item based on index give
    public static void getItem(ListArrayBasedPlusN list) {
        // prompt for and read in index, if empty, no need to prompt
        if(list.size() != 0) {
            int position = 0;

            boolean correct = false;
            while(!correct) { // reprompt until valid input given
                System.out.print("\tEnter position to retrieve item from: ");
                try {
                    position = Integer.parseInt(stdin.readLine().trim());
                    System.out.println(position);
                    correct = true;
                } catch(Exception e) {
                    invalidInput();
                }
            }
            // if index is valid, get item
            if(position >= 0 && position < list.size()) {
                String retrieved = (String)list.get(position);
                System.out.println("Item " + retrieved + " retrieved from position " + position + " in the list.");
                // else, print outOfRange
            } else {
                outOfRange();
            }// end else
        } else { // if empty, print
            listEmpty();
        }// end else
    }// end getItem

    // method to utilize search and print values to user
    public static void getSearchKey(ListArrayBasedPlusN list) {
        if(!list.isEmpty()) {
            // prompt for what value you are looking for, and except only valid input
            String key = "";
            boolean correct = false;
            while(!correct) { // reprompt until valid input given
                System.out.print("\tWhat value are you searching for? ");
                try {
                    key = stdin.readLine().trim();
                    System.out.println(key);
                    correct = true;
                } catch(Exception e) {
                    invalidInput();
                }
            }// end while

            // save value of search
            int ind = search(list, key);

            // if serach key is found, print using decode, else print it is not in list
            if(ind > 0) {
                System.out.println("\t" + key + " was found in list, at position " + (ind - 1));
            } else {
                System.out.println("\t" + key + " does not exist in the list.");
            }// end else
            // if list is empty, print
        } else {
            listEmpty();
        }// end else
    }// end method


    //method to search for item in list
    // This uses Modified Sequential Search III, as it continues until a equality of over is reached
    // It uses continue to constantly advance as the search key is greater than the current checked key
    // If the value exists in the list, it will return the exact index it is at exactly
    // If it does not exist, since 0 is a valid return value for where a key already exists, the value
    // for where it should be inserted will be returned, but 1 added to the index of where, and then
    // made negative.
    // TO DECODE: where value would be if inserted (does not already exist)multiply by negative, and minus 1
    //  If sucessful(Does not exist yet in list), -1 from return value.
    private static int search(ListArrayBasedPlusN list, String searchKey) {
        // save value of size to avoid repetitive calls
        int listSize = list.size();
        //return value for index
        int indexVal = 0;
        // if list is not empty
        // for size of array, search sequntially
        for(int i = 0 ; i < listSize; i++) {
            String checkKey = (String)list.get(i);
            if(searchKey.compareTo(checkKey) > 0) {
                continue;
            } else if(checkKey.equals(searchKey)) {
                indexVal = i + 1;
                i = listSize;
            } else {
                indexVal = -1*(i + 1);
                i = listSize;
            }// end else
        }// end for

        if(indexVal == 0) {
            indexVal = -1*(listSize+1);
        }
        return indexVal;
    }

    // method to clear all from list using ListArrayBased removeAll method
    public static void clearList(ListArrayBasedPlusN list) {
        if(list.size() > 0) {
            list.removeAll();
        }
    }

    // method to print info on list
    public static void printListStats(ListArrayBasedPlusN list) {
        // get num of items in list
        int listSize = list.size();
        // if 0, print it is empty
        if(listSize == 0) {
            listEmpty();
            // else, print size and use toString to get all items in list seperated by spaces
        } else {
            System.out.println("\tList of size " + listSize + " has the following items: " + list.toString());
        }// end else
    }// end printListStats

    // method to declare that inputted option is not valid from menu items
    public static void invalidInput() {
        System.out.println("This is not an option. Try again.");
    }// end invalidInput
}// end class
