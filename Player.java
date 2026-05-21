import java.util.ArrayList;

public class Player {
   String name;
   ArrayList<Card> hand;
   int chips;

   public Player (String name, ArrayList<Card> hand, int chips) {
      this.name = name; this.hand = hand; this.chips = chips;
   }

   int bet = 0;

   //Getters & Setters
   public int get_chips() {return chips;}
   public String get_name() {return name;}
   public int get_bet() {return bet;}
   public ArrayList<Card> get_hand() {return hand;}

   public void recieve_card (Card card) {
      card.turn_over("up");
      this.hand.set(this.hand.size()-1, card);
   }

   public void muck_hand (Shoe shoe) {
      this.hand.forEach( (n) -> {
         shoe.discard(n);
         this.hand.remove(n);
      });
   }

   public Boolean bet (int amount) {
      if (amount <= this.chips) {   //see if the amount bet is possible
         this.chips -= amount;
         bet = amount;
         return true;
      }
      return false;  // return if the bet was performed
   }
}
