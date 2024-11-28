import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class PokerHandTest {

    // Проверки на правильное определение комбинации
    @ParameterizedTest
    @ValueSource(strings = {
            "TH JH QH KH AH",
            "TD JD QD KD AD",
            "TC JC QC KC AC",
            "TS JS QS KS AS"
    })
    public void testRoyalFlush(String hand) {
        PokerHand royalFlush = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.ROYAL_FLUSH, royalFlush.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "9C TC JC QC KC",
            "4C 5C 6C 7C 8C",
            "2S 3S 4S 5S 6S",
            "2D 3D 4D 5D 6D"
    })
    public void testStraightFlush(String hand) {
        PokerHand straightFlush = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.STRAIGHT_FLUSH, straightFlush.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "4C 5C 4C 4C 4C",
            "4C 3C 3D 3C 3D",
            "2H 2S 2H 2S AS",
            "AD AD AD AD 6D"
    })
    public void testFourOfAKind(String hand) {
        PokerHand fourOfAKind = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.FOUR_OF_A_KIND, fourOfAKind.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "3D 3H 3S 6D 6H",
            "5C 5S 5D 9H 9S",
            "7H 7D 7S 3C 3D",
            "8S 8H 8C 6S 6D"
    })
    public void testFullHouse(String hand) {
        PokerHand fullHouse = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.FULL_HOUSE, fullHouse.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2H 4H 5H 7H KH",
            "3D 5D 7D 9D AD",
            "6S 7S 8S 9S AS",
            "TH JH KH KH AH"
    })
    public void testFlush(String hand) {
        PokerHand flush = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.FLUSH, flush.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "4D 5S 6C 7H 8D",
            "2D 3S 4C 5H 6D",
            "9D TS JC QS KS",
            "3C 4S 5H 6C 7D"
    })
    public void testStraight(String hand) {
        PokerHand straight = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.STRAIGHT, straight.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "5D 5S 5C KH QD",
            "3H 3S 3D 9C 6D",
            "7H 7D 7C TS 2D",
            "9D 9H 9S JD KS"
    })
    public void testThreeOfAKind(String hand) {
        PokerHand threeOfAKind = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.THREE_OF_A_KIND, threeOfAKind.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "3D 3H 6S 6C KD",
            "7S 7C 2D 2H 9C",
            "9H 9D 3C 3S KS",
            "8D 8C 2H 2S TS"
    })
    public void testTwoPairs(String hand) {
        PokerHand twoPairs = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.TWO_PAIRS, twoPairs.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2D 2H JD QS KH",
            "3C 3D 5H 6S 9C",
            "7H 7S JC KS TC",
            "9D 9H TS JS KD"
    })
    public void testPair(String hand) {
        PokerHand pair = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.PAIR, pair.getRank());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2D AH JD QS KH",
            "3H 5D 6C TS KC",
            "7S 8H 9D 5C JH",
            "2S 5H 6D 8C KS"
    })
    public void testHighCard(String hand) {
        PokerHand highCard = new PokerHand(hand);
        assertEquals(PokerHand.HandRank.HIGH_CARD, highCard.getRank());
    }

    // Проверки на сравнение одинаковых комбинаций
    @Test
    public void testRoyalFlushsComparison() {
        PokerHand highCardHand = new PokerHand("TS JS QS KS AS");
        PokerHand lowCardHand = new PokerHand("TH JH QH KH AH");

        assertEquals(PokerHand.HandRank.ROYAL_FLUSH, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.ROYAL_FLUSH, lowCardHand.getRank());

        // Сделал сравнение по мастям в royal flush при одинаковых комбинациях
        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testStraightFlushComparison() {
        PokerHand highCardHand = new PokerHand("7S 8S 9S TS JS");
        PokerHand lowCardHand = new PokerHand("5C 6C 7C 8C 9C");

        assertEquals(PokerHand.HandRank.STRAIGHT_FLUSH, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.STRAIGHT_FLUSH, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testFourOfAKindComparison() {
        PokerHand highCardHand = new PokerHand("6S 6S 6S 6C 9C");
        PokerHand lowCardHand = new PokerHand("4S 4S 4S 4S JS");

        assertEquals(PokerHand.HandRank.FOUR_OF_A_KIND, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.FOUR_OF_A_KIND, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testFullHouseComparison() {
        PokerHand highCardHand = new PokerHand("9S 9C 9S 5C 5C");
        PokerHand lowCardHand = new PokerHand("8S 8S 8S 4C 4S");

        assertEquals(PokerHand.HandRank.FULL_HOUSE, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.FULL_HOUSE, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testFlushComparison() {
        PokerHand highCardHand = new PokerHand("9C 2C AC 7C 5C");
        PokerHand lowCardHand = new PokerHand("9S 2S TS 4S 5S");

        assertEquals(PokerHand.HandRank.FLUSH, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.FLUSH, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testStraightComparison() {
        PokerHand highCardHand = new PokerHand("5C 6S 7C 8S 9C");
        PokerHand lowCardHand = new PokerHand("2S 3C 4S 5C 6S");

        assertEquals(PokerHand.HandRank.STRAIGHT, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.STRAIGHT, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testThreeOfAKindComparison() {
        PokerHand highCardHand = new PokerHand("5C 5S 5C 8S 9C");
        PokerHand lowCardHand = new PokerHand("3S 3C 3S 5C 6S");

        assertEquals(PokerHand.HandRank.THREE_OF_A_KIND, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.THREE_OF_A_KIND, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testTwoPairsComparison() {
        PokerHand highCardHand = new PokerHand("5C 5S 8C 8S 9C");
        PokerHand lowCardHand = new PokerHand("3S 3C 8S 8C 6S");

        assertEquals(PokerHand.HandRank.TWO_PAIRS, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.TWO_PAIRS, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testPairsComparison() {
        PokerHand highCardHand = new PokerHand("5C 5S 2C 8S 9C");
        PokerHand lowCardHand = new PokerHand("3S 3C 4S 8C 6S");

        assertEquals(PokerHand.HandRank.PAIR, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.PAIR, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testHighCardComparison() {
        PokerHand highCardHand = new PokerHand("AC 5S 2C 8S 9C");
        PokerHand lowCardHand = new PokerHand("TS 3C 4S 8C 6S");

        assertEquals(PokerHand.HandRank.HIGH_CARD, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.HIGH_CARD, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    // Проверки на сравнение разных комбинаций
    @Test
    public void testRoyalFlushVsStraightFlush() {
        PokerHand highCardHand = new PokerHand("TS JS QS KS AS");
        PokerHand lowCardHand = new PokerHand("9H TH JH QH KH");

        assertEquals(PokerHand.HandRank.ROYAL_FLUSH, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.STRAIGHT_FLUSH, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testStraightFlushVsFourOfAKind() {
        PokerHand highCardHand = new PokerHand("9H TH JH QH KH");
        PokerHand lowCardHand = new PokerHand("4S 4S 4S 4S JS");

        assertEquals(PokerHand.HandRank.STRAIGHT_FLUSH, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.FOUR_OF_A_KIND, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testFourOfAKindVsFullHouse() {
        PokerHand highCardHand = new PokerHand("4S 4S 4S 4S JS");
        PokerHand lowCardHand = new PokerHand("9S 9C 9S 5C 5C");

        assertEquals(PokerHand.HandRank.FOUR_OF_A_KIND, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.FULL_HOUSE, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testFullHouseVsFlush() {
        PokerHand highCardHand = new PokerHand("9S 9C 9S 5C 5C");
        PokerHand lowCardHand = new PokerHand("9C 2C AC 7C 5C");

        assertEquals(PokerHand.HandRank.FULL_HOUSE, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.FLUSH, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testFlushVsStraight() {
        PokerHand highCardHand = new PokerHand("9C 2C AC 7C 5C");
        PokerHand lowCardHand = new PokerHand("2S 3C 4S 5C 6S");

        assertEquals(PokerHand.HandRank.FLUSH, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.STRAIGHT, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testStraightVsThreeOfAKind() {
        PokerHand highCardHand = new PokerHand("2S 3C 4S 5C 6S");
        PokerHand lowCardHand = new PokerHand("5C 5S 5C 8S 9C");

        assertEquals(PokerHand.HandRank.STRAIGHT, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.THREE_OF_A_KIND, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testThreeOfAKindVsTwoPairs() {
        PokerHand highCardHand = new PokerHand("5C 5S 5C 8S 9C");
        PokerHand lowCardHand = new PokerHand("5C 5S 8C 8S 9C");

        assertEquals(PokerHand.HandRank.THREE_OF_A_KIND, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.TWO_PAIRS, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testTwoPairsVsPair() {
        PokerHand highCardHand = new PokerHand("5C 5S 8C 8S 9C");
        PokerHand lowCardHand = new PokerHand("5C 5S 2C 8S 9C");

        assertEquals(PokerHand.HandRank.TWO_PAIRS, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.PAIR, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }

    @Test
    public void testPairVsHighCard() {
        PokerHand highCardHand = new PokerHand("5C 5S 2C 8S 9C");
        PokerHand lowCardHand = new PokerHand("2C 3S 4C 6C 8S");

        assertEquals(PokerHand.HandRank.PAIR, highCardHand.getRank());
        assertEquals(PokerHand.HandRank.HIGH_CARD, lowCardHand.getRank());

        assertTrue(highCardHand.compareTo(lowCardHand) > 0);
    }


    // Проверки на некорректные данные
    @ParameterizedTest
    @ValueSource(strings = {
            "2H 3D 4C 5S 6H 7D",
            "2H 3D 4C 5S",
            "2H 3D 4C",
            "2H 3D",
            "2H",
            ""
    })
    public void testInvalidCountCards(String hand) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new PokerHand(hand);
        });
        assertEquals("Invalid number of cards", exception.getMessage());
    }

    @Test
    public void testInvalidCardValueFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new PokerHand("2H XH 2H 2H 2H");
        });
        assertEquals("Invalid card value: X", exception.getMessage());
    }

    @Test
    public void testInvalidCardSuitFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new PokerHand("2H 2H 2Y 2H 2H");
        });
        assertEquals("Invalid card suit: Y", exception.getMessage());
    }
}
