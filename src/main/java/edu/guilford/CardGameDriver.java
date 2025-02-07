package edu.guilford;

/**
 * The driver program for this card game project. It performs tests on both the Blackjack and LamarckianPoker classes,
 * implicitly testing all other classes. 10000 games of Blackjack are run by instantiating a Blackjack object and 
 * using its methods to deal cards, execute player and dealer turns, and evaluate the result. It records for each 
 * game whether the player won, the dealer won, or there was a tie/push. During the testing, when a deck has less than
 * 10 cards, a new deck is used for the game. The number of dealer wins, player wins, and pushes is displayed. 
 * One simulated Lamarckian poker game is run. It instantiates a LamarckianPoker object and deals hands to the two 
 * players. It then has the object execute game turns until the turn() method indicates that the game is complete and
 * the hands are ready to be evaluated. The evaluation of hands and determination of a winner is not implemented yet.
 */
public class CardGameDriver {
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
        //    System.out.println(lmpGame);
            gameDone = !lmpGame.turn();
        }
  
        System.out.println("Final Lamarckian hands\n" + lmpGame);   

    }
}