import java.util.Map;

public interface ATMAbstract {
    public void takeBills(Map<Integer, Integer> bills);

    public Map<Integer, Integer> giveBills (int amount, boolean isLarge);

    public String displayState ();
}
