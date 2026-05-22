import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Card> order = new ArrayList<>();

        Shoe shoe = new Shoe(order, 6);

        Player player_1 = new Player("Dealer Mimic", 1000);
        Player player_2 = new Player("Basic Strategy", 1000);
        Player player_3 = new Player("Hi-Lo Counting", 1000);
        Player dealer = new Player("Dealer", 100000);
        
        ArrayList<Player> players = new ArrayList<>();
        players.add(player_1); players.add(player_2); players.add(player_3);

        Blackjack game = new Blackjack(players, dealer, shoe, 3);
        
        game.run();
        System.out.println("Done");
    }
}
