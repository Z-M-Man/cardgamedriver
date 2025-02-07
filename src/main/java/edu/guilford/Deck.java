package edu.guilford;

import java.util.ArrayList;
import java.util.Random;

/**
 * Holds a sequence of Card objects that can be used in card games.
 */
public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();
    private Random rand = new Random();

    /**
     * Constructs a standard 52-card deck using the build() method.
     */
    public Deck() {
        build();
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
    
    public void clear() {
        deck.clear();
    }

    /**
     * Adds all 52 Card objects in a standard deck to an arrayList in the order of suit, sorted by rank. 
     */
    public void build() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
    }

    /**
     * Shuffles the deck into a random arrangement. This will work on any deck with more than 0 Card objects.
     */
    public void shuffle() {
        ArrayList<Card> tempDeck = new ArrayList<Card>();
        while (deck.size() > 0) {
            int loc = rand.nextInt(deck.size());
            tempDeck.add(deck.get(loc));
            deck.remove(loc);
        }
        deck = tempDeck;
    }

    /**
     * Returns the Card object at the given index and removes it from the deck.
     * 
     * @param i the index of the Card object to be returned.
     * @return the Card object at the given index.
     */
    public Card pick(int i) {
        Card picked = deck.remove(i);
        return picked;
    }

    /**
     * Returns the Card object at the top of the deck (at index 0) and removes it from the deck.
     * 
     * @return the Card at the top of the deck, at index 0.
     */
    public Card deal() {
        return deck.remove(0);
    }

    public int size() {
        return deck.size();
    }

    /**
     * Returns a well-formatted string representation of the Deck object.
     * 
     * @return a well-formatted string representation of the deck.
     */
    public String toString() {
        String deckString = "";
        for (Card card : deck) {
            deckString += card.toString() + "\n";
        }
        return deckString;
    }
}
