import java.util.Comparator;

public class InvertedAlphabeticalMemeComparator implements Comparator<Meme> {

    @Override
    public int compare(Meme m1, Meme m2) {
        StringBuilder b1 = new StringBuilder();
        StringBuilder b2 = new StringBuilder();

        String m1FullText = String.join(" ", m1.getText());
        String m2FullText = String.join(" ", m2.getText());

        return m1FullText.compareTo(m2FullText);

    }
}
