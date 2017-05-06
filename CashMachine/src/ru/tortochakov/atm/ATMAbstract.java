package ru.tortochakov.atm;

import java.util.Map;

public interface ATMAbstract {
    public boolean takeBills(Map<Integer, Integer> bills);

    public Map<Integer, Integer> giveBills (int amount, boolean isLarge);

    public String displayState ();
}
