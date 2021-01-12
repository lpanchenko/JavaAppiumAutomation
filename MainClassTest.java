import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Returned value have to be 14",
                14, MainClass.getLocalNumber());
    }
}
