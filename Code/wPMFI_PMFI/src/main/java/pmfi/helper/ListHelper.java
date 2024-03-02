package pmfi.helper;

import java.util.List;

public class ListHelper {
    /**
     * check a sub-list is sub-list at the end of larger list
     * @param largerList
     * @param subList
     * @return a list is sub list at the end of another list ?
     * @param <E> type of item in list
     */
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
