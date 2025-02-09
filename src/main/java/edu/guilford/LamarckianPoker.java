package edu.guilford;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implements the start of a modified Lamarckian poker game and the playing of a
 * turn. Each player gets 4 cards, plus a pool of 4 cards. The players choose a 
 * sacrificial card, and the higher card goes first. All cards of the same
 * suit or rank are taken from the pool and the sacrifical card is added to the
 * discard. Unchosen cards are discarded and a new pool is drawn. Once all players 
 * have at least 7 cards, the game ends.
 */
public class LamarckianPoker {
    private Hand player1Hand;
    private Hand player2Hand;
    private Hand pool;
    private Deck discard;
    private Deck deck;
    private Random rand = new Random();
    private int iTurn;

    /**
     * Creates a new game of Lamarckian poker using the reset() method.
     */
    public LamarckianPoker() {
        reset(true);
    }

    public Hand getPlayer1Hand() {
        return player1Hand;
    }

    public Hand getPlayer2Hand() {
        return player2Hand;
    }

    public Hand getPool() {
        return pool;
    }

    /**
     * Creates a game with a new shuffled deck and empty discard pile.
     * 
     * @param newDeck whether the reset method should run or not.
     */
    public void reset(boolean newDeck) {
        if (newDeck) {
            deck = new Deck();
            discard = new Deck();
            discard.clear();
            deck.shuffle();
        }
        iTurn = 0;
    }

    /**
     * Instantiates new Hand objects for each player and deals 4 Card objects from
     * the deck to each player.
     */
    public void deal() {
        player1Hand = new Hand();
        player2Hand = new Hand();
        for (int iCard = 0; iCard < 4; iCard++) {
            player1Hand.addCard(deck.deal());
            player2Hand.addCard(deck.deal());
        }
    }

    /**
     * Creates a Hand object to represent the pool and deal 4 Card objects from the
     * deck to that object.
     */
    public void makePool() {
        pool = new Hand();
        for (int iCard = 0; iCard < 4; iCard++) {
            pool.addCard(deck.deal());
        }
        // System.out.println("Deck size: " + deck.size());
    }

    /**
     * Chooses a random card from each player's hand to be the "sacrificial" card.
     * The higher card goes first, adding all cards of the same suit or rank to 
     * that player's hand, removing it from the pool, and adding the sacrificial 
     * card and unchosen cards to the discard pile. If less than four Card objects 
     * are available to create a pool, the cards from the discard are readded to 
     * the deck.
     * 
     * @return true if the size of both players' hands is less than 7 and false
     *         otherwise.
     */
    public boolean turn() {
        // if (player1Hand.size() < 7 || player2Hand.size() < 7) {
        // The or operator in this condition should be changed to an and operator.
        if (player1Hand.size() < 7 && player2Hand.size() < 7) {
            makePool();
            // System.out.println("Turn " + iTurn + "\n" + pool);
            Card player1Card = player1Hand.getCard(rand.nextInt(player1Hand.size()));
            Card player2Card = player2Hand.getCard(rand.nextInt(player2Hand.size()));
            Hand firstHand, secondHand;
            Card firstCard, secondCard;
            if (player1Card.getRank().ordinal() > player2Card.getRank().ordinal()) {
                firstHand = player1Hand;
                secondHand = player2Hand;
                firstCard = player1Card;
                secondCard = player2Card;
            } else if (player1Card.getRank().ordinal() < player2Card.getRank().ordinal()) {
                firstHand = player2Hand;
                secondHand = player1Hand;
                firstCard = player2Card;
                secondCard = player1Card;
            } else {
                if (player1Card.getSuit().ordinal() > player2Card.getSuit().ordinal()) {
                    firstHand = player1Hand;
                    secondHand = player2Hand;
                    firstCard = player1Card;
                    secondCard = player2Card;
                } else {
                    firstHand = player2Hand;
                    secondHand = player1Hand;
                    firstCard = player2Card;
                    secondCard = player1Card;
                }

            }

            ArrayList<Card> poolRemove = new ArrayList<Card>();

            for (Card poolCard : pool.getHand()) {
                if (firstCard.getRank().ordinal() == poolCard.getRank().ordinal() ||
                        firstCard.getSuit().ordinal() == poolCard.getSuit().ordinal()) {
                    firstHand.addCard(poolCard);
                    poolRemove.add(poolCard);
                }
            }
            // Remove cards from pool
            for (Card poolCard : poolRemove) {
                pool.removeCard(poolCard);
            }
            poolRemove.clear();
            pool.addCard(firstCard);
            firstHand.removeCard(firstCard);
            for (Card poolCard : pool.getHand()) {
                if (secondCard.getRank().ordinal() == poolCard.getRank().ordinal() ||
                        secondCard.getSuit().ordinal() == poolCard.getSuit().ordinal()) {
                    secondHand.addCard(poolCard);
                    poolRemove.add(poolCard);
                }
            }
            for (Card poolCard : poolRemove) {
                pool.removeCard(poolCard);
            }
            pool.addCard(secondCard);
            secondHand.removeCard(secondCard);
            for (Card poolCard : pool.getHand()) {
                discard.getDeck().add(poolCard);
            }
            pool.getHand().clear();
            // System.out.println("Discard\n" + discard.size());
            if (deck.size() < 4) {
                for (Card card : discard.getDeck()) {
                    deck.getDeck().add(card);
                }
                discard.clear();
                // System.out.println("Discard\n" + discard.size());
            }
            iTurn++;

            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "\nPlayer 1: \n" + player1Hand + "\nPlayer 2: \n" + player2Hand + "\nPool: " + pool + "\n";
    }
}
