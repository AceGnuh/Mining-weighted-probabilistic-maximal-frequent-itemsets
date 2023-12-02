package pmfi.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static <T> List<List<T>> findSubsetsWithSameLength(List<List<T>> inputSet, int length) {
        List<List<T>> result = new ArrayList<>();
        if (length < 0 || length > inputSet.size()) {
            return result;
        }

        generateSubsetsWithSameLength(inputSet, length, new ArrayList<>(), result);
        return result;
    }

    private static <T> void generateSubsetsWithSameLength(
            List<List<T>> remainingSet, int length, List<T> currentSubset, List<List<T>> result) {

        if (length == 0) {
            result.add(new ArrayList<>(currentSubset));
            return;
        }

        for (List<T> element : remainingSet) {
            List<List<T>> newRemainingSet = new ArrayList<>(remainingSet);
            newRemainingSet.remove(element);

            List<T> newCurrentSubset = new ArrayList<>(currentSubset);
            newCurrentSubset.addAll(element);

            generateSubsetsWithSameLength(newRemainingSet, length - 1, newCurrentSubset, result);
        }
    }
}
