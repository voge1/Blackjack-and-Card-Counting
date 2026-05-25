import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Card> order = new ArrayList<>();

        Shoe shoe = new Shoe(order, 6);

        Player player_1 = new Player("Dealer Mimic", 1000);
        Player player_2 = new Player("Basic Strategy", 1000);
        Player player_3 = new Player("Hi-Lo Counting", 1000);
        //Player player_4 = new Player("K-O Counting", 1000);
        Player dealer = new Player("Dealer", 0);

        ArrayList<Player> players = new ArrayList<>();
        players.add(player_1); players.add(player_2); players.add(player_3);//players.add(player_4);

        Blackjack game = new Blackjack(players, dealer, shoe, 100);
        
        game.run();
    }
}
