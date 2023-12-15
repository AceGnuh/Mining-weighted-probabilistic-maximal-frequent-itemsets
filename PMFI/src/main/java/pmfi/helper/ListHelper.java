package pmfi.helper;

import java.util.List;

public class ListHelper {
    public static <E> boolean isSubListAtEnd(List<E> largerList, List<E> subList) {
        // Check if the largerList has enough elements for subList to be at the end
        if (largerList.size() < subList.size()) {
            return false;
        }

        // Get the tail (last elements) of the largerList
        List<E> tail = largerList.subList(largerList.size() - subList.size(), largerList.size());

        // Compare the tail with the subList
        return tail.equals(subList);
    }
}
