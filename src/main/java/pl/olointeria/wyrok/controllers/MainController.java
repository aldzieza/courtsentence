package pl.olointeria.wyrok.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.olointeria.wyrok.utils.DialogsUtils;
import pl.olointeria.wyrok.utils.FxmlUtils;

import java.util.Optional;

public class MainController {

    public static final String ADD_SENTENCE_FXML = "/fxml/AddSentence.fxml";
    private static final String ADD_PARAGRAPH_FXML = "/fxml/AddParagraph.fxml";
    private static final String ADD_SYGNATURE_FXML = "/fxml/AddSygnature.fxml";
    private static final String ADD_LIST_SENTENCES_FXML = "/fxml/AddListSentences.fxml";
    @FXML
    private Button addSygnatureButton;

    @FXML
    private BorderPane borderPane;



    @FXML
    private void initialize(){
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }
    @FXML
    public void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }
@FXML
    public void setModena( ) {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }
@FXML
    public void closeApplication() {
        Optional<ButtonType> buttonType = DialogsUtils.confirmationDialog();
        if (buttonType.get()== ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    public void setAlawaysOnTop(ActionEvent actionEvent) {
        //musimy dosta� si� do stage
        Stage stage = (Stage)borderPane.getScene().getWindow();
        boolean value = ((CheckMenuItem) actionEvent.getSource()).selectedProperty().get();
        // zwraca nam true lub false
        stage.setAlwaysOnTop(value);
    }
    public void setAbout() {
        DialogsUtils.dialogAboutApplication();
    }
    //wracamy do metody addBook w TopButtonsController
    @FXML
    public void addSygnature() {
        //[ 4 ] sprawdzamy czy w naszej grupie jest co� wybranie je�eli tak robimy �e nic
        //inaczej zwalnia wyb�r grupy button�w
        //poniewa� s� ju� 2 takie metody to tworzymy odzielna metod� tego fragmentu

        String s = borderPane.toString();
        System.out.println(s);
       // FxmlUtils.fxmlLoader(ADD_SYG_FXML);//ta linijka nie zadzia�a musi by� zapis jak ni�ej
        setCenter(ADD_SENTENCE_FXML);

    }
    public void setCenter(String fxmlPath) {
        borderPane.setCenter(FxmlUtils.fxmlLoader(fxmlPath));
    }

    public void addParagraphOnAction( ) {
        setCenter(ADD_PARAGRAPH_FXML);

    }

    public void addSygnatureOnAction( ) {
        System.out.println("wyswietl  dodaj Sygnature");
        setCenter(ADD_SYGNATURE_FXML);
    }

    public void addListSentences( ) {
        setCenter(ADD_LIST_SENTENCES_FXML);
    }
}
