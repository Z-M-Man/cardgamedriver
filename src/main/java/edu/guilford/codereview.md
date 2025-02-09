## Documentation

- Although the initial project did not have proper Javadoc comments for all classes and methods, it is now 
satisfactory in its current state.

## Code Structure

- The `LamarckianPoker` class does not properly fulfill the specification for the `turn()` method. The specification calls for it to check if both player `Hand`s have at least seven `Card`s, but the condition for the `if` statement uses the `or` operator `||`, returning false if just one player `Hand` has at least seven `Card`s. This can be fixed by replacing it with the `and` operator `&&` so that both `Hand`s are checked.
- All other classes and methods fulfill their specifications properly.  
- The `compareTo()` method in the `Card` class has an unnecessary `TODO` comment that should be removed.
- All classes are consistently formatted.
- There is no debugging code that inadvertently runs in production. However, in `LamarckianPoker`, the pool is printed as part of the `toString()` method. In the driver program, the `LamarckianPoker` object is printed at the end, when the pool is empty, so the string `Pool: null` prints. It may be worth deleting `+ "\nPool: " + pool + "\n"` from `toString()` if this is a concern.
- In `Blackjack`, the `playerTurn()` and `dealerTurn()` methods are very similar and their code can be combined:
    ```
    public boolean blackjackTurn(Hand hand, int drawLimit) {
        while (hand.getTotalValue() < drawLimit) {
            hand.addCard(deck.deal());
        }
        return hand.getTotalValue() <= 21;
    }
    ```
    This method can then be called in `playerTurn()` using `return blackjackTurn(playerHand, 16);` and in `dealerTurn()` using `return blackjackTurn(dealerHand, 17);`. Alternatively, instead of integers, constants can be used.
- `Blackjack`, `Hand`, and `LamarckianPoker` use integers that could be redefined as constants.
    - `Blackjack`: `private static PLAYER_DRAW_TO = 16;`, `private static DEALER_DRAW_TO = 17;`, `private static BLACKJACK_MAX_POINTS = 21;`
    - `Hand`: `private static ACES_MAX = 11;`, `private static ACES_MIN = 1;`, `private static BLACKJACK_MAX_POINTS = 21;`
    - `LamarckianPoker`: `private static INIT_DEAL = 4;`, `private static POOL_SIZE = 4;`, `private static MAX_HAND_SIZE = 7;`, `private static MIN_DECK_SIZE = 4;`
- The `turn()` method in `LamarckianPoker` is quite complex and its functions could be split among several helper methods:
    ```
    private Card[] selectSacrificialCards() {
        Card player1Card = player1Hand.getCard(rand.nextInt(player1Hand.size()));
        Card player2Card = player2Hand.getCard(rand.nextInt(player2Hand.size()));
        return new Card[]{player1Card, player2Card};
    }
    ```
    ```
    private Hand[] findTurnOrder(Card player1Card, Card player2Card) {
        if (player1Card.getRank().ordinal() > player2Card.getRank().ordinal()) {
            return new Hand[]{player1Hand, player2Hand};
        } 
        else if (player1Card.getRank().ordinal() < player2Card.getRank().ordinal()) {
            return new Hand[]{player2Hand, player1Hand};
        } 
        else {
            return (player1Card.getSuit().ordinal() > player2Card.getSuit().ordinal()) 
                ? new Hand[]{player1Hand, player2Hand} 
                : new Hand[]{player2Hand, player1Hand};
        }
    }
    ```
    ```
    private void takeCards(Hand playerHand, Card sacrificialCard) {
        ArrayList<Card> poolRemove = new ArrayList<>();
    
        for (Card poolCard : pool.getHand()) {
            if (poolCard.getRank() == sacrificialCard.getRank() || 
                poolCard.getSuit() == sacrificialCard.getSuit()) {
                playerHand.addCard(poolCard);
                poolRemove.add(poolCard);
            }
        }

        for (Card poolCard : poolRemove) {
            pool.removeCard(poolCard);
        }
    }
    ```
    ```
    private void discardPool() {
        for (Card poolCard : pool.getHand()) {
            discard.getDeck().add(poolCard);
        }
        pool.getHand().clear();
    }
    ```
    ```
    private void refillDeck() {
        if (deck.size() < MIN_DECK_SIZE) {
            deck.getDeck().addAll(discard.getDeck());
            discard.clear();
        }
    }
    ```
    ```
    public boolean turn() {
        if (player1Hand.size() < 7 && player2Hand.size() < 7) {
            makePool();

            // Select sacrificial cards
            Card[] sacrificialCards = selectSacrificialCards();
            Card player1Card = sacrificialCards[0];
            Card player2Card = sacrificialCards[1];

            // Determine which player goes first
            Hand[] turnOrder = findTurnOrder(player1Card, player2Card);
            Hand firstHand = turnOrder[0];
            Hand secondHand = turnOrder[1];
            Card firstCard = (firstHand == player1Hand) ? player1Card : player2Card;
            Card secondCard = (secondHand == player1Hand) ? player1Card : player2Card;

            // Collect matching cards for both players
            takeCards(firstHand, firstCard);
            pool.addCard(firstCard);
            firstHand.removeCard(firstCard);

            takeCards(secondHand, secondCard);
            pool.addCard(secondCard);
            secondHand.removeCard(secondCard);

            // Discard remaining cards and check if deck needs refilling
            discardPool();
            refillDeck();

            iTurn++;
            return true;
        }
        return false;
    }
    ```

## Variables
- All variables have reasonable types and identifiers.
- Each variable has a single purpose in its scope