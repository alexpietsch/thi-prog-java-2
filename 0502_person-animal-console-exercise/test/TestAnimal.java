import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class TestAnimal extends Helper {

    private Class<?> datenEinlesen;
    private Class<?> datenAusgeben;
    private Class<?> animal;
    private Class<?> datenIo;

    @Before
    public void setUp() throws Exception {
        datenEinlesen = TestAnimal.class.getClassLoader().loadClass("DatenEinlesen");
        datenAusgeben = TestAnimal.class.getClassLoader().loadClass("DatenAusgeben");
        animal = TestAnimal.class.getClassLoader().loadClass("Tier");
        try {
            datenIo = TestAnimal.class.getClassLoader().loadClass("DatenIo");
        } catch(ClassNotFoundException e) {
            datenIo = null;
        }
    }
    
    @Test
    public void test_implements() {
        assertTrue("Animal soll DatenAusgeben implementieren", datenAusgeben.isAssignableFrom(animal));
        assertTrue("Animal soll DatenEinlesen implementieren", datenEinlesen.isAssignableFrom(animal));
    }
    
    @Test
    public void test_methods() {
        checkMethod(animal, String.class, "wasFehltNoch");
        checkMethod(animal, void.class, "setzeNaechstenWert", String.class);
        checkMethod(animal, String[].class, "informationen");
    }
    
    @Test
    public void test_final() {
        if(datenIo != null) {
            assertTrue("Animal soll DatenIo implementieren", datenIo.isAssignableFrom(animal));
        } else {
            fail("Später soll Animal DatenIo implementieren");
        }
    }

    private Method informationen() {
        return checkMethod(animal, String[].class, "informationen");
    }

    private Method setzeNaechstenWert() {
        return checkMethod(animal, void.class, "setzeNaechstenWert", String.class);
    }

    private Method wasFehltNoch() {
        return checkMethod(animal, String.class, "wasFehltNoch");
    }
    
    @Test
    public void test_wasFehltNoch() throws Exception {
        Object personInstance = createInstance(animal.getConstructor());
        assertTrue("wasFehltNoch sollte zuerst zurückgeben, dass der Name fehlt", "Tiername".equals(wasFehltNoch().invoke(personInstance)));
        checkField(animal, "name", String.class).set(personInstance, "Test1234");
        assertTrue("wasFehltNoch sollte als nächstes zurückgeben, dass die E-Mail fehlt", "Tieralter".equals(wasFehltNoch().invoke(personInstance)));
        checkField(animal, "alter", int.class).set(personInstance, 1);
        assertTrue("wasFehltNoch sollte als abschließend null zurückgeben", wasFehltNoch().invoke(personInstance) == null);
    }
    
    @Test
    public void test_setzeNaechstenWert() throws Exception {
        Object personInstance = createInstance(animal.getConstructor());
        setzeNaechstenWert().invoke(personInstance, "Wuff");
        setzeNaechstenWert().invoke(personInstance, "1234");
        assertEquals("name sollte Wuff sein", "Wuff", getValue(personInstance, "name", String.class));
        assertEquals("email sollte wuff@test.de sein", 1234, (int) getValue(personInstance, "alter", int.class));
    }
    
    @Test
    public void test_informationen() throws Exception {
        Object personInstance = createInstance(animal.getConstructor());
        checkField(animal, "name", String.class).set(personInstance, "Test1234");
        checkField(animal, "alter", int.class).set(personInstance, 1234);
        String[] infos = (String[]) informationen().invoke(personInstance);
        assertTrue("Erstes Element sollte den Namen enthalten", infos[0].contains("Test1234"));
        assertTrue("Zweites Element sollte die E-Mail enthalten", infos[1].contains("1234"));
    }

}