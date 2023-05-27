package pl.olointeria.wyrok.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.olointeria.wyrok.modelFX.SygnatureFX;
import pl.olointeria.wyrok.modelFX.SygnatureModel;
import pl.olointeria.wyrok.utils.DialogsUtils;
import pl.olointeria.wyrok.utils.exceptions.ApplicationException;

public class SygnatureController {
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private TableColumn<SygnatureFX,String> sygnatureColumn1;
@FXML
    private TableView<SygnatureFX> sygnatureTableView;

    @FXML
    private TextField sygnatureTextField;
    @FXML
    private VBox AddSygnature;

    @FXML
    private Button saveSygnatureButton;
    private SygnatureModel sygnatureModel;

    @FXML
    private void initialize() {
        this.sygnatureModel = new SygnatureModel();
        try {
            this.sygnatureModel.fillSygnatureList();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }

        bindings();
        bindingsTableView();

    }

    private void bindingsTableView() {
        this.sygnatureTableView.setItems(this.sygnatureModel.getSygnatureFXObservableList());//pobieramy liste sygnatur do TableView
        this.sygnatureColumn1.setCellValueFactory(cellData -> cellData.getValue().sygnatureNoProperty());//pobiera dana do columny
        this.sygnatureColumn1.setCellFactory(TextFieldTableCell.forTableColumn());//uaktywnia nasze komórki w kolumnach
        this.sygnatureTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.sygnatureModel.setSygnatureFXObjectPropertyEDIT(newValue);//pozwala na edycje i wprowadzenie nowej wartości
        });
    }

    private void bindings() {
        this.sygnatureModel.sygnatureFXObjectPropertyProperty().get().sygnatureNoProperty().bind(this.sygnatureTextField.textProperty());
        this.saveSygnatureButton.disableProperty().bind(this.sygnatureTextField.textProperty().isEmpty());
        this.deleteMenuItem.disableProperty().bind(this.sygnatureTableView.getSelectionModel().selectedItemProperty().isNull());//disable kiedy pusty
    }

    @FXML
    public void saveSygnatureOnAction( ) {
   // String sygnatureNo = this.sygnatureModel.getSygnatureFXObjectProperty().getSygnatureNo();
   // System.out.println(sygnatureNo);
    try {
        this.sygnatureModel.saveSygnatureInDatabase();
    } catch (ApplicationException e) {
        DialogsUtils.errorDialog(e.getMessage());
    }
    this.sygnatureTextField.clear();
}

    public void onEditSygnature(TableColumn.CellEditEvent<SygnatureFX, String> sygnatureFXStringCellEditEvent) {
        String newValue = sygnatureFXStringCellEditEvent.getNewValue();
       // System.out.println(newValue);
        this.sygnatureModel.getSygnatureFXObjectPropertyEDIT().setSygnatureNo(newValue);
        try {
            this.sygnatureModel.saveSygnatureEditInDatabase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public void deleteSygnatureOnAction( ) {
        try {
            this.sygnatureModel.deleteSygnatureInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }
}
