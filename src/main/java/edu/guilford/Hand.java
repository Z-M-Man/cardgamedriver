package edu.guilford;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;

    /**
     * Assigns an empty list to a Hand object.
     */
    public Hand() {
        hand = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public void reset() {
        hand.clear();
    }

    public int size() {
        return hand.size();
    }

    public Card getCard(int index) {
        return hand.get(index);
    }

    /**
     * Calculates a total for the hand according to the rules of Blackjack. Number
     * cards add their face value.
     * Face cards add 10. Aces count as either 1 or 11, whichever provides the
     * higher score without exceeding 21.
     * 
     * @return the total value of the hand, according to the rules of Blackjack.
     */
    public int getTotalValue() {
        int value = 0;
        int aces = 0;
        for (Card card : hand) {
            switch (card.getRank()) {
                case TWO:
                    value += 2;
                    break;
                case THREE:
                    value += 3;
                    break;
                case FOUR:
                    value += 4;
                    break;
                case FIVE:
                    value += 5;
                    break;
                case SIX:
                    value += 6;
                    break;
                case SEVEN:
                    value += 7;
                    break;
                case EIGHT:
                    value += 8;
                    break;
                case NINE:
                    value += 9;
                    break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING:
                    value += 10;
                    break;
                case ACE:
                    aces++;
                    break;
            }
        }
        for (int i = 0; i < aces; i++) {
            if (value + 11 <= 21) {
                value += 11;
            } else {
                value += 1;
            }
        }
        return value;
    }

    // Override toString method
    public String toString() {
        String handString = "";
        for (Card card : hand) {
            handString += card.toString() + "\n";
        }
        return handString;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

}
