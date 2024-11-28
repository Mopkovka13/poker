import java.util.*;
import java.util.stream.Collectors;

public class PokerHandService {

    public PokerHand.HandRank calculateRank(List<Card> cards) {
        if (isRoyalFlush(cards)) {
            return PokerHand.HandRank.ROYAL_FLUSH;
        } else if (isStraightFlush(cards)) {
            return PokerHand.HandRank.STRAIGHT_FLUSH;
        } else if (isFourOfAKind(cards)) {
            return PokerHand.HandRank.FOUR_OF_A_KIND;
        } else if (isFullHouse(cards)) {
            return PokerHand.HandRank.FULL_HOUSE;
        } else if (isFlush(cards)) {
            return PokerHand.HandRank.FLUSH;
        } else if (isStraight(cards)) {
            return PokerHand.HandRank.STRAIGHT;
        } else if (isThreeOfAKind(cards)) {
            return PokerHand.HandRank.THREE_OF_A_KIND;
        } else if (isTwoPairs(cards)) {
            return PokerHand.HandRank.TWO_PAIRS;
        } else if (isPair(cards)) {
            return PokerHand.HandRank.PAIR;
        }
        return PokerHand.HandRank.HIGH_CARD;
    }

    /*
     * @return Целое число, представляющее результат сравнения:
     *         0 - если руки равны,
     *         -1 - если первая рука меньше второй,
     *         1 - если первая рука больше второй.
     */
    public int compareHands(PokerHand hand, PokerHand otherHand, PokerHand.HandRank rank) {
        return switch(rank) {
            case HIGH_CARD -> compareHighCard(hand, otherHand);
            case PAIR -> comparePair(hand, otherHand);
            case TWO_PAIRS -> compareTwoPairs(hand, otherHand);
            case THREE_OF_A_KIND -> compareThreeOfAKind(hand, otherHand);
            case STRAIGHT -> compareStraight(hand, otherHand);
            case FLUSH -> compareFlush(hand, otherHand);
            case FULL_HOUSE -> compareFullHouse(hand, otherHand);
            case FOUR_OF_A_KIND -> compareFourOfAKind(hand, otherHand);
            case STRAIGHT_FLUSH -> compareStraightFlush(hand, otherHand);
            case ROYAL_FLUSH -> compareRoyalFlush(hand, otherHand);
        };
    }

    // Check combinations
    private boolean isRoyalFlush(List<Card> cards) {
        if (!isFlush(cards))
            return false;

        Set<Card.Value> highCards = Set.of(
                Card.Value.TEN,
                Card.Value.JACK,
                Card.Value.QUEEN,
                Card.Value.KING,
                Card.Value.ACE);

        return cards
                .stream()
                .map(Card::getValue)
                .collect(Collectors.toSet())
                .containsAll(highCards);
    }

    private boolean isStraightFlush(List<Card> cards) {
        return isFlush(cards) && isStraight(cards);
    }

    private boolean isFourOfAKind(List<Card> cards) {
        Map<Card.Value, Integer> valueCount = countValues(cards);

        return valueCount.containsValue(4);
    }

    private boolean isFullHouse(List<Card> cards) {
        Map<Card.Value, Integer> valueCount = countValues(cards);

        return valueCount.containsValue(3) && valueCount.containsValue(2);
    }

    private boolean isFlush(List<Card> cards) {
        Map<Card.Suit, Integer> suitCount = countSuits(cards);

        return suitCount.size() == 1;
    }

