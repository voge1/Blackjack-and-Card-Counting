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

    Algorithm basic_strategy = new Algorithm("basic strategy", players);

    public void run () {
        this.shoe.populate_shoe();this.shoe.randomise_shoe();   //top of the game - populate shoe, randomise shoe
        for (int i = 0; i == rounds; i++) {                           //top of the round
            ArrayList<Player> round_players = players;
             for (Player player : players) {                    //check chip count and take bets -> minimum bet: 2
                Boolean bet = true;//consult algorithm to get bet amount & store bet amount
                if (bet) {
                    shoe.deal(player, 2);                   //deal
                }
                else {
                    round_players.remove(player);
                }
            }
            shoe.deal(dealer, 1);                           //deal to dealer - in no hole card the dealer is only dealt the hole card after player action

            for (Player player : round_players) {                     //turn order
                Boolean bust = false; Boolean soft = false; Boolean stick = false;
                int hand_total = 0;ArrayList<Card> hand = player.get_hand(); //establish game state
                
                for (Card card : hand) {                    //check hand total
                    hand_total += card.get_value();
                    if (card.get_card_face() == "Ace") {
                        if (soft == false) {                //check if soft = true, if not - soft = true and add 10
                            soft = true;
                        }  
                    }
                }

                while (bust == false && stick == false) {       //take turn - loop until stick or bust
                //player consult algorithm
                    //Action
                    
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
            
            //dealer turn
            if (dealer.get_dealer_card().get_value() == 1) {} == 1 && dealer.check_hole_card()
            shoe.deal(dealer, 1);
                //reveal hand
                //follow algorithm
            //payout/collection
            //muck
            //if deck empty - randomise
        }
    }
}
