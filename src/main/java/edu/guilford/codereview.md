## Documentation

- Although the initial project did not have proper Javadoc comments for all classes and methods, it is now 
satisfactory in its current state.

## Code Structure

- The `LamarckianPoker` class does not properly fulfill the specification for the `turn()` method. The specification calls for it to check if both player `Hand`s have at least seven `Card`s, but the condition for the `if` statement uses the `or` operator `||`, returning false if just one player `Hand` has at least seven `Card`s. This can be fixed by replacing it with the `and` operator `&&` so that both `Hand`s are checked.
- All other classes and methods fulfill their specifications properly.  
- The `compareTo()` method in the `Card` class has an unnecessary `TODO` comment that should be removed.
- All classes are consistently formatted.
- There is no debugging code that inadvertently runs in production.
- In `Blackjack`, the `playerTurn()` and `dealerTurn()` methods are very similar and their code can be combined:
    ```
    public boolean blackjackTurn(Hand hand, int drawLimit) {
        while (hand.getTotalValue() < drawLimit) {
            hand.addCard(deck.deal());
        }
        return hand.getTotalValue() <= 21;
    }
    ```
This method can then be called in `playerTurn()` using `return blackjackTurn(playerHand, 16);` and in `dealerTurn()` using `return blackjackTurn(dealerHand, 17);`.