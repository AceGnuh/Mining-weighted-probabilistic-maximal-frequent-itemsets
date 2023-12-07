package pmfi.pmfit;

import java.util.ArrayList;
import java.util.List;

public class Node <E>{
    private ItemsetTuple<E> item;
    private List<Node<E>> child;

    public Node(ItemsetTuple<E> item, List<Node<E>> child) {
        this.item = item;
        this.child = child;
    }

    public Node(ItemsetTuple<E> item) {
        this(item, new ArrayList<>());
    }

    public ItemsetTuple<E> getItem() {
        return item;
    }

    public void setItem(ItemsetTuple<E> item) {
        this.item = item;
    }

    public List<Node<E>> getChild() {
        return child;
    }

    public void setChild(List<Node<E>> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Node{" +
                "item=" + item +
                ", child=" + child +
                '}';
    }
}
