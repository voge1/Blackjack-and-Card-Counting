import java.util.ArrayList;

public class Algorithm{

    String name;
    ArrayList<Player> players;

    public Algorithm (String name, ArrayList<Player> players) {
        this.name = name;
        this.players = players;
    }
    
    protected ArrayList<Card> assess (Player our_player, Dealer dealer, Shoe shoe) {
        players.remove(our_player);
        ArrayList<Card> other_visible_cards = new ArrayList<Card>();
        for (Player other_player : players) {
            other_player.get_hand().forEach( (card) -> {
                other_visible_cards.add(card);
            });
        }
        players.add(our_player);
        shoe.get_discard().forEach( (card) -> {
            other_visible_cards.add(card);
        });
        other_visible_cards.add(dealer.get_dealer_card());
        return other_visible_cards;
    }

    public String basic_strategy (ArrayList<Card> our_hand, Dealer dealer, Boolean can_bet_again) { //ENHC - European No Hole Card - 4-8 decks
        String recommended_play = "Error";
        int our_total = 0;Boolean soft = false;
        for (Card card:our_hand) {
            our_total += card.get_value();
            if (card.get_value() == 1 && our_total < 12) {
                our_total += 10;
                soft = true;
            }
        };
        int dealer_card = dealer.get_dealer_card().get_value();
        //Hard total
        if (soft == false) {
            if (our_total < 12) {
                recommended_play = "Hit";
                if ((our_total == 9 && (dealer_card < 7 && dealer_card > 2)) ||
                    ((our_total == 10 || our_total == 11) && !(dealer_card == 10 || dealer_card == 1))) {
                    recommended_play = "Double Down";
                }
            }
            else if (our_total < 17) {
                if ((dealer_card > 6 || dealer_card == 1) ||
                (our_total == 12 && dealer_card < 3)){
                    recommended_play = "Hit";
                }
                else if (our_total != 12 || dealer_card > 3) { //for read-ability keeping specifics that aren't necessary
                    recommended_play = "Stand";
                }
            }
            if (our_total > 16) {
                recommended_play = "Stand";
            }
        }
        //Soft total
        else if (soft == true) {
            int other_card = our_total - 11; //if the total is soft and not 21, the value of all other cards will equal (total - 11)
            switch (other_card) {
                case 2,3:
                    if (dealer_card > 4 && dealer_card < 7) {
                        recommended_play = "Hit";
                    }
                    else {recommended_play = "Double Down";}
                    break;
                case 4,5:
                    if (dealer_card > 3 && dealer_card < 7) {
                        recommended_play = "Hit";
                    }
                    else {recommended_play = "Double Down";}
                    break;
                case 6:
                    if (dealer_card > 2 && dealer_card < 7) {
                        recommended_play = "Hit";
                    }
                    else {recommended_play = "Double Down";}
                    break;
                case 7:
                    if (dealer_card > 8 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else if (dealer_card > 2 && dealer_card < 7) {
                        recommended_play = "Double Down";
                    }
                    else {recommended_play = "Stand";}
                    break;
                case 8, 9:
                    recommended_play = "Stand";
                    break;
            }
        }
        //Split
        if (our_hand.get(0).get_value() == our_hand.get(1).get_value() && our_hand.size() && can_bet_again) { // have to check if the player can bet here rather than later before the decision is made
            int card_number = our_hand.get(0).get_value();
            switch (card_number) {
                case 2,3,7:
                    if (dealer_card > 7 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else {recommended_play = "Split";}
                    break;
                case 4:
                    if (dealer_card == 5 || dealer_card == 6) {
                        recommended_play = "Split";
                    }
                    else {recommended_play = "Hit";}
                    break;
                case 5, 8:
                    if (dealer_card == 10 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else if (card_number == 5) {recommended_play = "Double Down";}
                    else {recommended_play = "Split";}
                    break;
                case 6:
                    if (dealer_card > 6 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else {recommended_play = "Split";}
                    break;
                case 9:
                    if (dealer_card == 7 || dealer_card == 1 || dealer_card == 10) {
                        recommended_play = "Stand";
                    }
                    else {recommended_play = "Split";}
                    break;
                case 10:
                    recommended_play = "Stand";
                    break;
                case 1:
                    if (dealer_card == 1) {recommended_play = "Hit";}
                    else {recommended_play = "Split";}
            }
        }
        return recommended_play;
    }

    public String mimic_dealer (Player player) {
        String recommended_play = "Error";
        int our_total = 0;Boolean soft = false;
        ArrayList<Card> our_hand = our_player.get_hand();
        for (Card card:our_hand) {
            our_total += card.get_value();
            if (card.get_value() == 1 && our_total < 12) {
                our_total += 10;
                soft = true;
            }
        };
        if (our_total < 17) {
            recommended_play = "Hit";
        }
        else {
            recommended_play = "Stand";
        }
        return recommended_play;
    }

    //ComplexActions

   public String double_action (Shoe shoe, Player player) {
    int bet = player.get_bet();
    if (player.bet(bet)) {  //if player cannot afford to double their bet
        shoe.deal(player, 1);
        return "Stick";
    }
    shoe.deal(player, 1);
    return "Hit";
   }

   public ArrayList<Card> split_action (Shoe shoe, Player player) {
    bet = player.get_bet();
    card = player.get_hand().get(0);

   }
}
