## Documentation

### Javadoc: 

Although the initial project did not have proper Javadoc comments for all classes and methods, it is now 
satisfactory in its current state.

## Code Structure

### Specifications: 

- The LamarckianPoker class does not properly fulfill the specification for the turn() method. The specification calls for it to check if both player Hand objects have at least seven Card objects, but the condition for the if statement uses the **or** operator **||**, returning false if just one player Hand has at least seven Card objects. This can be fixed by replacing it with the **and** operator **&&** so that both Hands are checked.
- All other classes and methods fulfill their specifications properly.  

### Formatting: 


