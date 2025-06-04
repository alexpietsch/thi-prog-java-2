import java.util.Scanner;

// Wichtig: in dieser Klasse soll kein Datentyp Person oder Animal auftauchen!
public class KonsolenAnwendung  {

    private Scanner scanner;

    public KonsolenAnwendung() {
        scanner = new Scanner(System.in);
    }

    public void einlesen(DatenEinlesen dE) {
        System.out.println("--------");
        String wert = dE.wasFehltNoch();
        while(wert != null) {
            dE.setzeNaechstenWert(wert);
            wert = dE.wasFehltNoch();
        }
        // Rufen Sie mithilfe der Schnittstelle DatenEinlesen die Methode
        // wasFehltNoch solange auf, wie diese nicht null zurück gibt.
        // Für jede Rückgabe soll die Methode in der Konsole nach einer
        // Eingabe fragen und dann mittels der Methode
        // setzeNaechstenWert den eingelesenen Wert übergeben
        
        
    }

    public void ausgeben(DatenAusgeben dA) {
        System.out.println("--------");
        // Lesen Sie über die Schnittstelle DatenAusgeben die Informationen aus,
        // iterieren Sie über diese und geben Sie diese aus.
        
    }

}