import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.After;
import org.junit.Before;

public class Helper {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    
    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    protected void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    protected String getOutput() {
        return testOut.toString();
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

	protected Constructor<?> checkConstructor(Class<?> testClass, Class<?>... parameterTypes) {
		try {
			return testClass.getDeclaredConstructor(parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			String params = "";
			for (Class<?> class1 : parameterTypes) {
				if (params.length() > 0) {
					params += ", ";
				}
				params += class1.getSimpleName();
			}
			fail("Der Konstruktor mit den Parametern " + params + " fehlt in " + testClass.getSimpleName());
		}
		return null;
	}
	
	protected Method checkMethod(Class<?> someClass, Class<?> returnType, String method, Class<?>... paramter) {
		return checkMethod(false, someClass, returnType, method, paramter);
	}

	protected Method checkMethod(boolean isAbstract, Class<?> someClass, Class<?> returnType, String method, Class<?>... paramter) {
		try {
			Method declaredMethod = someClass.getDeclaredMethod(method, paramter);
			assertEquals(
					"Rückgabetyp in Methode " + someClass.getSimpleName() + "." + method + " ist nicht " + returnType.getSimpleName(),
					returnType, declaredMethod.getReturnType());
			if(isAbstract) {
				assertTrue("Methode " + method + " muss Abstrakt sein", Modifier.isAbstract(declaredMethod.getModifiers()));
			} else {
				assertTrue("Methode " + method + " soll nicht Abstrakt sein", !Modifier.isAbstract(declaredMethod.getModifiers()));
			}
			return declaredMethod;
		} catch (NoSuchMethodException | SecurityException e) {
			fail("Keine Methode mit dem Namen " + method + " in " + someClass.getSimpleName());
		}
		return null;
	}

	protected Field checkField(Class<?> testClass, String field, Class<?> type) {
		try {
			Field declaredField = testClass.getDeclaredField(field);
			assertTrue("Attribut " + field + " sollte den Typ " + type + " haben",
					declaredField.getType().equals(type));
			assertTrue("Attribut " + field + " sollte privat sein", Modifier.isPrivate(declaredField.getModifiers()));
			assertTrue("Attribut " + field + " sollte nicht static sein",
					!Modifier.isStatic(declaredField.getModifiers()));
			assertTrue("Attribut " + field + " sollte nicht final sein",
					!Modifier.isFinal(declaredField.getModifiers()));
			declaredField.setAccessible(true);
			return declaredField;
		} catch (NoSuchFieldException | SecurityException e) {
			fail(testClass.getSimpleName() + " fehlt das Attribut " + field);
		}
		return null;
	}

}