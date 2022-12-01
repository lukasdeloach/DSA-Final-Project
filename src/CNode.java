public class CNode<T> {
    private T item;
    private CNode<T> next;

    public CNode(T newItem) {
        item = newItem;
        next = this;
    } // end constructor

    public CNode(T newItem, CNode<T> nextNode) {
        item = newItem;
        next = nextNode;
    } // end constructor

    public void setItem(T newItem) {
        item = newItem;
    } // end setItem

    public T getItem() {
        return item;
    } // end getItem

    public void setNext(CNode<T> nextNode) {
        next = nextNode;
    } // end setNext

    public CNode<T> getNext() {
        return next;
    } // end getNext
}

