import java.util.ArrayList;

public class Dealer extends Player {
    
    public Dealer(String name, ArrayList<Card> hand, int chips) {
        super(name, hand, chips);
    }

    private ArrayList<Card> hand;

    public void recieve_card (Card card) {
        if ((this.hand.isEmpty())) { //If this is the first card drawn by the dealer, it is face up
            card.turn_over("up");
        }
        this.hand.set(this.hand.size()-1, card);
    }

    public Card get_dealer_card () {
        return hand.get(0);
    }

    public boolean check_hole_card () { //for insurance purposes, simply returns if the hole card is a ten/face
        Card hole_card = this.hand.get(1);
        hole_card.turn_over("Up");
        if (this.hand.get(1).get_value() == 10) {
            hole_card.turn_over("Down");
            return true;
        }
        else {
            hole_card.turn_over("Down");
            return false;
        }
    }

    public void reveal_cards () {
        this.hand.forEach( (card) -> {
            card.turn_over("up");
        });
    }
}