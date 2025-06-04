import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

public class TestNachEmailSortieren extends Helper {

    private Class<?> person;
    private Class<?> sorter;

    @Before
    public void setUp() throws Exception {
        person = TestNachEmailSortieren.class.getClassLoader().loadClass("Person");
        sorter = TestNachEmailSortieren.class.getClassLoader().loadClass("NachEmailSortieren");
    }
    
    @Test
    public void test_implements() {
        assertTrue("NachEmailSortieren soll Comparator implementieren", Comparator.class.isAssignableFrom(sorter));
    }
    
    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void test_compare() throws Exception {
        Comparator comp = (Comparator) createInstance(sorter.getConstructor());
        Object personInstance1 = createInstance(person.getConstructor());
        checkField(person, "name", String.class).set(personInstance1, "A");
        checkField(person, "email", String.class).set(personInstance1, "a@a.a");
        Object personInstance2 = createInstance(person.getConstructor());
        checkField(person, "name", String.class).set(personInstance2, "B");
        checkField(person, "email", String.class).set(personInstance2, "b@b.b");
        
        int invoke1 = comp.compare(personInstance1, personInstance2);
        assertEquals("A < B => -1", -1, invoke1);
        
        int invoke2 = comp.compare(personInstance2, personInstance1);
        assertEquals("B > A => 1", 1, invoke2);
        
        int invoke3 = comp.compare(personInstance1, personInstance1);
        assertEquals("A == A => 0", 0, invoke3);
        
        int invoke4 = comp.compare(personInstance2, personInstance2);
        assertEquals("B == B => 0", 0, invoke4);
        
        int invoke5 = comp.compare(personInstance1, (Object) null);
        assertEquals("A > null => 1", 1, invoke5);
    }

}