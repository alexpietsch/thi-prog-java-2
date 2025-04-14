import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class UTestMain extends Helper {
    private static final Pattern OUTPUT_REGEX = Pattern.compile("^([0-9]*.[0-9]*)\\/([0-9]*.[0-9]*)(\r|\n)([0-9]*.[0-9]*)$");

    @Test
    public void test_main_00() throws Exception {
        Main.main(new String[0]);
        String output = getOutput();
        Matcher matcher = OUTPUT_REGEX.matcher(output);
        boolean find = matcher.find();
        if(!find) {
            fail("Ausgabe passt nicht, geben Sie zuerst das Zentrum und dann den Umfang aus");
        } else {
            assertEquals("X des Zentrums sollte bei ca. 13.0 liegen", 13.0, Double.valueOf(matcher.group(1)).doubleValue(), 0.1);
            assertEquals("Y des Zentrums sollte bei ca. 11.8 liegen", 11.8, Double.valueOf(matcher.group(2)).doubleValue(), 0.1);
            assertEquals("Der Umfang sollte ca. 28.0 betragen", 28.0, Double.valueOf(matcher.group(4)).doubleValue(), 0.1);
        }
    }
}