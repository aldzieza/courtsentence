package pl.olointeria.wyrok.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.olointeria.wyrok.database.dao.ParagraphDao;
import pl.olointeria.wyrok.database.dao.SentenceDao;
import pl.olointeria.wyrok.database.dao.SygnatureDao;
import pl.olointeria.wyrok.database.dbutils.DbManager;
import pl.olointeria.wyrok.database.models.Paragraph;
import pl.olointeria.wyrok.database.models.Sentence;
import pl.olointeria.wyrok.database.models.Sygnature;
import pl.olointeria.wyrok.utils.converters.ConvertSentence;
import pl.olointeria.wyrok.utils.converters.ConvertToSygnature;
import pl.olointeria.wyrok.utils.converters.ConverterParagraph;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

import java.util.List;

public class SentenceModel {
    private ObjectProperty<SentenceFX> sentenceFXObjectProperty=new SimpleObjectProperty<>(new SentenceFX());
    private ObservableList<ParagraphFX> paragraphFXList=FXCollections.observableArrayList();
    private ObservableList<SygnatureFX> sygnatureFXList=FXCollections.observableArrayList();

    public void saveSentenceInDB() throws ApplicationException {
//objekt FX sentenceFX posiada w sobie wszystkie potrzebne parametry(dane) do zapisu w bd
        //potrzebujemy convertera
        Sentence sentence= ConvertSentence.convertSentenceFXTOSentence(this.getSentenceFXObjectProperty());
        //ale brakuje nam jeszcze Paragrafu i Sygnatury które są w FX
        SygnatureDao sygnatureDao=new SygnatureDao();
        Sygnature sygnature = sygnatureDao.findById(Sygnature.class, this.getSentenceFXObjectProperty().getSygnatureFX().getId());
        ParagraphDao paragraphDao=new ParagraphDao();
        Paragraph paragraph=paragraphDao.findById(Paragraph.class,this.getSentenceFXObjectProperty().getParagraphFX().getId());
        sentence.setSygnature(sygnature);
        sentence.setParagraph(paragraph);

        SentenceDao sentenceDao=new SentenceDao();
        sentenceDao.creatOrUpdate(sentence);

    }
    //METODA DO UZUPEŁNIANIA W PARAGRAFU I SYGNATURY W FORMATCE SENTENCE_FX
        public void fillSygnatureIParagraph() throws ApplicationException {
            //potrzebujemy list obietow z bd
            fillSygnatureList();
            fillParagraphList();
        }

    private void fillParagraphList() throws ApplicationException {
        ParagraphDao paragraphDao=new ParagraphDao();
        List<Paragraph> paragraphs = paragraphDao.queryForAll(Paragraph.class);
        paragraphFXList.clear();
        paragraphs.forEach(paragraph -> {
            ParagraphFX paragraphFX= ConverterParagraph.convertParagraphToParagraphFX(paragraph);
                paragraphFXList.add(paragraphFX);

        });
        DbManager.closeConnectionSource();
    }

    private void fillSygnatureList() throws ApplicationException {
        SygnatureDao sygnatureDao=new SygnatureDao();
        List<Sygnature> sygnatures = sygnatureDao.queryForAll(Sygnature.class);
        sygnatureFXList.clear();
        sygnatures.forEach(sygnature -> {
            SygnatureFX sygnatureFX= ConvertToSygnature.convertSygnatureToSygnatureFX(sygnature);
            sygnatureFXList.add(sygnatureFX);

        });
        DbManager.closeConnectionSource();

    }


    public SentenceFX getSentenceFXObjectProperty() {
        return sentenceFXObjectProperty.get();
    }

    public ObjectProperty<SentenceFX> sentenceFXObjectPropertyProperty() {
        return sentenceFXObjectProperty;
    }

    public void setSentenceFXObjectProperty(SentenceFX sentenceFXObjectProperty) {
        this.sentenceFXObjectProperty.set(sentenceFXObjectProperty);
    }

    public ObservableList<ParagraphFX> getParagraphFXList() {
        return paragraphFXList;
    }

    public void setParagraphFXList(ObservableList<ParagraphFX> paragraphFXList) {
        this.paragraphFXList = paragraphFXList;
    }

    public ObservableList<SygnatureFX> getSygnatureFXList() {
        return sygnatureFXList;
    }

    public void setSygnatureFXList(ObservableList<SygnatureFX> sygnatureFXList) {
        this.sygnatureFXList = sygnatureFXList;
    }
}
