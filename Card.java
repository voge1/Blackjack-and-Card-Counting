import java.util.HashMap;

public class Card { // An individual card: It has a card face from which a value can be determined, and is either face-up(True) or face-down(False)
    String card_face;
    Boolean face_up = false;

    public Card (String card_face, Boolean face_up) {
        this.card_face = card_face;
        this.face_up = face_up;
    }

    public static HashMap<String, Integer> get_hashmap () {

        String[] faces = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        HashMap<String, Integer> face_values = new HashMap<>(13);

        for (int i = 0; i<12; i++) {
            face_values.put(faces[i], values[i]);
        }

        // Ace is valued at one so that after totalling a hands value, if it is less than 12,
        // 10 can be added thus summing the Ace to 11 and causing the value to be 'soft'
        
        return face_values;
        
    }

    public String get_card_face () { //if a card is face-up it can be read, otherwise it cannot
        if (face_up) {
            return (card_face);
        }
        else {
            return ("Card is Face-Down");
        }
    }

    public void turn_over (String face) { // Flip a card: turn it face-up or face-down
        face_up = "up".equals(face);
    }

    public int get_value () {
       HashMap<String, Integer> face_values = get_hashmap();
        if (face_up) {
            return face_values.get(card_face);
        }
        return 0;
    }
}
