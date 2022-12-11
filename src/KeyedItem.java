/**
 * KeyedItem class that uses a Key that can be compared
 */
public abstract class KeyedItem<KT extends Comparable<? super KT>> {
    private KT searchKey;

    public KeyedItem(KT key) {
        searchKey = key;
    }  // end constructor

    /**
     * Getter method of return type KT that returns the data field searchKey
     * @return searchKey - of type KT that holds the value of the key
     */
    public KT getKey() {
        return searchKey;
    } // end getKey
} // end KeyedItem