    private boolean isStraight(List<Card> cards) {
        List<Integer> values = cards
                .stream()
                .map(card -> card.getValue().getNumericValue())
                .sorted()
                .toList();

        if (!values.isEmpty()) {
            // Если разница между максимальным и минимальным = 4
            if (values.get(values.size() - 1) - values.get(0) == 4) {
                HashSet<Integer> valuesSet = new HashSet<>(values);
                // Если все значения уникальны
                if (valuesSet.size() == values.size()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isThreeOfAKind(List<Card> cards) {
        Map<Card.Value, Integer> valueCount = countValues(cards);

        return valueCount.containsValue(3);
    }

    private boolean isTwoPairs(List<Card> cards) {
        Map<Card.Value, Integer> valueCount = countValues(cards);
        long pairs = valueCount.values().stream().filter(count -> count == 2).count();

        return pairs == 2;
    }

    private boolean isPair(List<Card> cards) {
        Map<Card.Value, Integer> valueCount = countValues(cards);

        return valueCount.containsValue(2);
    }

    // Compare identical combinations
    private int compareRoyalFlush(PokerHand hand, PokerHand otherHand) {
        Card.Suit handSuit = hand.getCards().get(0).getSuit();
        Card.Suit otherHandSuit = otherHand.getCards().get(0).getSuit();

        return handSuit.compareTo(otherHandSuit);
    }

    private int compareStraightFlush(PokerHand hand, PokerHand otherHand) {
        return compareMaxCard(hand, otherHand);
    }

    private int compareFourOfAKind(PokerHand hand, PokerHand otherHand) {
        Card.Value handValue = getValueByCount(hand, 4);
        Card.Value otherHandValue = getValueByCount(otherHand, 4);

        return handValue.compareTo(otherHandValue);
    }

    private int compareFullHouse(PokerHand hand, PokerHand otherHand) {
        Card.Value handValue = getValueByCount(hand, 3);
        Card.Value otherHandValue = getValueByCount(otherHand, 3);

        return handValue.compareTo(otherHandValue);
    }

    private int compareFlush(PokerHand hand, PokerHand otherHand) {
        List<Card.Value> handValues = hand.getCards()
                .stream()
                .map(Card::getValue)
                .sorted(Comparator.reverseOrder())
                .toList();

        List<Card.Value> otherHandValues = otherHand.getCards()
                .stream()
                .map(Card::getValue)
                .sorted(Comparator.reverseOrder())
                .toList();

        for (int i = 0; i < handValues.size(); i++) {
            if (handValues.get(i).getNumericValue() > otherHandValues.get(i).getNumericValue()) {
                return 1;
            } else if (handValues.get(i).getNumericValue() < otherHandValues.get(i).getNumericValue()) {
                return -1;
            }
        }

        return 0;
    }

    private int compareStraight(PokerHand hand, PokerHand otherHand) {
        return compareMaxCard(hand, otherHand);
    }

    private int compareThreeOfAKind(PokerHand hand, PokerHand otherHand) {
        Card.Value handValue = getValueByCount(hand, 3);
        Card.Value otherHandValue = getValueByCount(otherHand, 3);

        return handValue.compareTo(otherHandValue);
    }

    private int compareTwoPairs(PokerHand hand, PokerHand otherHand) {
        List<Card.Value> handCardsValue = getDescValuesByCount(hand, 2);
        List<Card.Value> otherHandCardsValue = getDescValuesByCount(otherHand, 2);
        int comparison;

        for (int i = 0; i < handCardsValue.size(); i++) {
            comparison = handCardsValue.get(i).compareTo(otherHandCardsValue.get(i));
            if (comparison != 0)
                return comparison;
        }

        Card.Value lastHandCard = getValueByCount(hand, 1);
        Card.Value lastOtherHandCard = getValueByCount(otherHand, 1);

        return lastHandCard.compareTo(lastOtherHandCard);
    }

    private int comparePair(PokerHand hand, PokerHand otherHand) {
        Card.Value handPairValue = getValueByCount(hand, 2);
        Card.Value otherHandPairValue = getValueByCount(otherHand, 2);

        int comparison = handPairValue.compareTo(otherHandPairValue);

        // compare pair
        if (comparison != 0)
            return comparison;

        // compare other
        List<Card.Value> handCardsValue = getDescValuesByCount(hand, 1);
        List<Card.Value> otherHandCardsValue = getDescValuesByCount(otherHand, 1);

        for (int i = 0; i < handCardsValue.size(); i++) {
            comparison = handCardsValue.get(i).compareTo(otherHandCardsValue.get(i));
            if (comparison != 0)
                return comparison;
        }

        return 0;
    }

    private int compareHighCard(PokerHand hand, PokerHand otherHand) {
        List<Card.Value> handCardsValue = getDescValuesByCount(hand, 1);
        List<Card.Value> otherHandCardsValue = getDescValuesByCount(otherHand, 1);
        int comparison;

        for (int i = 0; i < handCardsValue.size(); i++) {
            comparison = handCardsValue.get(i).compareTo(otherHandCardsValue.get(i));
            if (comparison != 0)
                return comparison;
        }

        return 0;
    }

    // Help methods
    private Map<Card.Value, Integer> countValues(List<Card> cards) {
        Map<Card.Value, Integer> valueCount = new HashMap<>();

        for (Card card : cards) {
            valueCount.put(card.getValue(), valueCount.getOrDefault(card.getValue(), 0) + 1);
        }

        return valueCount;
    }

    private Map<Card.Suit, Integer> countSuits(List<Card> cards) {
        Map<Card.Suit, Integer> suitCount = new HashMap<>();

        for (Card card : cards) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }

        return suitCount;
    }

    private int compareMaxCard(PokerHand hand, PokerHand otherHand) {
        Card handHighCard = hand
                .getCards()
                .stream()
                .max(Comparator.comparing(Card::getValue))
                .orElseThrow(() -> new IllegalArgumentException("Hand is empty"));

        Card otherHandCard = otherHand
                .getCards()
                .stream()
                .max(Comparator.comparing(Card::getValue))
                .orElseThrow(() -> new IllegalArgumentException("Other hand is empty"));

        return handHighCard.getValue().compareTo(otherHandCard.getValue());
    }

    private Card.Value getValueByCount(PokerHand hand, Integer count) {
        Map<Card.Value, Integer> handValues = countValues(hand.getCards());

        return handValues
                .entrySet()
                .stream()
                .filter(x -> x.getValue().equals(count))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The combination does not contain" + count + "identical cards"));
    }

    private List<Card.Value> getDescValuesByCount(PokerHand hand, Integer count) {
        Map<Card.Value, Integer> handValues = countValues(hand.getCards());

        return handValues
                .entrySet()
                .stream()
                .filter(x -> x.getValue().equals(count))
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();
    }
}