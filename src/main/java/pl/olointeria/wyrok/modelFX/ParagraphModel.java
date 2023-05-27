package pl.olointeria.wyrok.modelFX;

import com.j256.ormlite.dao.Dao;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import pl.olointeria.wyrok.database.dao.ParagraphDao;
import pl.olointeria.wyrok.database.dbutils.DbManager;
import pl.olointeria.wyrok.database.models.Paragraph;
import pl.olointeria.wyrok.database.models.Sygnature;
import pl.olointeria.wyrok.utils.converters.ConverterParagraph;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

import java.util.List;

import static pl.olointeria.wyrok.utils.converters.ConverterParagraph.convertParagraphToParagraphFX;

public class ParagraphModel {

    private ObservableList<ParagraphFX> paragraphFXObservableList= FXCollections.observableArrayList();
        //lista która dzia³a na paragrafach wpisaywanych do formatki FXML TextField
    private ObjectProperty<ParagraphFX> paragraph =new SimpleObjectProperty<ParagraphFX>();
    //objekt aktualnie wybrany w ComBoBoxie
    private TreeItem<String>  korzenie = new TreeItem<>();

    public void fillList() throws ApplicationException {
        ParagraphDao paragraphDao= new ParagraphDao();
        List<Paragraph> paragraphList = paragraphDao.queryForAll(Paragraph.class);
        initParagraphList(paragraphList);
        initKorzenie(paragraphList);
       // DbManager.closeConnectionSource();
    }

    private void initKorzenie(List<Paragraph> paragraphList) { // nie usuwaja sieParagrafy
        this.korzenie.getChildren().clear();
        paragraphList.forEach(paragraph1 -> {
            TreeItem<String> paragraphItem =new TreeItem<>(paragraph1.getParagraphNo());
            //TreeItem<String> paragraphItem2 =new TreeItem<String>(paragraph1.getSentences());
            //aby pobrac Sentences
            paragraph1.getSentences().forEach(p->{
                paragraphItem.getChildren().add(new TreeItem<>(p.getDescription()));
                paragraphItem.getChildren().add(new TreeItem<String>(p.getSygnature().getSygnatureNo()));
                paragraphItem.getChildren().add(new TreeItem<String>(p.getMaxSentence().toString()));
                paragraphItem.getChildren().add(new TreeItem<String>(p.getMinSentence().toString()));

            });
//                        paragraph1.getSygnatures().forEach(p2->{
//                            boolean add = paragraphItem.getChildren().add(new TreeItem<Sygnature>(p2.getSygnatureNo()));
//
//
//                        });
            korzenie.getChildren().add(paragraphItem);

        });
    }

    private void initParagraphList(List<Paragraph> paragraphList) {
        this.paragraphFXObservableList.clear();
        paragraphList.forEach(paragraf->{
            ParagraphFX paragraphFX = convertParagraphToParagraphFX(paragraf);

            this.paragraphFXObservableList.add(paragraphFX);
                    });
    }

    public void saveParagraphInDataBase(String name) throws ApplicationException {
                //1. wywo³ujemy po³¹czenie
        ParagraphDao paragraphDao= new ParagraphDao();//nie potrzebujemy CoonectionSource poniewa¿
        // Konstruktor w commonDao  posiada ten zapis
        Paragraph paragraph =new Paragraph();
        //paragraph.setId(paragraphDao.g);
        paragraph.setParagraphNo(name);
        System.out.println("paragraph:\t"+paragraph+"\tname:\t"+name.toUpperCase());
        paragraphDao.creatOrUpdate(paragraph);
        //po³¹czenie jest zamykane automatycznie -zapis w met. DbManager
        DbManager.closeConnectionSource();
        fillList(); //odœwierzenie dodanych elementów w BD,ale lista powinna byæ czyszczona
    }
    public void deleteParagraphById() throws ApplicationException {
        ParagraphDao paragraphDao= new ParagraphDao();//nie potrzebujemy CoonectionSource poniewa¿
        paragraphDao.deleteById(Paragraph.class,paragraph.getValue().getId());
        DbManager.closeConnectionSource();
        fillList();
    }
    public void updateParagraphInDataBase() throws ApplicationException {
        ParagraphDao paragraphDao= new ParagraphDao();//nie potrzebujemy CoonectionSource poniewa¿
        Paragraph paragraphById = paragraphDao.findById(Paragraph.class,getParagraph().getId()); //paragraph.getValue().getId());
        paragraphById.setParagraphNo(getParagraph().getParagraphNo());
        paragraphDao.creatOrUpdate(paragraphById);
        DbManager.closeConnectionSource();
        fillList();
    }

    public ObservableList<ParagraphFX> getParagraphFXObservableList() {
        return paragraphFXObservableList;
    }

    public void setParagraphFXObservableList(ObservableList<ParagraphFX> paragraphFXObservableList) {
        this.paragraphFXObservableList = paragraphFXObservableList;
    }

    public ParagraphFX getParagraph() {
        return paragraph.get();
    }

    public ObjectProperty<ParagraphFX> paragraphProperty() {
        return paragraph;
    }

    public void setParagraph(ParagraphFX paragraph) {
        this.paragraph.set(paragraph);
    }

    public TreeItem<String> getKorzenie() {
        return korzenie;
    }

    public void setKorzenie(TreeItem<String> korzenie) {
        this.korzenie = korzenie;
    }
}
