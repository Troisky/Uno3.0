package uno;

public class UnoDeck {
    private UnoCard[] cards;
    private int cardsInDeck;

    public UnoDeck() {
        cards = new UnoCard[108];
    }

    public void reset() {
        UnoCard.Color[] colors = UnoCard.Color.values();
        cardsInDeck = 0;
        for (int i = 0; i < colors.length - 1; i++) {
            UnoCard.Color color = colors[i];
            cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(0));
            for (int j = 1; j < 10; j++) {
                cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(j));
                cards[cardsInDeck++] = new UnoCard(color, UnoCard.Value.getValue(j));
            }
        }
        UnoCard.Value[] values = new UnoCard.Value[]{UnoCard.Value.DrawTwo, UnoCard.Value.Skip, UnoCard.Value.Reverse};
        for (UnoCard.Value value : values) {
            cards[cardsInDeck++] = new UnoCard(color, value);
            cards[cardsInDeck++] = new UnoCard(color, value);
        }
        UnoCard.Value[] values = new UnoCard.Value[]{UnoCard.Value.Wild, UnoCard.Value.Wild_Four};
        for(UnoCard.Value value : values){
            for(int i = 0; i < 4; i++){
                cards[cardsInDeck++] = new UnoCard(UnoCard.Color.Wild, value);
            }
        }
    }
}
