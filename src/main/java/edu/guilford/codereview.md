# Reviewing Implementation

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
- Each variable has a single purpose in its scope.
- Type consistency is ensured for each variable.
- The variable `iTurn` and its references in `LamarckianPoker` can be removed because it serves no function in the code.

## Arithmetic Operations
- No potential concerns about operations apply to this project.

## Loops and Conditional Statements
- There are no errors in nesting or conditional statements other than the concerns already listed above.
- There are no concerns in the order of tested conditions in `if`-`else` chains or `switch` statements. For the Blackjack part of the driver program, natural Blackjacks are tested for first. This may not be the most common scenario, but it makes sense given the rules of the game. The `switch` statement in `Hand` is ordered numerically, but that can potentially improve readability. If testing the most common case first is a must, the cases for the face cards should be moved to the top of the statement.
- All possible cases are accounted for in these loops, with the exception of a bug in the driver program that will be individually explored below.
- All loops have proper initialization statements and termination conditions.
- There are no statements that should be moved outside of a loop or conditional statement.

## General Practices
- All return values are assigned and returned correctly.
- All statements operate on the correct data.
- The code returns reasonable results for a wide range of cases.
- The driver program prints the results of the Blackjack games without context. It may be helpful to add a print statement to the start to indicate what the results mean.

# Bug Tracking and Fixing

## The Error

- Occasionally, the output of the driver program will include an error:
    ```
    Exception in thread "main" java.lang.IllegalArgumentException: bound must be positive
        at java.base/java.util.Random.nextInt(Random.java:322)
        at edu.guilford.LamarckianPoker.turn(LamarckianPoker.java:101)
        at edu.guilford.CardGameDriver.main(CardGameDriver.java:60)
    ```
- This error points to lines 99 and 100 of `LamarckianPoker`, which are
    ```
    Card player1Card = player1Hand.getCard(rand.nextInt(player1Hand.size()));
    Card player2Card = player2Hand.getCard(rand.nextInt(player2Hand.size()));
    ```
- It occurs if `size()` returns 0, as `nextInt()` of the `Random` class cannot return a random integer between 0 and 0. This means that at least one player's hand has zero cards when these statements run. 
- For a player to get 0 cards in their hand, their chosen sacrifical card has to consistently not gain them any cards from the pool. As the sacrificial card is discarded, their hand effectively shrinks by one card. As the scenario in which a player repeatedly fails to gain cards from the pool until they lose all cards is unlikely, this error only happens occasionally. 

## The Fix
- A potential solution for this error is to modify the `turn()` method so that it checks if at least one of the players has zero cards in their deck before choosing a sacrificial card for them. If that is the case, it will end the game. 
    ```
    public boolean turn() {
        if (player1Hand.size() < 7 && player2Hand.size() < 7) {
            makePool();
        
            // Check if a player hand is empty before selecting a sacrifical card
            if (player1Hand.size() == 0 || player2Hand.size() == 0) {
                return false; // End the game if either hand is empty
            }
        ...
        }
    }
    ```
- The project specifications for `LamarckianPoker` should be updated to mention that if either player `Hand` has no `Card` objects to choose as the sacrificial card, the game will end early.