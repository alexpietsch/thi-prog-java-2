import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Erzeugen von Objekten
        Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();
        Tier animal1 = new Tier();

        // KonsolenAnwendung nutzen und Objekte ausfüllen
        KonsolenAnwendung controller = new KonsolenAnwendung();
        controller.einlesen(person1); /* comment */
        controller.einlesen(person2); /* comment */
        controller.einlesen(person3); /* comment */
        //controller.einlesen(animal1); /* comment */

        // Liste von Personen und anschließend sortieren und ausgeben
        Person[] liste = new Person[] { person1, person2, person3 };
        Arrays.sort(liste); /* comment */
        for (Person p : liste) {
            controller.ausgeben(p); /* comment */
        }

        // Animal ausgeben...
        //controller.ausgeben(animal1); /* comment */
    }
}
