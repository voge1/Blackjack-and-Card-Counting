import java.util.ArrayList;

public class Player {
   String name;
   int chips;

   public Player (String name, int chips) {
      this.name = name; this.chips = chips;
   }

   ArrayList<ArrayList<Card>> hands;
   int bet = 0;

   //Getters & Setters
   public int get_chips() {return chips;}
   public String get_name() {return name;}
   public int get_bet() {return bet;}
   public ArrayList<Card> get_hand() {return hands.get(0);}
   public ArrayList<ArrayList<Card>> get_all_hands() {return hands;}

   public void recieve_card (Card card) {
      card.turn_over("up");
      hands.get(0).add(card);
   }

   public void muck_hand (Shoe shoe) {
      hands.forEach( (hand) -> {
         hand.forEach( (card) -> {
            shoe.discard(card);
         });
         hand.clear();
      });
   }

   public Boolean bet (int amount) {
      if (amount <= chips) {   //see if the amount bet is possible
         chips -= amount;
         bet = amount;
         return true;
      }
      return false;  // return if the bet was performed
   }
}
