package ru.tortochakov.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    private ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATM();
    }

    @Test
    void testTakeBills() {
        Map<Integer, Integer> teamo = new HashMap<>();
        atm.giveBills(10, true);
        atm.giveBills(10, true);
        atm.giveBills(10, true);
        assertEquals(atm.displayState(), "6659970\n" +
                "5997\n" +
                "Worth: 10 amount: 997\n" +
                "Worth: 50 amount: 1000\n" +
                "Worth: 100 amount: 1000\n" +
                "Worth: 500 amount: 1000\n" +
                "Worth: 1000 amount: 1000\n" +
                "Worth: 5000 amount: 1000\n");
        teamo.put(10, 1);
        teamo.put(100, 1);
        teamo.put(50, 1);
        atm.takeBills(teamo);
        System.out.println(atm.displayState());
        assertEquals(atm.displayState(), "6660130\n" +
                "6000\n" +
                "Worth: 10 amount: 998\n" +
                "Worth: 50 amount: 1001\n" +
                "Worth: 100 amount: 1001\n" +
                "Worth: 500 amount: 1000\n" +
                "Worth: 1000 amount: 1000\n" +
                "Worth: 5000 amount: 1000\n");
    }

    private int sum(Map<Integer, Integer> temp) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
            sum += entry.getValue() * entry.getKey();
        }
        return sum;
    }

    @Test
    void testGiveBills() {
        Map<Integer, Integer> temp = atm.giveBills(1000, true);
        assertEquals(sum(temp), 1000);
        temp = atm.giveBills(3570, true);
        assertEquals(sum(temp), 3570);
        for (int i = 0; i < 999; i++) {
            temp = atm.giveBills(50, true);
        }
        temp = atm.giveBills(60, true);
        assertEquals(temp.get(10), (Integer) 6);
        System.out.println(atm.displayState());
        temp = atm.giveBills(600, false);
        assertEquals(temp.get(10),(Integer) 60);
        temp = atm.giveBills(222, false);
        assertEquals(temp, null);
    }

    @Test
    void testDisplayState() {
        String s = atm.displayState();
        assertEquals(s, "6660000\n" +
                "6000\n" +
                "Worth: 10 amount: 1000\n" +
                "Worth: 50 amount: 1000\n" +
                "Worth: 100 amount: 1000\n" +
                "Worth: 500 amount: 1000\n" +
                "Worth: 1000 amount: 1000\n" +
                "Worth: 5000 amount: 1000\n");
    }

}