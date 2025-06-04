import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UTestMeme extends Helper {

    @Test
    public void test_meme_00()
    {
    	String name = "Meme";
        try {
			Class<?> memeClass = UTestMeme.class.getClassLoader().loadClass(name);
			checkMethod(true, memeClass, String[].class, "getText");
			checkMethod(true, memeClass, String.class, "getImageUrl");
			List<Class<?>> interfaces = Arrays.asList(memeClass.getInterfaces());
			assertEquals(name + " erweitert nicht das Interface Comparable", 1, interfaces.stream().filter(c -> c.equals(Comparable.class)).toArray().length);
		} catch (ClassNotFoundException e) {
			fail("Keine Klasse mit dem Namen " + name);
		}
    }

    @Test
    public void test_lowerMeme_00()
    {
    	String methodName = "getLowerText";
    	String memeClassName = "Meme";
    	String name = "MemeWithLowerText";
        checkSubInterface(memeClassName, name, methodName);
    }

    @Test
    public void test_upperMeme_00()
    {
    	String methodName = "getUpperText";
    	String memeClassName = "Meme";
    	String name = "MemeWithUpperText";
        checkSubInterface(memeClassName, name, methodName);
    }

	private void checkSubInterface(String memeClassName, String name, String methodName) {
		try {
        	Class<?> memeClass = UTestMeme.class.getClassLoader().loadClass(memeClassName);
			Class<?> upperMemeClass = UTestMeme.class.getClassLoader().loadClass(name);
			
			checkMethod(true, upperMemeClass, String.class, methodName);
			List<Class<?>> interfaces = Arrays.asList(upperMemeClass.getInterfaces());
			assertEquals(name + " erweitert nicht das Interface " + memeClass.getSimpleName(), 1, interfaces.stream().filter(c -> c.equals(memeClass)).toArray().length);
		} catch (ClassNotFoundException e) {
			fail("Keine Klasse mit dem Namen " + name);
		}
	}
}