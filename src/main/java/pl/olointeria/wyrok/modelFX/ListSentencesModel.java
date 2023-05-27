package pl.olointeria.wyrok.modelFX;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import pl.olointeria.wyrok.database.dao.ParagraphDao;
import pl.olointeria.wyrok.database.dao.SentenceDao;
import pl.olointeria.wyrok.database.dao.SygnatureDao;
import pl.olointeria.wyrok.database.models.Paragraph;
import pl.olointeria.wyrok.database.models.Sentence;
import pl.olointeria.wyrok.database.models.Sygnature;
import pl.olointeria.wyrok.utils.converters.ConvertSentence;
import pl.olointeria.wyrok.utils.converters.ConvertToSygnature;
import pl.olointeria.wyrok.utils.converters.ConverterParagraph;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListSentencesModel {
    @FXML
    private TextField maxSentenceTextField;
    private ObservableList<SentenceFX> sentenceFXObservableList= FXCollections.observableArrayList();
    private ObservableList<SygnatureFX> sygnatureFXObservableList= FXCollections.observableArrayList();
    private ObservableList<ParagraphFX> paragraphFXObservableList= FXCollections.observableArrayList();
    //WYLICZENIE SUM WYROKOW DOADANO DO POTRZEB WYLICZENIA SUM WYROKOW
    private DoubleProperty sumMaxSentence = new SimpleDoubleProperty();
    private DoubleProperty sumMinSentence =new SimpleDoubleProperty();


    //tu beda obiekty które beda przechowywaly elementy
    //private ObjectProperty<ParagraphFX> paragraphFXObjectProperty =new SimpleObjectProperty<ParagraphFX>();
    //[B£¥D FILTR NIE REAGUJE]zmieniono liniê jak wy¿ej wczeœniej <~> -TO NIE TO
    private ObjectProperty<ParagraphFX> paragraphFXObjectProperty =new SimpleObjectProperty<>();
    private ObjectProperty<SygnatureFX> sygnatureFXObjectProperty =new SimpleObjectProperty<>();

private List<SentenceFX> sentenceFXList=new ArrayList();//pusta lista
// [B£¥D FILTR NIE REAGUJE]usunieto nawiasy<> w oryginale ich nie ma -TO NIE TO

    public void   fillSentenceFXList() throws ApplicationException {
            SentenceDao sentenceDao=new SentenceDao();
        List<Sentence> sentences = sentenceDao.queryForAll(Sentence.class);
        sentenceFXList.clear();
        //modyfikujemy met.fillSentencesList i tworzymy liste
        sentences.forEach(sentence -> {
            SentenceFX sentenceFX= ConvertSentence.convertSentenceTOSentenceFX(sentence);
            // this.sentenceFXObservableList.add(sentenceFX);
             sentenceFXList.add(sentenceFX); //teraz ta lista jest pe³ana wszystkich elemenów
            System.out.println(sentenceFX.toString());
        });
        this.sentenceFXObservableList.setAll(sentenceFXList); //wykasuj wszystko co tu jest i dodaj tylko to co jest zadane

        fillSygnatures();
        fillParagraphs();

}
    private void fillParagraphs() throws ApplicationException {
        ParagraphDao paragraphDao=new ParagraphDao();
        List<Paragraph> paragraphList = paragraphDao.queryForAll(Paragraph.class);
        this.paragraphFXObservableList.clear();
        paragraphList.forEach(paragraph -> {
            ParagraphFX paragraphFX= ConverterParagraph.convertParagraphToParagraphFX(paragraph);
            this.paragraphFXObservableList.add(paragraphFX);
        });
    }

    private void fillSygnatures() throws ApplicationException {
        SygnatureDao sygnatureDao=new SygnatureDao();
        List<Sygnature> sygnatureList = sygnatureDao.queryForAll(Sygnature.class);
        this.sygnatureFXObservableList.clear();
        sygnatureList.forEach(sygnature -> {
            SygnatureFX sygnatureFX= ConvertToSygnature.convertSygnatureToSygnatureFX(sygnature);
            this.sygnatureFXObservableList.add(sygnatureFX);
        });
    }
    //[49.4]  tworzymy metodê któa usuwa ksi¹¿kê item SentenceFX
    public void deleteSentence(SentenceFX sentenceFX) throws ApplicationException {
        SentenceDao sentenceDao=new SentenceDao();

        sentenceDao.deleteById(Sentence.class,sentenceFX.getId());
        //[49.5]  odœwierzenie widoku TableView
        fillSentenceFXList();

    }

    public ObservableList<SentenceFX> getSentenceFXObservableList() {
        return sentenceFXObservableList;
    }

    public void setSentenceFXObservableList(ObservableList<SentenceFX> sentenceFXObservableList) {
        this.sentenceFXObservableList = sentenceFXObservableList;
    }

    public ObservableList<SygnatureFX> getSygnatureFXObservableList() {
        return sygnatureFXObservableList;
    }

    public void setSygnatureFXObservableList(ObservableList<SygnatureFX> sygnatureFXObservableList) {
        this.sygnatureFXObservableList = sygnatureFXObservableList;
    }

    public ObservableList<ParagraphFX> getParagraphFXObservableList() {
        return paragraphFXObservableList;
    }

    public void setParagraphFXObservableList(ObservableList<ParagraphFX> paragraphFXObservableList) {
        this.paragraphFXObservableList = paragraphFXObservableList;
    }

    public ParagraphFX getParagraphFXObjectProperty() {
        return paragraphFXObjectProperty.get();
    }

    public ObjectProperty<ParagraphFX> paragraphFXObjectPropertyProperty() {
        return paragraphFXObjectProperty;
    }

    public void setParagraphFXObjectProperty(ParagraphFX paragraphFXObjectProperty) {
        this.paragraphFXObjectProperty.set(paragraphFXObjectProperty);
    }

    public SygnatureFX getSygnatureFXObjectProperty() {
        return sygnatureFXObjectProperty.get();
    }

    public ObjectProperty<SygnatureFX> sygnatureFXObjectPropertyProperty() {
        return sygnatureFXObjectProperty;
    }

    public void setSygnatureFXObjectProperty(SygnatureFX sygnatureFXObjectProperty) {
        this.sygnatureFXObjectProperty.set(sygnatureFXObjectProperty);
    }

    //PREDICATE na bazie Java 8
    //metoda ktora pracuje na predicatach
    //musimy filtrowaæ nasz¹ listê wyroków i dorzucaæ do listy
    //modyfikujemy met.fillSentencesList

    private Predicate<SentenceFX> predicateParagraph(){
        //pobieramy wyrok.paragraf i Id i sprawdza³ czy pobrane -tzw.predicate (zdanie warunkowe)
        Predicate<SentenceFX> sentenceFXPredicate = ParagraphFX -> ParagraphFX.getParagraphFX().getId() == getParagraphFXObjectProperty().getId();
        return  sentenceFXPredicate;
    }
    private Predicate<SentenceFX> predicateSygnature(){
        //pobieramy wyrok.sygnature i Id i sprawdza³ czy pobrane -tzw.predicate (zdanie warunkowe)
        Predicate<SentenceFX> sentenceFXPredicate = SygnatureFX -> SygnatureFX.getSygnatureFX().getId() == getSygnatureFXObjectProperty().getId();
        return  sentenceFXPredicate;
    }

    public void filterSentenceList() {
//sprawdzam czy sygnatura
        if (getSygnatureFXObjectProperty() != null && getParagraphFXObjectProperty() != null) {
            filterPredicate(predicateParagraph().and(predicateSygnature()));
        }else if(getParagraphFXObjectProperty()!=null){
            filterPredicate(predicateParagraph());
        }else if(getSygnatureFXObjectProperty()!=null) {
            filterPredicate(predicateSygnature());
        }else  //[B£¥D FILTR NIE REAGUJE] --TU BYL b³¹d ROZWIAZANY !!!
        //je¿eli filtr jest pusty wype³niamy go
        this.sentenceFXObservableList.setAll(sentenceFXList);
//        this.sentenceFXObservableList.forEach(consum->{
//            System.out.println("sentenceFXList\t:"+consum.toString());
//        });
    }

    public void filterPredicate(Predicate<SentenceFX> predicate){
        //bedzie zwraca³ liste
        List<SentenceFX> newSentenceFXList = sentenceFXList.stream().filter(predicate).collect(Collectors.toList());
        this.sentenceFXObservableList.setAll(newSentenceFXList);
    }

}
