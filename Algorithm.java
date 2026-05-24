import java.util.ArrayList;

public class Algorithm{

    public static ArrayList<Card> assess (Player dealer, Shoe shoe, ArrayList<Player> players) { //isn't just 'get count' because hi-lo and K-O count 7s differently
        ArrayList<Card> other_visible_cards = new ArrayList<>();
        for (Player player : players) {
            player.get_all_hands().forEach( (hand) -> {
                hand.forEach( (card) -> {
                    other_visible_cards.add(card);
                });
            });
        }
        shoe.get_discard().forEach( (card) -> {
            other_visible_cards.add(card);
        });
        if (!dealer.get_hand().isEmpty()) {
        other_visible_cards.add(dealer.get_hand().get(0));
        }
        return other_visible_cards;
    }

    public static int counter_bet (Player player, int min_bet, Player dealer, Shoe shoe, ArrayList<Player> players, String count) {
        ArrayList<Card> other_visible_cards = assess(dealer, shoe, players);
        int running_count = 0; int true_count = 0;
        int bet = min_bet;boolean can_bet = false;
        int player_chips = player.get_chips();
        switch (count) {
            case "Hi-Lo" -> {
                //assumes deck quantity is at least 6
                for (Card card : other_visible_cards) {
                    int value = card.get_value();
                    if (value < 7 && value != 1) {running_count++;}
                    else if (value == 10 || value == 1) {running_count--;}
                }
                true_count = (int) Math.floor(running_count / Math.ceil((shoe.get_deck_size() / 52)));
                while (can_bet == false) {
                    switch (true_count) {
                        case 2 -> {bet = min_bet * 2;}
                        case 3 -> {bet = min_bet * 4;}
                        case 4 -> {bet = min_bet * 8;}
                        default -> {
                            if (true_count < 2) {
                                bet = min_bet;
                            }
                            else if (true_count > 4) {
                                bet = min_bet * 12;
                            }
                        }
                    }
                    can_bet = (bet <= player_chips);
                    if (!can_bet) {true_count--;} //if cannot meet the bet, treat the count as a slightly lower and try again
                }
            }
            case "K-O" -> {
                //assumes deck quantity is at least 6
                for (Card card : other_visible_cards) {
                    int value = card.get_value();
                    if (value < 8 && value != 1) {running_count++;}
                    else if (value == 10 || value == 1) {running_count--;}
                }
                while (can_bet == false) {
                    switch (running_count) {
                        case 1 -> {bet = min_bet * 2;}
                        case 2 -> {bet = min_bet * 4;}
                        case 3 -> {bet = min_bet * 8;}
                        case 4 -> {bet = min_bet * 10;}
                        default -> {
                            if (true_count < 1) {
                                bet = min_bet;
                            }
                            else if (true_count > 4) {
                                bet = min_bet * 12;
                            }
                        }
                    }
                    can_bet = (bet <= player_chips);
                    if (!can_bet) {true_count--;} //if cannot meet the bet, treat the count as a slightly lower and try again
                }
            }
        }
        return bet;
    }

