package CardGame;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class testCard {
    @Test
    public void testCardCreation () {
        Card test1 = new Card(1);
        //Card test2 = new Card(-1); // to make sure no invalid cards are created or stored 


        System.out.println("Value =" + test1.getValue());
        System.out.println("String = " +  test1.toString());

        assertEquals(1, test1.getValue());
        //assertNotNull(test1.toString());
    }
}
