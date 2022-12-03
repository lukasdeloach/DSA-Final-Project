
/**
 *
 * should we perform checkout checks for add and remove
 *
 * Customer class
 * Will add Javadoc later
 * @author Lukas DeLoach and Jessica Rodgers
 */
public class Customer extends KeyedItem<String> {

    private String name;
    private int totalTime;
    private int itemAmount;
    private boolean checkOut;

    public Customer(String name) {

        super(name);
        if(!name.isEmpty()) {
            this.name = name;
        }
        itemAmount = totalTime = 0;
        checkOut = false;
    }

    /**
     * Basic method will add more logic later
     * */
    public void addItem(Item item) {
        itemAmount++;
    }

    public void updateTime() {
        totalTime++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void removeItem() {
        itemAmount--;
    }

    public void setCheckOut(boolean status) {
        checkOut = status;
    }

    public boolean getCheckOut() {
        return checkOut;
    }

    public String toString() {
        String retStr = name + " " + itemAmount + " " + totalTime;
        return retStr;
    }
}
