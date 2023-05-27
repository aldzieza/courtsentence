package pl.olointeria.wyrok.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import pl.olointeria.wyrok.database.dao.SentenceDao;
import pl.olointeria.wyrok.database.models.Sentence;
import pl.olointeria.wyrok.modelFX.*;
import pl.olointeria.wyrok.utils.DialogsUtils;
import pl.olointeria.wyrok.utils.FxmlUtils;
import pl.olointeria.wyrok.utils.converters.ConvertSentence;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListSentencesController {
    //1inicjalizujemy comboBoxy w ListSentenceModel

    @FXML
    private ComboBox sygnatureComboBox;
    @FXML
    private ComboBox paragraphComboBox;
    @FXML
    private TextField maxSentenceTextField;
    @FXML
    private TextField minSentenceTextField;

    //TUTAJ BYŁ BŁĄD POPRAWIŁEM NAZWE W POLU ALE NIE W FXML - public TableColumn SygnatureColumn;
    //ta tabela ma odwzorowywać SentenceFX czyli np.SygnatureColumn
    //odwzorowuje sygnatureFX które pracuje na SygnatureFX
    //najtrudniejsze bdzie znowu dobranie typu dla maxi minSentence
    //próba z Number

    @FXML
    private TableView<SentenceFX> sentencesTableView;
    @FXML
    private TableColumn<SentenceFX,SygnatureFX> sygnatureColumn;
    @FXML
    private TableColumn<SentenceFX,ParagraphFX> paragraphColumn;
    @FXML
    private TableColumn <SentenceFX, String> descriptionColumn;
    @FXML
    private TableColumn <SentenceFX, Number> maxSentenceColumn;
    @FXML
    private TableColumn <SentenceFX, Number> minSentenceColumn;
    @FXML
    private TableColumn<SentenceFX,SentenceFX> deleteColumn;
    @FXML
    private TableColumn<SentenceFX,SentenceFX> editColumn;

    private ListSentencesModel listSentencesModel;
@FXML
    public void initialize() throws ApplicationException {
    this.listSentencesModel=new ListSentencesModel();
    //tu musimy te elementy zbindować ale najpierw tworzymy model do naszego Controllera
    try {
        this.listSentencesModel.fillSentenceFXList();
    } catch (ApplicationException e) {
        DialogsUtils.errorDialog(e.getMessage());
    }
//




    //tu tworzymy połączenie list do comboBoxow
    this.sygnatureComboBox.setItems(this.listSentencesModel.getSygnatureFXObservableList());
    this.paragraphComboBox.setItems(this.listSentencesModel.getParagraphFXObservableList());
    //bindujemy
    this.listSentencesModel.paragraphFXObjectPropertyProperty().bind(this.paragraphComboBox.valueProperty());
    this.listSentencesModel.sygnatureFXObjectPropertyProperty().bind(this.sygnatureComboBox.valueProperty());

    this.sentencesTableView.setItems(this.listSentencesModel.getSentenceFXObservableList());
    //jeszcze nic nie jest widoczne w naszym SenetenceFX znajduje sie kazda property
  this.sygnatureColumn.setCellValueFactory(cellData->cellData.getValue().sygnatureFXProperty());
   this.descriptionColumn.setCellValueFactory(cellData->cellData.getValue().descSentenceProperty());
    this.paragraphColumn.setCellValueFactory(cellData->cellData.getValue().paragraphFXProperty());
    this.maxSentenceColumn.setCellValueFactory(cellData->cellData.getValue().maxSentenceProperty());
    this.minSentenceColumn.setCellValueFactory(cellData->cellData.getValue().minSentenceProperty());
    // this.deleteColumn.setCellValueFactory(ListBookController::call); alternatywny zapis tego co pod spodem
    this.deleteColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue()));
    this.editColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue()));
    this.deleteColumn.setCellFactory(param->new TableCell<SentenceFX, SentenceFX>(){
        Button button=createButton("/icons/delete01.jpg");
        @Override
        protected void updateItem(SentenceFX item, boolean empty) {
            super.updateItem(item, empty);
            //tu możemy budowac naszą komórkę w całej naszej CellFactory
            //komórka jest Nodem możemy odwołać sie do setGrapics
            //[49.7] znikają objekty ale nie znikają przyciski- jeżeli jest pusty
            if (empty) {
                setGraphic(null);
            }
            //[49.3] przyciski są tam gdzie nie powinny być jak temu zapobiec?[49.3]
            // po to jest ten parametr empty
            if (!empty) {
                setGraphic(button);
                //[49.4]teraz ustawiamy metode setOnAction
                button.setOnAction(event->{
                    try {
                        listSentencesModel.deleteSentence(item);
                    } catch (ApplicationException e) {
                        DialogsUtils.errorDialog(e.getMessage());
                    }
                });
            }
        }
    });
    this.editColumn.setCellFactory(param->new TableCell<SentenceFX, SentenceFX>(){
        Button button=createButton("/icons/edit01.jpg");
        @Override
        protected void updateItem(SentenceFX item, boolean empty) {
            super.updateItem(item, empty);
            //tu możemy budowac naszą komórkę w całej naszej CellFactory
            //komórka jest Nodem możemy odwołać sie do setGrapics
            //[49.7] znikają objekty ale nie znikają przyciski- jeżeli jest pusty
            if (empty) {
                setGraphic(null);
            }
            //[49.3] przyciski są tam gdzie nie powinny być jak temu zapobiec?[49.3]
            // po to jest ten parametr empty
            if (!empty) {
                setGraphic(button);
                //[49.4]teraz ustawiamy metode setOnAction
                button.setOnAction(event->{

                        FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddSentence.fxml");
                        //teraz można pobrac z loadera contener
                    Scene scene=null;
                    try {
                         scene=new Scene(loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //to powoduje że beda widoczne elem. wyroku w danych wyroku
                    SentenceController sentence=loader.getController();
                    //Object controller = loader.getController();
                     sentence.getSentenceModel().setSentenceFXObjectProperty(item);//ten element który jest w wierszu
                    sentence.bindings();
                    Stage stage=new Stage();
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                });
            }
        }
    });
   // this.maxSentenceTextField.textProperty().addListener((ListSentencesController::minSentenceTextFieldListener));
}
//    private static void minSentenceTextFieldListener(ObservableValue<? extends String> observable, String oldValue,String newValue){
//        System.out.println("Tutaj: "+observable);
//        System.out.println("Nowa: "+newValue);
//    }
    private Button createButton(String path) {
        Button button =new Button();
        Image image =new Image((this.getClass().getResource(path).toString()));
        ImageView imageView=new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }
    //[49.1] tworzymy przycisk i potem musimy obsł€żyć naszą kolumnę
    private Button createDeleteButton(String s){
        Button button =new Button();
        Image image =new Image((this.getClass().getResource("/icons/delete01.jpg").toString()));
        ImageView imageView=new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }
    //[50.2] tworzymy przycisk i potem musimy obsł€żyć naszą kolumnę
    private Button createEditButton(){
        Button button =new Button();
        Image image =new Image((this.getClass().getResource("/icons/edit01.jpg").toString()));
        ImageView imageView=new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }
    public void filterOnAction() {
  //      SygnatureFX sygnatureFX = this.listSentencesModel.getSygnatureFXObjectProperty();
//        String s = sygnatureFX.toString();
//        System.out.println(s);  będzie wywalać bład gdy usuniemy sygnaturę
        //!!!  WARTO PRZEJRZEĆ LOGI  !!!
        this.listSentencesModel.filterSentenceList();
    }

    public void countSentencesOnAction( ) throws ApplicationException {
        System.out.println("jesteśmy w buttonie Oblicz max wymiar kary");
        SentenceDao sentenceDao=new SentenceDao();
        List<Sentence> sentences = sentenceDao.queryForAll(Sentence.class);
        int i=0;
        for (Sentence sentence1 : sentences) {
            sentence1.getSygnature();
            i = i + 1;
        }
        System.out.println("i wynosi :\t"+i+"\t elementów");

        double sumMaxSentences = sentences.stream().mapToDouble(sentence -> sentence.getMaxSentence())
                //.mapToInt(song1 -> song1.getLength())  //to samo co na górze tylko inaczej zapisane
                .sum();
        System.out.println("suma maksymalnych wyroków:\t"+sumMaxSentences);
        DoubleProperty max = new SimpleDoubleProperty();
        DoubleProperty sample = new SimpleDoubleProperty();
        max.bind(sample.add(sumMaxSentences).divide(i));
       // System.out.println("divideMax to String:\t"+divideMax.toString());
        StringConverter<? extends Number> converter2 = new DoubleStringConverter();
        Bindings.bindBidirectional(this.maxSentenceTextField.textProperty(),max , (StringConverter<Number>) converter2);


        double sumMinSentences = sentences.stream().mapToDouble(sentence -> sentence.getMinSentence())
                //.mapToInt(song1 -> song1.getLength())  //to samo co na górze tylko inaczej zapisane
                .sum();
        System.out.println("suma minimalnych wyroków:\t"+sumMinSentences);
        DoubleProperty min = new SimpleDoubleProperty();
        DoubleProperty sample2 = new SimpleDoubleProperty();
         min.bind(sample2.add(sumMinSentences).divide(i));
       // System.out.println("divideMax to String:\t"+divideMin.toString());
        StringConverter<? extends Number> converter1 = new DoubleStringConverter();
        Bindings.bindBidirectional(this.minSentenceTextField.textProperty(),min , (StringConverter<Number>) converter1);



    }



    public void clearSygnatureComboBox( ) {
        this.sygnatureComboBox.getSelectionModel().clearSelection();
    }

    public void clearParagraphComboBox( ) {
    this.paragraphComboBox.getSelectionModel().clearSelection();
    }
}
