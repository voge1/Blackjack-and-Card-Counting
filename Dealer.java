import java.util.ArrayList;

// Originally had plans for Dealer to have polymorphised some functions, but was deemed unnecessary
// now dealer primarily exists for readability

public class Dealer extends Player {
    
    public Dealer(String name, ArrayList<Card> hand, int chips) {
        super(name, hand, chips);
    }

    public Card get_dealer_card () {
        return hand.get(0);
    }

    public boolean check_hole_card () { //for insurance purposes, simply returns if the hole card is a ten/face
        Card hole_card = hand.get(1);
        hole_card.turn_over("Up");
        if (hand.get(1).get_value() == 10) {
            hole_card.turn_over("Down");
            return true;
        }
        else {
            hole_card.turn_over("Down");
            return false;
        }
    }

    public void reveal_cards () {
        hand.forEach( (card) -> {
            card.turn_over("up");
        });
    }
}
