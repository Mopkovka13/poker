import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<PokerHand> hands = new ArrayList<>();
        hands.add(new PokerHand("KS 2H 5C JD TD"));
        hands.add(new PokerHand("2C 3C 6C 4C 5C"));
        hands.add(new PokerHand("3C 3C 3C 3C 5D"));
        hands.add(new PokerHand("6C 6C KC KC KC")); // Фулл хаус или флэш? Должен быть фулл хаус, т.к. старше
        hands.add(new PokerHand("TS JS QD AS ArS"));
        hands.add(new PokerHand("TD JD QS KD AS"));

        System.out.println("before ranking");
        for (PokerHand hand : hands) {
            System.out.println(hand);
        }

        hands.sort(Collections.reverseOrder());

        System.out.println("\nafter ranking");
        for (PokerHand hand : hands) {
            System.out.println(hand);
        }
    }
}
