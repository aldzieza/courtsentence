package pl.olointeria.wyrok.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import pl.olointeria.wyrok.database.models.Paragraph;
import pl.olointeria.wyrok.modelFX.ParagraphFX;
import pl.olointeria.wyrok.modelFX.ParagraphModel;
import pl.olointeria.wyrok.utils.DialogsUtils;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

public class ParagraphController {
    @FXML
    private TreeView<String> paragraphTreeView;
    @FXML
    private Button addParagraphButton;
    @FXML
    private Button editParagraphButton;
    @FXML
    private Button deleteParagraphButton;
    @FXML
    private ComboBox<ParagraphFX> chooseParagrahComboBox;
    //inne klasy nie nadaja sie do przechowywwania -nie mo�emy mieszac bazy danych w widoku okna
    //dlatego robimy inna wartw� separacyjn� ~ModelFX
    //[34.2] bezdize obs�ugiwa� w�a�nie tak� klas� ParagraphFX (StringProperty)
    //[34.3]potrzebujemy jescze ParagraphModel
    private ParagraphModel paragraphModel;


    @FXML
    private TextField paragraphTextField;

    @FXML
    public void initialize() throws ApplicationException {
        //[34.3]
        this.paragraphModel=new ParagraphModel();
        this.paragraphModel.fillList();//mamy liste ale nie jest podpieta do ComboBoxa
        this.chooseParagrahComboBox.setItems(this.paragraphModel.getParagraphFXObservableList());
        this.paragraphTreeView.setRoot(this.paragraphModel.getKorzenie());//pobieramy Korzenie
        //ale jezszce si nic nie wy�wietla bo
        initBindings();
                    }

    private void initBindings() {
        addParagraphButton.disableProperty().bind(paragraphTextField.textProperty().isEmpty());
        deleteParagraphButton.disableProperty().bind(this.paragraphModel.paragraphProperty().isNull());
        editParagraphButton.disableProperty().bind(this.paragraphModel.paragraphProperty().isNull());

    }

    public void addParagraphOnAction( ) {
       // System.out.println(paragraphModel);//je�eli si� wyswietla:"pl.olointreia.wyrok
        // .modelFX.ParagraphModel@ad5795a  to Obiekt nie jest nullem
        //wchodzimy do pola textowego
        String text = paragraphTextField.getText();
        System.out.println(text);
        try {
            paragraphModel.saveParagraphInDataBase(text);
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        //CHECK CZY ZAPISUJE SIE W BAZIE DANYCH- jest B��d ???  NullPointerException
        //ZAWSZE PRZY Bledach przegl�daj LOGI !!!
          //      B��d w aplikacji -nie tworzy�a sie tablica SENTENCES wyrok polega� na tym �e :
        //1.zmienne mia�y double zamiast Double
        //2.doda�em parametr width=1 zmiennych jako parametr
        //3. zosta�y zmienione nazwy coloumny "MIN."  na "MIN_" -mog�y to by� jakie� wewnetrzne obostrzenia
        //4.ponadto update gettery i settery
        paragraphTextField.clear();//czy�cimy pole tekstowe
    }

    public void editParagraphOnAction( ) throws ApplicationException {
        String oldText = this.paragraphModel.getParagraph().getParagraphNo();
        String newParagraphNo=DialogsUtils.editDialog(oldText); //nowa zmienna kt�ra przechowuje edytowany paragaraf
        if(newParagraphNo!=null) {
            this.paragraphModel.getParagraph().setParagraphNo(newParagraphNo);
            this.paragraphModel.updateParagraphInDataBase();
        }
    }

    public void deleteParagraphOnAction( ) {
        try {
            this.paragraphModel.deleteParagraphById();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    public void OnActionComboBox( ) {
        ParagraphFX selectedItem = this.chooseParagrahComboBox.getSelectionModel().getSelectedItem();
            this.paragraphModel.setParagraph(selectedItem);

    }
}
