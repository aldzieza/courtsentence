package pl.olointeria.wyrok.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogsUtils {
    static ResourceBundle boundle = FxmlUtils.getResourceBundle();
    public static void dialogAboutApplication(){
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(boundle.getString("about.title"));
        informationAlert.setHeaderText(boundle.getString("about.header"));
        informationAlert.setContentText(boundle.getString("about.content"));
        informationAlert.showAndWait();
    }
    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(boundle.getString("exit.title"));
        confirmationDialog.setHeaderText(boundle.getString("exit.header"));
        Optional<ButtonType> result= confirmationDialog.showAndWait();
        return result ;
    }
    //musimy przygotować sobie okno dialogowe aby wiedziec jakie błędy moga wystapić
    public static void errorDialog(String error){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(boundle.getString("error.title"));
        errorAlert.setHeaderText(boundle.getString("error.header"));
        errorAlert.showAndWait();
        // TextArea textArea= new TextArea(error);
        // errorAlert.getDialogPane().setContent(textArea);
        // errorAlert.showAndWait();
    }
    public static  String editDialog(String value ) {
        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle(boundle.getString("edit.title"));
        dialog.setHeaderText(boundle.getString("edit.header"));
        dialog.setContentText(boundle.getString("edit.content"));
        Optional<String> result =dialog.showAndWait();
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
