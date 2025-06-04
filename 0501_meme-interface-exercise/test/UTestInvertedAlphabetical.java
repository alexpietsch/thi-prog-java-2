import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

public class UTestInvertedAlphabetical {

	@SuppressWarnings("rawtypes")
	private Comparable createInstance(String upper, String lower) {
		String name = "CondescendingWonkaMeme";
		try {
			Class<?> wonkaClass = UTestWonka.class.getClassLoader().loadClass(name);
			return (Comparable) wonkaClass.getConstructor(String.class, String.class).newInstance(upper, lower);
		} catch (ClassNotFoundException e) {
			fail("Keine Klasse mit dem Namen " + name);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			fail("Konnte keine Instanz erzeugen von " + name
					+ ", gibt es einen Konstruktor mit zwei Strings als Parameter?");
		}
		return null;
	}

	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void test_compare_00() {
		Comparable instance1 = createInstance("Oh you created a easy to use meme generator in Java?",
				"Thanks for mentioning it before i died");
		Comparable instance2 = createInstance("Oh, so the teacher unit tests failed?",
				"Tell me about how its all the teachers fault");
		Comparable instance3 = createInstance("Oh you create memes in exercises?", "That's very unique");

		Comparable[] list = new Comparable[] { instance1, instance2, instance3 };

		Object instance;
		try {
			instance = UTestWonka.class.getClassLoader().loadClass("InvertedAlphabeticalMemeComparator").getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			fail("Konnte keine Instanz von InvertedAlphabeticalMemeComparator erzeugen, es sollte keinen Konstruktor geben!");
			return;
		}
		assertTrue("InvertedAlphabeticalMemeComparator implementiert nicht den Comparator", instance instanceof Comparator);
		Arrays.sort(list, (Comparator)instance);

		assertEquals("Es müsste " + getText(instance2) + " kommen", instance2, list[0]);
		assertEquals("Es müsste " + getText(instance1) + " kommen", instance1, list[1]);
		assertEquals("Es müsste " + getText(instance3) + " kommen", instance3, list[2]);
	}

	private String[] getText(Object instance) {
		try {
			return (String[]) instance.getClass().getMethod("getText").invoke(instance);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			return new String[0];
		}
	}

}