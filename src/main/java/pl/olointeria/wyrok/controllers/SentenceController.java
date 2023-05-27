package pl.olointeria.wyrok.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;
import pl.olointeria.wyrok.modelFX.ParagraphFX;
import pl.olointeria.wyrok.modelFX.SentenceModel;
import pl.olointeria.wyrok.modelFX.SygnatureFX;
import pl.olointeria.wyrok.utils.DialogsUtils;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

//import static com.sun.corba.se.impl.util.Version.asString;

public class SentenceController {
@FXML
    private HBox addSentence;
    @FXML
    private TextField minSenteceTextField;
    @FXML
    private TextField maxSenteceTextField;
    @FXML
    private TextArea descSygnatureTextArea;
    @FXML
    private ComboBox<SygnatureFX> sygnatureComboBox;
    @FXML
    private ComboBox<ParagraphFX> paragraphComboBox;
    @FXML
    private Button addSentenceButton;

    private SentenceModel sentenceModel;
    @FXML
    public void initialize() {
        this.sentenceModel=new SentenceModel();
        //to służy do wypełnieniea ComboBoxow
        try {
            this.sentenceModel.fillSygnatureIParagraph();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        //jeszcze musimy powiazac nasze ComboBoxy po wypełnieniu listami ktore sa w SenetenceModel
        this.paragraphComboBox.setItems(this.sentenceModel.getParagraphFXList());
        this.sygnatureComboBox.setItems(this.sentenceModel.getSygnatureFXList());

        bindings();
    }

    public void bindings() {
        //this.sentenceModel.getSentenceFXObjectProperty().sygnatureFXProperty().bind(this.sygnatureComboBox.valueProperty());
        //this.sentenceModel.getSentenceFXObjectProperty().paragraphFXProperty().bind(this.paragraphComboBox.valueProperty());
        //this.sentenceModel.getSentenceFXObjectProperty().descSentenceProperty().bind(this.descSygnatureTextArea.textProperty());
        this.descSygnatureTextArea.textProperty().bindBidirectional(this.sentenceModel.getSentenceFXObjectProperty().descSentenceProperty());
        this.paragraphComboBox.valueProperty().bindBidirectional(this.sentenceModel.getSentenceFXObjectProperty().paragraphFXProperty());
        this.sygnatureComboBox.valueProperty().bindBidirectional(this.sentenceModel.getSentenceFXObjectProperty().sygnatureFXProperty());
        // this.sentenceModel.getSentenceFXObjectProperty().maxSentenceProperty().bind(maxSenteceTextField.textProperty());
        //this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty().bind(this.minSenteceTextField.textProperty());

        // this.sentenceModel.getSentenceFXObjectProperty().maxSentenceProperty().bind((ObservableValue<? extends Double>) this.maxSenteceTextField.textProperty();
        // this.sentenceModel.getSentenceFXObjectProperty().maxSentenceProperty().bind((ObservableValue<? extends Double>) this.minSenteceTextField);
        //this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty().bind(this.minSenteceTextField.textProperty().bind(new SimpleDoubleProperty(double).asString());
        // this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty().bind(this.minSenteceTextField.textProperty());
        // konwenter to konwertowania String na Number
        //ten fragment kodu został skopiowany z prog.EXAMPL BINDING
        StringConverter<? extends Number> converter2 = new DoubleStringConverter();
        Bindings.bindBidirectional(this.maxSenteceTextField.textProperty(), this.sentenceModel.getSentenceFXObjectProperty().maxSentenceProperty(), (StringConverter<Number>) converter2);
        Bindings.bindBidirectional(this.minSenteceTextField.textProperty(), this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty(), (StringConverter<Number>) converter2);
        // Bindings.bindBidirectional(this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty(),this.minSenteceTextField.textProperty(), (StringConverter<Number>) converter2);
        //this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty().bind(this.minSenteceTextField.textProperty(), converter);
        //this.minSenteceTextField.textProperty().bindBidirectional(this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty(),converter);
        //this.minSenteceTextField.textProperty().bindBidirectional(this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty());
        // bindowanie z konwenterem, textProperty bindujemy z IntegerProperty
        // this.sentenceModel.getSentenceFXObjectProperty().minSentenceProperty().bindBidirectional(this.minSenteceTextField.textProperty(),converter);
        //      yearTextField.textProperty().bindBidirectional(personViewModel.getYearProperty(), conventer);
    }

    @FXML
       public void addSentenceOnAction( ) {
    System.out.println(this.sentenceModel.getSentenceFXObjectProperty().toString());
    try {
        this.sentenceModel.saveSentenceInDB();
    } catch (ApplicationException e) {
        DialogsUtils.errorDialog(e.getMessage());
    }

}

    public SentenceModel getSentenceModel() {
        return sentenceModel;
    }

    public void setSentenceModel(SentenceModel sentenceModel) {
        this.sentenceModel = sentenceModel;
    }
}
