
/**
 * Customer class
 * Will add Javadoc later
 * @author Lukas DeLoach
 */
public class Customer extends KeyedItem<String> {

    private String name;
    private int totalTime;
    private int itemAmount;

    public Customer(String name) {

	super(name);
        if(!name.isEmpty()) {
            this.name = name;
        }
        itemAmount = totalTime = 0;
    }

    /**
     * Basic method will add more logic later
     * */
    public void addItem(Item item) {
        itemAmount++;
    }

    /**
     * Basic method will add more logic later
     * */
    public void removeItem() {
        itemAmount--;

    }

    public void updateTime(){
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

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
}
