/**
 * This is the Item Class that extends the Keyed Item class.
 * The item that is considered a key for this class is a String which represents the name of the item.
 * Appropriate getter and setter methods for the class allow for easy access to the data fields and modification.
 * This class contains one Constructor which takes two paramters one of type String and the other of type int.
 * @author Lukas DeLoach
 */
public class Item extends KeyedItem<String> {

    private String name;
    private int amount;

    /**
     * The only Constructor for the Item class which takes two parameters that represent the item name and item amount. The constructor calls upon the KeyedItem classes constructor which instantiates the key as the Strng type name.
     * @param name - String type that represents the Item's name
     * @param amount - int type that represents the amount of the item
     */
    public Item(String name, int amount) {
        super(name);
        this.name = name;
        this.amount = amount;
    }


    /**
     * Getter method for the data field name.
     * @return name - String type name that represents the Item name.
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator method that decrements the item amount by one.
     */
    public void decrementAmount() {
        amount--;
    }

    /**
     * Setter method for the data field name
     * @param name - String type that represents the name of the Item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the data field amount of return type int.
     * @return amount - and integer value which represents the item amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter method for the data field amount.
     * @param amount - integer value which represents the new item amount.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
