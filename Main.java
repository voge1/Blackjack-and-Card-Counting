import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Card> order = new ArrayList<Card>();
        ArrayList<Card> player_1_hand = new ArrayList<Card>();
        ArrayList<Card> player_2_hand = new ArrayList<Card>();
        ArrayList<Card> player_3_hand = new ArrayList<Card>();
        ArrayList<Card> dealer_hand = new ArrayList<Card>();

        Shoe shoe = new Shoe(order, 6);
        Player player_1 = new Player("Dealer Mimic", player_1_hand, 1000);
        Player player_2 = new Player("Basic Strategy", player_2_hand, 1000);
        Player player_3 = new Player("Hi-Lo Counting", player_3_hand, 1000);
        Dealer dealer = new Dealer("Dealer", dealer_hand, 100000);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player_1); players.add(player_2); players.add(player_3);
        Blackjack game = new Blackjack(players, dealer, shoe, 3);
        
        game.run();
    }
}
