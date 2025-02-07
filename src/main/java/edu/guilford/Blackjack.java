package edu.guilford;

/**
 * Implements a simple version of player and dealer actions in the Blackjack game. At the start of the game, the
 * player and dealer are each dealt two cards. A player turn is when the player draws cards until the hand total
 * is greater than or equal to 16. The player busts if the total is over 21. A dealer turn is when the dealer draws
 * cards until the hand total is greater than or equal to 17. The dealer busts if the total is over 21. 
 */
public class Blackjack {
    private Hand playerHand;
    private Hand dealerHand;
    private Deck deck;

    /**
     * Uses the reset() method to prepare the Deck object for a new game.
     */
    public Blackjack() {
        reset(true);
    }

    

    public Hand getPlayerHand() {
        return playerHand;
    }


    public Hand getDealerHand() {
        return dealerHand;
    }


    public Deck getDeck() {
        return deck;
    }

    /**
     * Instantiates a new Deck object and applies its shuffle() method.
     * 
     * @param newDeck whether a new Deck object should be instantiated or not.
     */
    public void reset(boolean newDeck) {
        if (newDeck) {
            deck = new Deck();
            deck.shuffle();
        }
    }

    /**
     * Instantiates two Hand objects, assigning them the proper player and dealer attributes. It then adds two Card
     * objects from the Deck object to each Hand object.
     */
    public void deal() {
        playerHand = new Hand();
        dealerHand = new Hand();
        playerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
        playerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
    }

    /**
     * Applies the following rules: a player draws cards until the hand total is greater than or equal to 16. The 
     * player busts if the total is over 21. 
     * 
     * @return true if the value of the player's hand is less than or equal to 21 and false otherwise.
     */
    public boolean playerTurn() {
        while (playerHand.getTotalValue() < 16) {
            playerHand.addCard(deck.deal());
        }
        return playerHand.getTotalValue() <= 21;

    }

    /**
     * Applies the following rules: the dealer draws cards until the hand total is greater than or equal to 17. The 
     * dealer busts if the total is over 21. 
     * 
     * @return true if the value of the dealer's hand is less than or equal to 21 and false otherwise.
     */
    public boolean dealerTurn() {
        while (dealerHand.getTotalValue() < 17) {
            dealerHand.addCard(deck.deal());
        }
        return dealerHand.getTotalValue() <= 21;
    }

    // Override toString
    public String toString() {
        String result = "Player's Hand:\n";
        for (int i = 0; i < playerHand.size(); i++) {
            result += playerHand.getCard(i) + "\n";
        }
        result += "Player's Total: " + playerHand.getTotalValue() + "\n\n";
        result += "Dealer's Hand:\n";
        for (int i = 0; i < dealerHand.size(); i++) {
            result += dealerHand.getCard(i) + "\n";
        }
        result += "Dealer's Total: " + dealerHand.getTotalValue() + "\n\n";
        return result;
    }

}