    public static String hi_lo_strategy (Player our_player, Player dealer, Shoe shoe, ArrayList<Player> players, Boolean can_bet_again) {
        //get true count
        ArrayList<Card> other_visible_cards = assess(dealer, shoe, players);
        int running_count = 0;
        for (Card card : other_visible_cards) {
            int value = card.get_value();
            if (value < 7 && value != 1) {running_count++;}
            else if (value == 10 || value == 1) {running_count--;}
        }
        int true_count = (int) Math.floor(running_count / Math.ceil((shoe.get_deck_size() / 52))); //divide by number of decks left, rounded up to avoid math errors

        //basic strategy
        String recommended_play = basic_strategy(our_player.get_hand(), dealer, can_bet_again); //follow basic strategy, then account for deviations

        // get information
        ArrayList<Card> hand = our_player.get_hand();
        int dealer_has = dealer.get_hand().get(0).get_value();
        int sum = 0;boolean soft = false;
        for (Card card : hand) {                    //check hand total
            sum += card.get_value();
            if ("Ace".equals(card.get_card_face()) && soft == false) { //check if soft = true, if not - soft = true and add 10
                sum += 10;
                soft = true;
            }
        }

        //Illustrious 18 are the deviations counting brings to basic strategy - we only have the top 17, because ENHC doesn't do insurance
        // unfortunately this does sort of have to be a series of if statements as the same play might be different at higher counts, e.g. 12 vs 4
        if (true_count > -2) {
            if (sum == 13 && dealer_has == 3) {recommended_play = "Stick";}
            if (sum == 12 && dealer_has == 5) {recommended_play = "Stick";}
            if (true_count > -1) {
                if (sum == 12 && dealer_has == 6) {recommended_play = "Stick";}
                if (sum == 13 && dealer_has == 2) {recommended_play = "Stick";}
                if (true_count > 0) {
                    if (sum == 16 && dealer_has == 10) {recommended_play = "Stick";}
                    if (sum == 12 && dealer_has == 4) {recommended_play = "Stick";}
                    if (true_count > 1) {
                        if (sum == 9 && dealer_has == 2 && can_bet_again) {recommended_play = "Double Down";}
                        if (true_count > 2) {
                            if (sum == 12 && dealer_has == 3) {recommended_play = "Stick";}
                            if (true_count > 3) {
                                if (sum == 12 && dealer_has == 4) {recommended_play = "Stick";}
                                if (sum == 9 && dealer_has == 7 && can_bet_again) {recommended_play = "Double Down";}
                                if (true_count > 4) {
                                    if (sum == 15 && dealer_has == 10) {recommended_play = "Stand";}
                                    if ((hand.get(0).get_value() == 10 && hand.get(1).get_value() == 10 && hand.size() == 2 && can_bet_again) 
                                        && dealer_has == 10) {recommended_play = "Split";}
                                    if (sum == 11 && dealer_has == 10 && can_bet_again) {recommended_play = "Double Down";}
                                    if (true_count > 5) {
                                        if ((hand.get(0).get_value() == 10 && hand.get(1).get_value() == 10 && hand.size() == 2 && can_bet_again)
                                             && dealer_has == 5) {recommended_play = "Split";}
                                        if (sum == 16 && dealer_has == 9) {recommended_play = "Stick";}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // If none of the above listed situations occurred due to insufficient true count, the default action will occur
        return recommended_play;
    } 

    public static String basic_strategy (ArrayList<Card> our_hand, Player dealer, Boolean can_bet_again) { //ENHC - European No Hole Card - 4-8 decks
        String recommended_play = "Error";
        if (our_hand.size() < 1){return recommended_play;}
        int our_total = 0;Boolean soft = false;
        for (Card card:our_hand) {
            our_total += card.get_value();
            if (card.get_value() == 1 && our_total < 12 && soft == false) {
                our_total += 10;
                soft = true;
            }
        }
        int dealer_card = dealer.get_hand().get(0).get_value();
        //Soft total
        if (soft == true) {
            int other_card = our_total - 11; //if the total is soft and not 21, the value of all other cards will equal (total - 11)
            switch (other_card) {
                case 2,3 -> {
                    if (dealer_card > 4 && dealer_card < 7) {
                        recommended_play = "Hit";
                    }
                    else if (can_bet_again) {recommended_play = "Double Down";}
                    else {recommended_play = "Hit";}
                }
                case 4,5 -> {
                    if (dealer_card > 3 && dealer_card < 7) {
                        recommended_play = "Hit";
                    }
                    else if (can_bet_again) {recommended_play = "Double Down";}
                    else {recommended_play = "Hit";}
                }
                case 6 -> {
                    if (dealer_card > 2 && dealer_card < 7) {
                        recommended_play = "Hit";
                    }
                    else if (can_bet_again){recommended_play = "Double Down";}
                    else {recommended_play = "Hit";}
                }
                case 7 -> {
                    if (dealer_card > 8 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else if (dealer_card > 2 && dealer_card < 7 && can_bet_again) {
                        recommended_play = "Double Down";
                    }
                    else {recommended_play = "Stick";}
                }
                case 8, 9, 10 -> recommended_play = "Stick";
                default -> {
                    soft = false;
                }
            }
        }
        //Hard total
        if (soft == false) {
            if (our_total > 16) {
                recommended_play = "Stick";
            }
            else if (our_total < 12) {
                recommended_play = "Hit";
                if ((our_total == 9 && (dealer_card < 7 && dealer_card > 2)) ||
                    ((our_total == 10 || our_total == 11) && !(dealer_card == 10 || dealer_card == 1))) {
                    recommended_play = "Double Down";
                }
            }
            else if (our_total < 17) {
                if ((dealer_card > 6 || dealer_card == 1) || (our_total == 12 && dealer_card <= 3)){
                    recommended_play = "Hit";
                }
                else {
                    recommended_play = "Stick";
                }
            }
        }
        //Split
        if (our_hand.get(0).get_value() == our_hand.get(1).get_value() && our_hand.size() == 2 && can_bet_again) { // have to check if the player can bet here rather than later before the decision is made
            int card_number = our_hand.get(0).get_value();
            switch (card_number) {
                case 2,3,7 -> {
                    if (dealer_card > 7 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else {recommended_play = "Split";}
                }
                case 4 -> {
                    if (dealer_card == 5 || dealer_card == 6) {
                        recommended_play = "Split";
                    }
                    else {recommended_play = "Hit";}
                }
                case 5 -> {
                    if (dealer_card == 10 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else if (card_number == 5) {recommended_play = "Double Down";} //8 and 5 are very similar, except 5 doubles and 8 splits
                    else {recommended_play = "Split";}
                }
                case 6 -> {
                    if (dealer_card > 6 || dealer_card == 1) {
                        recommended_play = "Hit";
                    }
                    else {recommended_play = "Split";}
                }
                case 9 -> {
                    if (dealer_card == 7 || dealer_card == 1 || dealer_card == 10) {
                        recommended_play = "Stick";
                    }
                    else {recommended_play = "Split";}
                }
                case 10 -> recommended_play = "Stick";
                case 1 -> {
                    if (dealer_card == 1) {recommended_play = "Hit";}
                    else {recommended_play = "Split";}
                }
            }
        }
        return recommended_play;
    }

    public static String mimic_dealer (Player player) {
        String recommended_play;
        int our_total = 0;
        ArrayList<Card> our_hand = player.get_hand();
        boolean soft = false;
        for (Card card:our_hand) {
            our_total += card.get_value();
            if (card.get_value() == 1 && our_total < 12 && soft == false) { //soft here mainly prevents 2 Aces from counting as 22
                our_total += 10;
                soft = true;
            }
        }
        if (our_total > 21 && soft == true) {
            our_total -= 10;
        }
        if (our_total < 17) {
            recommended_play = "Hit";
        }
        else {
            recommended_play = "Stick";
        }
        return recommended_play;
    }
}
