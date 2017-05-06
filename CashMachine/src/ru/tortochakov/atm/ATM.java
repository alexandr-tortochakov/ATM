package ru.tortochakov.atm;

import java.util.*;

public class ATM implements ATMAbstract {
    private final int MAX_CAPACITY = 6000;
    private int nBills = 6000;
    private int totalMoney = 0;
    private final Map<Integer, Integer> content = createMap();


    private Map<Integer, Integer> createMap() {
        Map<Integer, Integer> myMap = new HashMap<>();
        myMap.put(10, 1000);
        myMap.put(50, 1000);
        myMap.put(100, 1000);
        myMap.put(500, 1000);
        myMap.put(1000, 1000);
        myMap.put(5000, 1000);
        for (Map.Entry<Integer, Integer> entry : myMap.entrySet()) {
            totalMoney += entry.getKey() * entry.getValue();
        }
        return myMap;
    }


    @Override
    public boolean takeBills(Map<Integer, Integer> bills) {
        for (Map.Entry<Integer, Integer> entry : bills.entrySet()) {
            int billWorth = entry.getKey();
            int billAmount = entry.getValue();
            int current = content.get(billWorth);
            if (MAX_CAPACITY - nBills == 0) {
                System.out.println("Низя!");
                return false;
            } else if (MAX_CAPACITY - nBills >= billAmount) {
                content.put(billWorth, billAmount + current);
                nBills += billAmount;
                totalMoney += entry.getKey() * entry.getValue();
            }
        }
        return true;
    }

    @Override
    public Map<Integer, Integer> giveBills(int amount, boolean isLarge) {
        if (totalMoney < amount) {
            System.out.println("Отстань!");
            return null;
        }
        ArrayList<Integer> worthList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : content.entrySet()
                ) {
            if (entry.getValue() > 0) {
                worthList.add(entry.getKey());
            }
        }
        Collections.sort(worthList);
        Map<Integer, Integer> temp = new HashMap<>();
        for (int i = isLarge ? worthList.size() - 1 : 0; isLarge ? i >= 0 : i < worthList.size(); i += isLarge ? -1 : 1) {
            int worth = worthList.get(i);
            int nWorths = amount / worth;
            int nTake = Math.min(nWorths, content.get(worth));
            temp.put(worth, nTake);
            amount -= worth * nTake;
        }
        if (amount > 0) {
            return null;
        } else {
            for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
                int worth = entry.getKey();
                int nTake = entry.getValue();
                nBills -= nTake;
                content.put(worth, content.get(worth) - nTake);
                totalMoney -= worth * nTake;
            }
        }
        return temp;
    }

    @Override
    public String displayState() {
        StringBuilder builder = new StringBuilder();
        builder.append(totalMoney).append("\n").append(nBills).append("\n");
        ArrayList<Integer> worthList = new ArrayList<>(content.keySet());
        Collections.sort(worthList);
        for (Integer o : worthList) {
            builder.append("Worth: ").append(o).append(" amount: ").append(content.get(o)).append("\n");
        }
        return builder.toString();
    }
}
