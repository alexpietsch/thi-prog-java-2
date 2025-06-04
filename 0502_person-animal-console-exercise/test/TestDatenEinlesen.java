import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestDatenEinlesen extends Helper {
    private Class<?> datenEinlesen;

    @Before
    public void setUp() throws Exception {
        datenEinlesen = TestDatenEinlesen.class.getClassLoader().loadClass("DatenEinlesen");
    }
    
    @Test
    public void test_interface() {
        assertTrue("DatenAusgeben muss ein Interface sein", datenEinlesen.isInterface());
    }
    
    @Test
    public void test_methods() {
        this.checkMethod(true, datenEinlesen, String.class, "wasFehltNoch");
        this.checkMethod(true, datenEinlesen, void.class, "setzeNaechstenWert", String.class);
    }

}