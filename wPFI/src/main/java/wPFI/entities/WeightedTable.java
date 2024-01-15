package wPFI.entities;

import java.util.*;

public class WeightedTable <E>{
    /**
     * key : item;
     * value: weighted of item
     */
    private Map<E, Double> weighedTable;

    public WeightedTable(Map<E, Double> weighedTable) {
        this.weighedTable = weighedTable;
    }

    public WeightedTable() {
        this(new HashMap<>());
    }

    public Map<E, Double> getWeighedTable() {
        return weighedTable;
    }

    public void setWeighedTable(Map<E, Double> weighedTable) {
        this.weighedTable = weighedTable;
    }

    /**
     * Calculate weighted of itemset
     * @param itemset
     * @return itemset weighted
     */
    public double getWeighedItemset(Set<E> itemset){
        double weighted = 0.0;

        for(E item : itemset){
            weighted += weighedTable.getOrDefault(item, 0.0);
        }

        return weighted / itemset.size();
    }

    public double findMaxWeighted() {
        double maxWeighted = Integer.MIN_VALUE;

        for(E item : weighedTable.keySet()){
            //get current weighted in weighted table
            double currWeighted = weighedTable.get(item);

            if(currWeighted > maxWeighted){
                maxWeighted = currWeighted;
            }
        }

        return maxWeighted;
    }

    public double findMinWeightItemset(Set<E> itemset){
        double minWeightedItemset = 1.1;
        List<Double> weightedItemList = new ArrayList<>();

        //get all item weighted in itemset
        for(E item: itemset){
            weightedItemList.add(weighedTable.get(item));
        }

        //find min weighted in itemset
        for(Double itemWeighted : weightedItemList){
            minWeightedItemset = Math.min(minWeightedItemset, itemWeighted);
        }

        return minWeightedItemset;
    }

    @Override
    public String toString() {
        return "WeightedTable{" +
                "weighedTable=" + weighedTable +
                '}';
    }
}
