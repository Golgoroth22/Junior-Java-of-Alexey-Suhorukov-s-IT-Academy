/**
 * Created by Админ on 02.07.2016.
 */
public class MultiTable {
    public static void main(String[] args) {
        int SIZE = 10;
        String SIZE_Length = SIZE + "", MAX_Length = SIZE * SIZE + "";
        String firstSimb = "|";
        while (firstSimb.length() <= SIZE_Length.length()){
            firstSimb = " " + firstSimb;
        }
        System.out.print(firstSimb);
        Numbers(1, SIZE, MAX_Length);
        Strings(SIZE, SIZE_Length, MAX_Length);
        for (int j = 1; j <= SIZE; j++) {
            String fStr = j + "|";
            while (fStr.length() <= SIZE_Length.length()) {
                fStr = " " + fStr;
            }
            System.out.print(fStr);
            Numbers(j, SIZE, MAX_Length);
            Strings(SIZE, SIZE_Length, MAX_Length);
        }
    }
    public static void Numbers(int j, int SIZE, String MAX_Length){
        for (int i = 1; i <= SIZE; i++) {
            String ourNumber = "";
            if (i != SIZE) {
                ourNumber = i * j + "|";
            } else {
                ourNumber = i * j + " ";
            }
            while (ourNumber.length() <= MAX_Length.length()) {
                ourNumber = " " + ourNumber;
            }
            System.out.print(ourNumber);
        }
        System.out.println();
    }
    public static void Strings(int SIZE, String SIZE_Length, String MAX_Length){
        for (int i = 0; i <= SIZE; i++) {
            String ourSimb = "+";
            if (i == 0) {
                while (ourSimb.length() <= SIZE_Length.length()) {
                    ourSimb = "-" + ourSimb;
                }
            } else if (i == SIZE) {
                ourSimb = "-";
                while (ourSimb.length() < MAX_Length.length()) {
                    ourSimb = "-" + ourSimb;
                }
            } else {
                while (ourSimb.length() <= MAX_Length.length()) {
                    ourSimb = "-" + ourSimb;
                }
            }
            System.out.print(ourSimb);
        }
        System.out.println();
    }
}
