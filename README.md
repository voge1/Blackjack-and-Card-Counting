The code for my 3rd year final project around researching card counting in blackjack

There are 3 primary class objects, Player, Shoe, and Card. These objects are used throughout the program.

There are 3 files that are primarily used to simulate the game of blackjack, those being Main, Blackjack, and Algorithm.

Player has two arguments, name and chips. Name generally stores what kind of strategy the player object is following, for example 'Hi-Lo counting', and chips is the amount
of chips the player has.
The methods are: some setters and getters, and a few methods primarily for the moving around of chips and cards

Card has two arguments, card_face and face_up. Card_face stores the string name of the card, e.g. Ace or Two, and face_up is whether or not the card can be read, i.e. if it
is in the deck. Within the constructor, the hashmap is assembled that matches card face strings up to their values.
The methdos are to get the face, which returns a set message if the card is face down, showing it cannot be read. Then a method to change face_down, and to check the value
of the card, again it only works if the card is face up

Shoe has two arguments as well, order which represents a queue for the deck, and deck_quantity for initialising the game. Of course there are some getters and setters,
and there is a method for populating the shoe - this is the only time in the program that new card objects are generated. There is a function for shuffling the discard
pile (an arraylist created earlier in the class) which simply uses Math.random() to grab randomly indexed items from within discard and puts them on top of the order.
Then there is a method for dealing a number of cards to a player, and a method to put a given card into the discard pile

Main instantiates players and initiates the Blackjack run method

Blackjack is sort of the primary file the game runs in - it has the run method which takes all the players, the dealer, and the shoe and initiates the game for the decided
number of rounds. It also contains the method for a player taking a turn - they run whatever algorithm their name suggests and then resolve whichever action that
algorithm decided on.

The Algorithm class is the location for all of the algorithm methods, from the relatively simple Mimic the Dealer to the longer but still quite simple Basic Strategy. All
of the algorithms used are essentially a long list of if operations as that tends to be how the structure of a poker algorithmn works.
