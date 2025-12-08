public class Card {

    private Bool face_up;
    private int value = 0;
    private String face;

    public Card (Bool face_up, int value, String face){
        this.face_up = face_up;
        this.value = value;
        this.face = face;
    }

    public int getValue(){
        if (face_up) {return this.value;}
        else {throw new FileNotFoundException("Card is Face-Down");}
    }

    public void turnFaceUp(){
        face_up = True;
    }
}
