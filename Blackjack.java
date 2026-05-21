//import java.util.ArrayList;

import java.util.ArrayList;

public class Blackjack {
    
    ArrayList<Player> players;
    Dealer dealer;
    Shoe shoe;
    int rounds;

    public Blackjack (ArrayList<Player> players, Dealer dealer, Shoe shoe, int rounds) {
        this.players = players;this.dealer = dealer; this.shoe = shoe;this.rounds = rounds;
    }

    Algorithm algorithm = new Algorithm("Algorithm", players);

    public void run () {
        shoe.populate_shoe();shoe.randomise_shoe();   //top of the game - populate shoe, randomise shoe
        for (int i = 0; i == rounds; i++) {                           //top of the round
            ArrayList<Player> round_players = players;
             for (Player player : players) {                    //check chip count and take bets -> minimum bet: 5
                if (player.bet(5)) {
                    shoe.deal(player, 5);                   //deal
                }
                else {
                    round_players.remove(player);
                }
            }
            shoe.deal(dealer, 1);                           //deal to dealer - in no hole card the dealer is only dealt the hole card after player action

            for (Player player : round_players) {                     //turn order
                hand_algorithm(player, player.get_hand(), player.get_bet(), shoe, dealer); //the hand is also passed for recursion purposes
            }
            
            //dealer turn
            shoe.deal(dealer, 1);
                //follow algorithm
            round_players.forEach( (player) -> {
                if () {} //get hands, for each hand a player has if its higher without being over 21
                            // else if equal give them their bet back
                            //else if equal to 21, give them their bet x1.2
                // muck
            })
        }
    }

    
    for (Card card : hand) {                    //check hand total
        hand_total += card.get_value();
        if (card.get_card_face() == "Ace") {
            if (soft == false) {                //check if soft = true, if not - soft = true and add 10
                soft = true;
            }  
        }
    }

    private void static hand_algorithm(Player player, ArrayList<Card> hand, int bet, Shoe shoe, Dealer dealer) {
        Boolean bust = false; Boolean soft = false; Boolean stick = false;
        int hand_total = 0; //establish game state
        while (bust == false && stick == false) {//take turn - loop until stick or bust
            String recommended_play = algorithm.basic_strategy(hand, dealer, (player.get_chips >= bet));

            switch (recommended_play) {

            case "Hit":
                shoe.deal(player, 1);
                break;

            case "Stick":
                stick = true;
                break;

            case "Double Down":
                if (player.bet(bet) && player.get_hand().size() == 2) { //if the player can afford to double their bet and it's their first action
                    stick = true;
                }
                shoe.deal(player, 1); //if the double isn't affordable, it is a 'Hit' instead
                break;

            case "Split":
                ArrayList<Card> split_hand = new ArrayList<>();
                split_hand.add(hand.get(1)); //split the cards between the two hands
                hand.remove(1);
                player.get_all_hands().add(hand);
                player.get_all_hands().set(0, split_hand); // the split hand is moved to the front
                hand_algorithm(player, )
                break;
            }

            if (hand_total < 12 && soft == true) {      //if total <12 and has an ace 
                hand_total += 10;
            }
            else if (hand_total > 21 && soft == true) { //if total >21, check if soft == true if so minus 10, soft = false
                hand_total -= 10;
                soft = false;
            }
            else {                                      //if not bust = true
                bust = true;
            }
        }
    }
}
