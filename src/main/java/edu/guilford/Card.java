package edu.guilford;

import java.util.Random;

/**
 * Represents a standard playing card with its suit and rank.
 */
public class Card implements Comparable<Card>{
    // enum for the suits
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    // enum for the ranks
    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN,
        KING
    }

    // instance variables
    private Suit suit;
    private Rank rank;

    /**
     * Produces a Card object of a specified suit and rank.
     * 
     * @param suit the Suit constant that will be assigned as the Card object's suit.
     * @param rank the Rank constant that will be assigned as the Card object's rank.
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Produces a Card object with a random suit and rank.
     */
    public Card() {
        // random Card
        Random rand = new Random();
        int suit = rand.nextInt(Suit.values().length);
        int rank = rand.nextInt(Rank.values().length);
        this.suit = Suit.values()[suit];
        this.rank = Rank.values()[rank];
    }

    // getters
    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    // toString

    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * Compares this Card object to another Card object. Rank is compared first, then suit. 
     * 
     * @param otherCard the Card being compared to this Card
     * @return 1 if this Card's rank is higher, -1 if the other Card's rank is higher. If the ranks are the same,
     * returns 1 if this Card's suit is higher, -1 if the other Card's suit is higher. Returns 0 if both Cards are
     * the same.
     */
    @Override
    public int compareTo(Card otherCard) {
        // TODO Auto-generated method stub
        if (this.rank.ordinal() > otherCard.rank.ordinal()) {
            return 1;
        }
        else if (this.rank.ordinal() < otherCard.rank.ordinal()) {
            return -1;
        }
        else {
            if (this.suit.ordinal() > otherCard.suit.ordinal()) {
                return 1;
            }
            else if (this.suit.ordinal() < otherCard.suit.ordinal()) {
                return -1;
            }
        }

        return 0;
    }

    
}