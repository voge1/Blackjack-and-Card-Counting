import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Card> order = new ArrayList<Card>();
        ArrayList<Card> player_1_hand = new ArrayList<Card>();
        ArrayList<Card> player_2_hand = new ArrayList<Card>();
        ArrayList<Card> player_3_hand = new ArrayList<Card>();
        ArrayList<Card> dealer_hand = new ArrayList<Card>();

        Shoe shoe = new Shoe(order, 4);
        Player player_1 = new Player("John", player_1_hand, 100);
        Player player_2 = new Player("Jane", player_2_hand, 100);
        Player player_3 = new Player("Jax", player_3_hand, 100);
        Dealer dealer = new Dealer("Dealer", dealer_hand, 10000);
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player_1); players.add(player_2); players.add(player_3);
        Blackjack game = new Blackjack(players, dealer, shoe, 3);
        
        game.run();
    }
}
