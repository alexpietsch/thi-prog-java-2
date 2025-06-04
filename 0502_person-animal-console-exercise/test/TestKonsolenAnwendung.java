import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class TestKonsolenAnwendung extends Helper {

    private Class<?> konsolenAnwendung;
    private Class<?> datenEinlesen;
    private Class<?> datenAusgeben;
    private Class<?> person;

    @Before
    public void setUp() throws Exception {
        person = TestKonsolenAnwendung.class.getClassLoader().loadClass("Person");
        datenEinlesen = TestKonsolenAnwendung.class.getClassLoader().loadClass("DatenEinlesen");
        datenAusgeben = TestKonsolenAnwendung.class.getClassLoader().loadClass("DatenAusgeben");
        konsolenAnwendung = TestKonsolenAnwendung.class.getClassLoader().loadClass("KonsolenAnwendung");
    }
    
    @Test
    public void test_methods() {
        einlesen();
        ausgeben();
    }

    private Method ausgeben() {
        return checkMethod(konsolenAnwendung, void.class, "ausgeben", datenAusgeben);
    }

    private Method einlesen() {
        return checkMethod(konsolenAnwendung, void.class, "einlesen", datenEinlesen);
    }

    private Method getName() {
        return checkMethod(person, String.class, "getName");
    }

    private Method getEmail() {
        return checkMethod(person, String.class, "getEmail");
    }
    
    @Test
    public void test_einlesen() throws Exception {
        provideInput("Test" + System.lineSeparator() + "test@test.de" + System.lineSeparator());
        
        Object personInstance = createInstance(person.getConstructor());
        Object konsolenInstance = createInstance(konsolenAnwendung.getConstructor());
        
        einlesen().invoke(konsolenInstance, personInstance);
        
        assertEquals("Name sollte Test sein", "Test", getName().invoke(personInstance));
        assertEquals("E-Mail sollte test@test.de sein", "test@test.de", getEmail().invoke(personInstance));
    }
    
    @Test
    public void test_ausgeben() throws Exception {
        Object personInstance = createInstance(person.getConstructor());
        Object konsolenInstance = createInstance(konsolenAnwendung.getConstructor());
        
        checkField(person, "name", String.class).set(personInstance, "Test1234");
        checkField(person, "email", String.class).set(personInstance, "test@test.de");
        ausgeben().invoke(konsolenInstance, personInstance);
        String output = getOutput();
        assertTrue("Es sollte der Name ausgegeben werden", output.contains("Test1234"));
        assertTrue("Es sollte die Mail ausgegeben werden", output.contains("test@test.de"));
    }
}