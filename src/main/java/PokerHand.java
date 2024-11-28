import java.util.ArrayList;
import java.util.List;

public class PokerHand implements Comparable<PokerHand> {
    private final List<Card> cards;
    private final HandRank rank;
    private final PokerHandService service;

    public PokerHand(String hand) {
        service = new PokerHandService();
        cards = new ArrayList<>();

        String[] stringCards = hand.split(" ");
        if (stringCards.length != 5) {
            throw new IllegalArgumentException("Invalid number of cards");
        }

        for(String card : stringCards) {
            cards.add(new Card(card));
        }

        this.rank = service.calculateRank(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
    public HandRank getRank() {
        return rank;
    }

    @Override
    public int compareTo(PokerHand other) {
        // обработка одинаковых комбинаций, но разного старшинства карт
        if (this.rank == other.rank) {
            return service.compareHands(this, other, rank);
        }

        return this.rank.compareTo(other.rank);
    }

    @Override
    public String toString() {
        return String.join(" ", cards.stream()
                .map(Card::toString)
                .toArray(String[]::new)) + " - " + rank;
    }

    protected enum HandRank {
        HIGH_CARD,
        PAIR,
        TWO_PAIRS,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }
}


