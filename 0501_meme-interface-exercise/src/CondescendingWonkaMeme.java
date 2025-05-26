public class CondescendingWonkaMeme implements MemeWithLowerText, MemeWithUpperText {
    private String lower;
    private String upper;

    public CondescendingWonkaMeme(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public String getLowerText() {
        return lower;
    }

    @Override
    public String getUpperText() {
        return upper;
    }

    @Override
    public String getImageUrl() {
        return "https://makeameme.org/media/templates/250/condescending_wonka.jpg";
    }

    @Override
    public String[] getText() {
        return new String[]{getUpperText(), getLowerText()};
    }

    @Override
    public int compareTo(Meme o){
        String fullOriginalText = String.join(" ", getText());
        String fullComparingText = String.join(" ", o.getText());
        return fullOriginalText.compareTo(fullComparingText);
    }
}
