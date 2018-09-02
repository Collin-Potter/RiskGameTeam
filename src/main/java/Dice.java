public class Dice {
    private int faceValue;

    public void roll(){
        faceValue = 1 + (int)(Math.random() * 5);
    }

    public int getFaceValue(){
        return faceValue;
    }
}
