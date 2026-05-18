import java.util.ArrayList;

public class Shoe {
    
    ArrayList<Card> order;
    int deck_quantity;

    public Shoe (ArrayList<Card> order, int deck_quantity) {
        this.order = order;
        this.deck_quantity = deck_quantity;
    }

    ArrayList<Card> discard;

    public void populate_shoe () {
        String[] faces = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

        for (int i = 0; i < (deck_quantity * 4); i++) { //This loop is written to create 13 new cards each cycle, this is because if the same object were simply added each time, there would be 13 objects with many references to them, which would cause problems later
            for (int j = 0; j<13; j++) {
                order.add(new Card(faces[j], false));
            }

        }
    }

    public void randomise_shoe () {

        this.order.forEach( (n) -> {discard.add(n);});                  //Move all cards to discard
        this.order.clear();                                             //Empty the shoe

        while (!this.discard.isEmpty()) {
            int num = (int)Math.floor(Math.random() * discard.size());  //Select a random point in discard
            Card card = discard.get(num);                               //Grab the card from that point
            card.turn_over("down");
            this.order.add(card);                                       //Put it on top of the shoe
            discard.remove(num);                                        //Remove it from discard
        }
    }

    public void deal (Player player, int num) {
        for (int i = 0; i<num;i++) {
            if (order.isEmpty()) {randomise_shoe();}                        //if would deal from empty deck, reshuffle
            Card card = this.order.get(0);
            this.order.remove(0);
            player.recieve_card(card);
        }
    }

    public void discard (Card card) {
        card.turn_over("up");
        discard.add(card);
    }

    public ArrayList<Card> get_discard () {
        return discard;
    }
}
