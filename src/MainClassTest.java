import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    MainClass mainClass = new MainClass();

    @Test
    public void testGetLocalNumber() {
        Assert.assertTrue("Результат выполнения getLocalNumber не равен 14", mainClass.getLocalNumber() == 14);
    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("Результат выполнения getClassNumber не больше 45", mainClass.getClassNumber() > 45);
    }

    @Test
    public void testGetClassString() {
        boolean containsHello = mainClass.getClassString().contains("Hello") || mainClass.getClassString().contains("hello");
        Assert.assertTrue("Результат выполнения не содержит подстроку Hello или hello", containsHello);
    }
}
