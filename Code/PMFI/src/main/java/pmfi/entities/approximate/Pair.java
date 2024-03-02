package pmfi.entities.approximate;

/**
 * Object has 2 part to store data
 * @param <K> type of first data
 * @param <T> type of second data
 */
public class Pair <K, T>{
    private T first;
    private K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public K getSecond() {
        return second;
    }

    public void setSecond(K second) {
        this.second = second;
    }
}
