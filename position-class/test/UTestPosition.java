import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class UTestPosition extends Helper {

    protected Class<?> positionClass;
    
    @Before
    public void setup() throws ClassNotFoundException {
        positionClass = UTestPosition.class.getClassLoader().loadClass("Position");
    }

    protected double getX(Object r) throws Exception {
        Field f = checkField(positionClass, "x", double.class);
        f.setAccessible(true);
        return f.getDouble(r);
    }

    protected double getY(Object r) throws Exception {
        Field f = checkField(positionClass, "y", double.class);
        f.setAccessible(true);
        return f.getDouble(r);
    }
    
    protected Object instance(double x, double y) {
        try {
            return checkConstructor(positionClass, double.class, double.class).newInstance(x, y);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            fail("Konnte keine Instanz von Position erzeugen, hat die Klasse einen Konstruktor mit zwei Double-Parametern?");
        }
        return null;
    }

    @Test
    public void test_structure_00() throws Exception {
        addMethod();
        subMethod();
        add2Method();
        sub2Method();
        mulMethod();
        divMethod();
        distanceMethod();
        dotMethod();
        
        checkMethod(positionClass, boolean.class, "equals", Object.class);
        checkMethod(positionClass, String.class, "toString");
        
        checkConstructor(positionClass, double.class, double.class);
    }

    private Method dotMethod() {
        return checkMethod(positionClass, double.class, "dot", positionClass);
    }

    private Method distanceMethod() {
        return checkMethod(positionClass, double.class, "distance", positionClass);
    }

    private Method divMethod() {
        return checkMethod(positionClass, positionClass, "div", double.class);
    }

    private Method mulMethod() {
        return checkMethod(positionClass, positionClass, "mul", double.class);
    }

    private Method sub2Method() {
        return checkMethod(positionClass, positionClass, "sub", double.class);
    }

    private Method add2Method() {
        return checkMethod(positionClass, positionClass, "add", double.class);
    }

    private Method subMethod() {
        return checkMethod(positionClass, positionClass, "sub", positionClass);
    }

    private Method addMethod() {
        return checkMethod(positionClass, positionClass, "add", positionClass);
    }

    @Test
    public void test_toString_00() throws Exception {
        String string = instance(4, 2).toString();
        assertEquals("4.0/2.0", string);
    }

    @Test
    public void test_equals_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = instance(4, 2);
        Object p3 = instance(2, 4);
        assertTrue("Sollte nicht gleich null sein", !p1.equals(null));
        assertTrue("Sollte sich selbst gleichen", p1.equals(p1));
        assertTrue("Sollte sich einer instanz gleichen, die die selben Koordinaten enthaelt", p1.equals(p2));
        assertTrue("Sollte nicht gleich anderer Koordinaten sein", !p1.equals(p3));
    }

    @Test
    public void test_add_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = instance(1, 6);
        Object p3 = addMethod().invoke(p1, p2);
        assertTrue(p1 != p3);
        assertTrue(p2 != p3);
        assertEquals(4 + 1, getX(p3), 0.01);
        assertEquals(2 + 6, getY(p3), 0.01);
    }

    @Test
    public void test_add2_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = add2Method().invoke(p1, 2);
        assertTrue(p1 != p2);
        assertEquals(4 + 2, getX(p2), 0.01);
        assertEquals(2 + 2, getY(p2), 0.01);
    }

    @Test
    public void test_sub_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = instance(1, 6);
        Object p3 = subMethod().invoke(p1, p2);
        assertTrue(p1 != p3);
        assertTrue(p2 != p3);
        assertEquals(4 - 1, getX(p3), 0.01);
        assertEquals(2 - 6, getY(p3), 0.01);
    }

    @Test
    public void test_sub2_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = sub2Method().invoke(p1, 2);
        assertTrue(p1 != p2);
        assertEquals(4 - 2, getX(p2), 0.01);
        assertEquals(2 - 2, getY(p2), 0.01);
    }

    @Test
    public void test_mul_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = mulMethod().invoke(p1, 2);
        assertTrue(p1 != p2);
        assertEquals(4 * 2, getX(p2), 0.01);
        assertEquals(2 * 2, getY(p2), 0.01);
    }

    @Test
    public void test_div_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = divMethod().invoke(p1, 2);
        assertTrue(p1 != p2);
        assertEquals(4 / 2, getX(p2), 0.01);
        assertEquals(2 / 2, getY(p2), 0.01);
    }

    @Test
    public void test_dot_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = instance(1, 6);
        Object p3 = dotMethod().invoke(p1, p2);
        assertEquals(4 * 1 + 2 * 6, (Double) p3, 0.01);
    }

    @Test
    public void test_distance_00() throws Exception {
        Object p1 = instance(4, 2);
        Object p2 = instance(1, 6);
        Object p3 = distanceMethod().invoke(p1, p2);
        assertEquals(Math.sqrt((4 - 1) * (4 - 1) + (2 - 6) * (2 - 6)), (Double) p3, 0.01);
    }
}