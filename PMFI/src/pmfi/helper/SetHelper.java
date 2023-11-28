package pmfi.helper;

import java.util.ArrayList;
import java.util.List;

public class SetHelper {
    public static <E> List<List<E>> generateAllSubsetFromSet(List<E> set){

        List<List<E>> setAllSubset = new ArrayList<>();

        int max = 1 << set.size();             //there are 2 power n different subsets

        for (int i = 0; i < max; i++) {
            List<E> subset = new ArrayList<>();

            for (int j = 0; j < set.size(); j++) {
                if (((i >> j) & 1) == 1) {
                    subset.add(set.get(j));
                }
            }

            setAllSubset.add(subset);
        }
        return setAllSubset;
    }

    public static <E> void generateCombinationsHelper(List<List<List<E>>> lists, int index, List<List<E>> currentCombination, List<List<List<E>>> result) {
        if (index == lists.size()) {
            result.add(new ArrayList<>(currentCombination));
            return;
        }

        for (List<E> element : lists.get(index)) {
            currentCombination.add(new ArrayList<>(element));
            generateCombinationsHelper(lists, index + 1, currentCombination, result);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}
