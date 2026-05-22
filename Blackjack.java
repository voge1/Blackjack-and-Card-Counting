//import java.util.ArrayList;

import java.util.ArrayList;

public class Blackjack {
    
    ArrayList<Player> players;
    Player dealer;
    Shoe shoe;
    int rounds;

    public Blackjack (ArrayList<Player> players, Player dealer, Shoe shoe, int rounds) {
        this.players = players;this.dealer = dealer; this.shoe = shoe;this.rounds = rounds;
    }

    public void run () {
        System.out.println("Populating shoe...");
        shoe.populate_shoe();shoe.randomise_shoe();             //top of the game - populate shoe, randomise shoe
        for (int i = 0; i < rounds; i++) {                     //top of the round
            System.out.println("Starting round "+i);
            ArrayList<Player> round_players = players;
             for (Player player : players) {                    //check chip count and take bets -> minimum bet: 5
                if (player.bet(5)) {
                    shoe.deal(player, 5);                       //deal
                }
                else {
                    round_players.remove(player);
                }
            }
            shoe.deal(dealer, 1);                               //deal to dealer - in no hole card the dealer is only dealt the hole card after player action

            for (Player player : round_players) {               //turn order
                System.out.println(player.get_name()+"'s turn");
                hand_algorithm(player, player.get_bet(), shoe, dealer); //the hand is also passed for recursion purposes
            }
            
            //dealer turn
            shoe.deal(dealer, 1);
                //follow algorithm
            boolean bust = false; boolean stick = false;boolean soft = false;
            int dealer_total = 0;int sum;
            while (bust == false && stick == false) {
                sum = 0;
                String recommended_action = Algorithm.mimic_dealer(dealer);

                switch (recommended_action) {
                    case "Hit" -> shoe.deal(dealer, 1);
                    case "Stick" -> stick = true;
                }

                for (Card card : dealer.get_hand()) {                    //check hand total
                    sum += card.get_value();
                    if ("Ace".equals(card.get_card_face()) && soft == false) { //check if soft = true, if not - soft = true and add 10
                        sum += 10;
                        soft = true;
                    }
                }

                if (sum > 21 && soft == true) { //if total >21, check if soft == true if so minus 10, soft = false
                    sum -= 10;
                    soft = false;
                }
                if (sum > 21) {
                    bust = true;
                    dealer_total = sum;
                }
                if (stick) {
                    dealer_total = sum;
                }
            }

            for (Player player : round_players) {
                for (ArrayList<Card> hand : player.get_all_hands()) {
                    sum = 0;soft = false;
                    for (Card card : hand) {
                        sum += card.get_value();
                        if ("Ace".equals(card.get_card_face()) && soft == false) {
                            soft = true;
                            sum += 10;
                        }
                    }
                            //for each hand a player has if its higher without being over 21, they get double their bet back
                            // else, if it's equal them get their bet back
                            //else if equal to 21, give them their bet x1.2
                    if (sum > dealer_total && sum < 22) {
                        if (sum == 21 && hand.size() == 2) {
                            player.recieve_chips((int) Math.floor((player.get_bet()) * 1.2));
                        }
                        else {
                            player.recieve_chips((player.get_bet()) * 2);
                        }
                    }
                    else if (sum == dealer_total) {
                        player.recieve_chips(player.get_bet());
                    }
                }
                player.muck_hand(shoe);
            }
        System.out.println("Thank you for playing");
        }
}

    private static void hand_algorithm(Player player, int bet, Shoe shoe, Player dealer) {
        Boolean bust = false; Boolean soft = false; Boolean stick = false;
        int hand_total; //establish game state
        ArrayList<Card> hand = player.get_hand();
        while (bust == false && stick == false) {//take turn - loop until stick or bust
            hand_total = 0;
            boolean can_bet_again = player.get_chips() >= bet;
            String recommended_play = Algorithm.basic_strategy(hand, dealer, can_bet_again);

            System.out.println(player.get_name() + " will " + recommended_play);

            System.out.println(player.get_name()+" has "); 
            player.get_hand().forEach( (card) -> {
                System.out.println(card.get_card_face());
            });


            switch (recommended_play) {

                case "Hit" -> shoe.deal(player, 1);

                case "Stick" -> stick = true;

                case "Double Down" -> {
                    if (player.bet(bet) && player.get_hand().size() == 2) { //if the player can afford to double their bet and it's their first action
                        stick = true;
                    }
                    shoe.deal(player, 1); //if the double isn't affordable, it is a 'Hit' instead
                    } // IF YOU SPLIT AND DOUBLE DOWN, YOUR BET HAS DOUBLED ON THE FIRST HAND WITHOUT PAYING

                case "Split" -> {
                    ArrayList<Card> split_hand = new ArrayList<>();
                    split_hand.add(hand.get(1));                //split the cards between the two hands
                    hand.remove(1);                             //remove the card that was added to split_hand
                    shoe.deal(player, 1);                       //a card has been removed from the hand, so we add one to it
                    player.get_all_hands().set(0, split_hand);  //the split hand is moved to the front
                    shoe.deal(player, 1);                       //now that split_hand was moved to the list's front, it'll be dealt to
                    hand_algorithm(player, bet, shoe, dealer);  //recur the method on this new split hand
                    player.get_all_hands().add(player.get_hand());
                    player.get_all_hands().set(0, hand);
                    }
            }
            
            for (Card card : hand) {                    //check hand total
                hand_total += card.get_value();
                if ("Ace".equals(card.get_card_face()) && soft == false) { //check if soft = true, if not - soft = true and add 10
                    hand_total += 10;
                    soft = true;
                }
            }

            if (hand_total > 21 && soft == true) { //if total >21, check if soft == true if so minus 10, soft = false
                hand_total -= 10;
                soft = false;
            }
            if (hand_total > 21) {                                      //if still over, bust = true
                bust = true;
            }
        }
    }
}
