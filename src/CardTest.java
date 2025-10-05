public class CardTest {
    public static void main(String[] args) {
        card test1 = new Card(1);
        card test2 = new Card(-1); // to make sure no invalid cards are created or stored 


        System.out.println("Value =" + test1.getValue());
        System.out.println("String = " + test1.toString());
    }
}
