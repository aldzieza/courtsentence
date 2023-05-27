package pl.olointeria.wyrok.modelFX;

import javafx.beans.property.*;

public class SentenceFX {
    private IntegerProperty id =new SimpleIntegerProperty();
    private ObjectProperty<ParagraphFX> paragraphFX =new SimpleObjectProperty<>();
    private ObjectProperty<SygnatureFX> sygnatureFX =new SimpleObjectProperty<>();
    private StringProperty descSentence =new SimpleStringProperty();
    //private ObjectProperty<Double> maxSentence =new SimpleObjectProperty<>();
    private DoubleProperty maxSentence = new SimpleDoubleProperty();
    private DoubleProperty minSentence =new SimpleDoubleProperty();


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public ParagraphFX getParagraphFX() {
        return paragraphFX.get();
    }

    public ObjectProperty<ParagraphFX> paragraphFXProperty() {
        return paragraphFX;
    }

    public void setParagraphFX(ParagraphFX paragraphFX) {
        this.paragraphFX.set(paragraphFX);
    }

    public SygnatureFX getSygnatureFX() {
        return sygnatureFX.get();
    }

    public ObjectProperty<SygnatureFX> sygnatureFXProperty() {
        return sygnatureFX;
    }

    public void setSygnatureFX(SygnatureFX sygnatureFX) {
        this.sygnatureFX.set(sygnatureFX);
    }

    public String getDescSentence() {
        return descSentence.get();
    }

    public StringProperty descSentenceProperty() {
        return descSentence;
    }

    public double getMaxSentence() {
        return maxSentence.get();
    }

    public DoubleProperty maxSentenceProperty() {
        return maxSentence;
    }

    public void setMaxSentence(double maxSentence) {
        this.maxSentence.set(maxSentence);
    }

    public double getMinSentence() {
        return minSentence.get();
    }

    public DoubleProperty minSentenceProperty() {
        return minSentence;
    }

    public void setMinSentence(double minSentence) {
        this.minSentence.set(minSentence);
    }

    public void setDescSentence(String descSentence) {
        this.descSentence.set(descSentence);
    }

    @Override
    public String toString() {
        return "SentenceFX{" +
                "id=" + id.get() +
                ", paragraphFX=" + paragraphFX.get() +
                ", sygnatureFX=" + sygnatureFX.get() +
                ", descSentence=" + descSentence.get()+
               "max.sentence" + maxSentence.get() +
                ", minSentence=" + minSentence.get() +
                '}';
    }
}
