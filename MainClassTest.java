import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Returned value have to be 14",
                14, MainClass.getLocalNumber());
    }

    @Test
    public void testGetClassNumber(){
        Assert.assertTrue("Returned value should be bigger that 45, actual is " + MainClass.getClassNumber(),
                MainClass.getClassNumber() > 45);

    }
}
