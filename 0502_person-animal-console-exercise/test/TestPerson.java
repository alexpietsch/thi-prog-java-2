import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class TestPerson extends Helper {

    private Class<?> datenEinlesen;
    private Class<?> datenAusgeben;
    private Class<?> person;

    @Before
    public void setUp() throws Exception {
        datenEinlesen = TestPerson.class.getClassLoader().loadClass("DatenEinlesen");
        datenAusgeben = TestPerson.class.getClassLoader().loadClass("DatenAusgeben");
        person = TestPerson.class.getClassLoader().loadClass("Person");
    }
    
    @Test
    public void test_implements() {
        assertTrue("Person soll DatenAusgeben implementieren", datenAusgeben.isAssignableFrom(person));
        assertTrue("Person soll DatenEinlesen implementieren", datenEinlesen.isAssignableFrom(person));
        assertTrue("Person soll Comparable implementieren", Comparable.class.isAssignableFrom(person));
    }
    
    @Test
    public void test_methods() {
        wasFehltNoch();
        setzeNaechstenWert();
        informationen();
        compareTo();
    }

    private Method compareTo() {
        return checkMethod(person, int.class, "compareTo", Object.class);
    }

    private Method informationen() {
        return checkMethod(person, String[].class, "informationen");
    }

    private Method setzeNaechstenWert() {
        return checkMethod(person, void.class, "setzeNaechstenWert", String.class);
    }

    private Method wasFehltNoch() {
        return checkMethod(person, String.class, "wasFehltNoch");
    }
    
    @Test
    public void test_wasFehltNoch() throws Exception {
        Object personInstance = createInstance(person.getConstructor());
        assertTrue("wasFehltNoch sollte zuerst zurückgeben, dass der Name fehlt", "Name".equals(wasFehltNoch().invoke(personInstance)));
        checkField(person, "name", String.class).set(personInstance, "Test1234");
        assertTrue("wasFehltNoch sollte als nächstes zurückgeben, dass die E-Mail fehlt", "E-Mail".equals(wasFehltNoch().invoke(personInstance)));
        checkField(person, "email", String.class).set(personInstance, "test@test.de");
        assertTrue("wasFehltNoch sollte als abschließend null zurückgeben", wasFehltNoch().invoke(personInstance) == null);
    }
    
    @Test
    public void test_setzeNaechstenWert() throws Exception {
        Object personInstance = createInstance(person.getConstructor());
        setzeNaechstenWert().invoke(personInstance, "Wuff");
        setzeNaechstenWert().invoke(personInstance, "wuff@test.de");
        assertEquals("name sollte Wuff sein", "Wuff", getValue(personInstance, "name", String.class));
        assertEquals("email sollte wuff@test.de sein", "wuff@test.de", getValue(personInstance, "email", String.class));
    }
    
    @Test
    public void test_informationen() throws Exception {
        Object personInstance = createInstance(person.getConstructor());
        checkField(person, "name", String.class).set(personInstance, "Test1234");
        checkField(person, "email", String.class).set(personInstance, "test@test.de");
        String[] infos = (String[]) informationen().invoke(personInstance);
        assertTrue("Erstes Element sollte den Namen enthalten", infos[0].contains("Test1234"));
        assertTrue("Zweites Element sollte die E-Mail enthalten", infos[1].contains("test@test.de"));
    }
    
    @Test
    public void test_compareTo() throws Exception {
        Object personInstance1 = createInstance(person.getConstructor());
        checkField(person, "name", String.class).set(personInstance1, "A");
        checkField(person, "email", String.class).set(personInstance1, "a@a.a");
        Object personInstance2 = createInstance(person.getConstructor());
        checkField(person, "name", String.class).set(personInstance2, "B");
        checkField(person, "email", String.class).set(personInstance2, "b@b.b");
        
        int invoke1 = (int) compareTo().invoke(personInstance1, personInstance2);
        assertEquals("A < B => -1", -1, invoke1);
        
        int invoke2 = (int) compareTo().invoke(personInstance2, personInstance1);
        assertEquals("B > A => 1", 1, invoke2);
        
        int invoke3 = (int) compareTo().invoke(personInstance1, personInstance1);
        assertEquals("A == A => 0", 0, invoke3);
        
        int invoke4 = (int) compareTo().invoke(personInstance2, personInstance2);
        assertEquals("B == B => 0", 0, invoke4);
        
        int invoke5 = (int) compareTo().invoke(personInstance1, (Object) null);
        assertEquals("A > null => 1", 1, invoke5);
    }
}