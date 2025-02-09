package edu.guilford;

/**
 * The driver program for this card game project which tests all classes and
 * methods. The evaluation of hands and determination of a winner for Lamarckian
 * Poker is not implemented yet.
 */
public class CardGameDriver {
    /**
     * Main method to run the card game driver program. Runs 10000 games of Blackjack and displays the 
     * number of player wins, dealer wins, and ties/pushes. Runs 1 game of Lamarckian Poker, displaying
     * the initial and final hands.
     * 
     * @param args
     */
    public static void main(String[] args) {
        final int NGAMES = 10000;
        int dealerWins = 0;
        int playerWins = 0;
        Blackjack game = new Blackjack();
        game.deal();
        // System.out.println(game);
        int iGame = 0;
        while (iGame < NGAMES) {
            game.deal();
            if (game.getPlayerHand().getTotalValue() == 21) {
                playerWins++;
            } else if (game.getDealerHand().getTotalValue() == 21) {
                dealerWins++;
            } else {
                boolean playerResult = game.playerTurn();
                boolean dealerResult = game.dealerTurn();
                if (!playerResult) {
                    dealerWins++;
                } else if (!dealerResult) {
                    playerWins++;
                } else if (game.getPlayerHand().getTotalValue() < game.getDealerHand().getTotalValue()) {
                    dealerWins++;
                } else if (game.getPlayerHand().getTotalValue() > game.getDealerHand().getTotalValue()) {
                    playerWins++;
                }
            }
            if (game.getDeck().size() < 10) {
                game.reset(true);
            }

            iGame++;
        }
        System.out.println("Dealer wins: " + dealerWins);
        System.out.println("Player wins: " + playerWins);
        System.out.println("Pushes: " + (NGAMES - dealerWins - playerWins));

        LamarckianPoker lmpGame = new LamarckianPoker();
        lmpGame.deal();
        System.out.println("\nInitial Lamarckian hands\n" + lmpGame);

        boolean gameDone = false;
        while (!gameDone) {
            // System.out.println(lmpGame);
            gameDone = !lmpGame.turn();
        }

        System.out.println("Final Lamarckian hands\n" + lmpGame);

    }
}