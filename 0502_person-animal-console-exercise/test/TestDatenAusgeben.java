import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestDatenAusgeben extends Helper {
    private Class<?> datenAusgeben;

    @Before
    public void setUp() throws Exception {
        datenAusgeben = TestDatenAusgeben.class.getClassLoader().loadClass("DatenAusgeben");
    }
    
    @Test
    public void test_interface() {
        assertTrue("DatenAusgeben muss ein Interface sein", datenAusgeben.isInterface());
    }
    
    @Test
    public void test_methods() {
        this.checkMethod(true, datenAusgeben, String[].class, "informationen");
    }
}