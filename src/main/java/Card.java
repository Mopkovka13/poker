public class Card {
    private final Value value;
    private final Suit suit;

    public Card(String card) {
        validateCard(card);

        this.value = Value.fromSymbol(card.charAt(0));
        this.suit = Suit.fromSymbol(card.charAt(1));
    }

    private void validateCard(String card) {
        if (card.length() != 2) {
            throw new IllegalArgumentException("Card must be 2 characters");
        }
        if (Value.fromSymbol(card.charAt(0)) == null) {
            throw new IllegalArgumentException("Invalid card value: " + card.charAt(0));
        }
        if (Suit.fromSymbol(card.charAt(1)) == null) {
            throw new IllegalArgumentException("Invalid card suit: " + card.charAt(1));
        }
    }

    @Override
    public String toString() {
        return String.format("%c%c", value.getSymbol(), suit.getSymbol());
    }

    public Value getValue() {
        return this.value;
    }

    public Suit getSuit() {
        return this.suit;
    }

    protected enum Value {
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9'),
        TEN('T'),
        JACK('J'),
        QUEEN('Q'),
        KING('K'),
        ACE('A');

        private final char symbol;

        Value(char symbol) {
            this.symbol = symbol;
        }

        public static Value fromSymbol(char symbol) {
            for (Value value : values()) {
                if (value.symbol == symbol) {
                    return value;
                }
            }
            return null;
        }

        public char getSymbol () {
            return symbol;
        }

        // Возвращает порядковый номер в последовательности
        public int getNumericValue() {
            return this.ordinal();
        }
    }

    protected enum Suit {
        CLUBS('C'),
        DIAMONDS('D'),
        HEARTS('H'),
        SPADES('S');

        private final char symbol;

        Suit(char symbol) {
            this.symbol = symbol;
        }

        public static Suit fromSymbol(char symbol) {
            for (Suit suit : values()) {
                if (suit.symbol == symbol) {
                    return suit;
                }
            }
            return null;
        }

        public char getSymbol () {
            return symbol;
        }
    }
}
